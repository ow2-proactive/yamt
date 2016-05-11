package org.ow2.proactive.cloud_watch.external.jmx;

import jersey.repackaged.com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.ow2.proactive.cloud_watch.model.NodeInformation;

import javax.management.*;
import javax.management.remote.JMXConnector;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProactiveJMXClientTest {

	@InjectMocks
	private ProactiveJMXClient proactiveJMXClient;

	@Mock
	private JMXConnectionProvider jmxConnectionProvider;

	private final Set<String> kpis = Sets.newHashSet("cpu");

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);

	}

	@Test
	public void testgetProperties() throws IOException, InstanceNotFoundException, MBeanException, ReflectionException {
		JMXConnector jmxConnector = mock(JMXConnector.class);
		when(jmxConnectionProvider.getJmxConnector()).thenReturn(jmxConnector);
		MBeanServerConnection serverConnector = mock(MBeanServerConnection.class);
		when(jmxConnector.getMBeanServerConnection()).thenReturn(serverConnector);
		Map<String, String> properties = new HashMap<String, String>();
		properties.put("cpu", "10.5");
		when(serverConnector.invoke(any(ObjectName.class), anyString(), any(), any())).thenReturn(properties);

		Map<String, Object> jmxMetrics = this.proactiveJMXClient
				.getProperties(new NodeInformation("proactiveNodeId", "OPENSTACK"), kpis);

		assertThat(new Double(jmxMetrics.get("cpu").toString()), is(new Double("10.5").doubleValue()));
	}

	@Test(expected = RuntimeException.class)
	public void testgetPropertiesException()
			throws IOException, InstanceNotFoundException, MBeanException, ReflectionException {
		JMXConnector jmxConnector = mock(JMXConnector.class);
		when(jmxConnectionProvider.getJmxConnector()).thenReturn(jmxConnector);
		MBeanServerConnection serverConnector = mock(MBeanServerConnection.class);
		when(jmxConnector.getMBeanServerConnection()).thenReturn(serverConnector);
		Map<String, String> properties = new HashMap<String, String>();
		properties.put("cpu", "10.5");
		when(serverConnector.invoke(any(ObjectName.class), anyString(), any(), any()))
				.thenThrow(new MBeanException(new Exception("error")));

		this.proactiveJMXClient.getProperties(new NodeInformation("proactiveNodeId", "OPENSTACK"), kpis);

	}

}
