package org.ow2.proactive.cloud_watch.external.jmx;

import com.beust.jcommander.internal.Maps;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyMap;

@RunWith(PowerMockRunner.class)
@PrepareForTest(JMXConnectorFactory.class)
public class JMXConnectionProviderTest {

	@Mock
	private JMXConnector jmxConnector;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		PowerMockito.mockStatic(JMXConnectorFactory.class);

	}

	@Test
	public void testJmxConnectorConstructor() throws IOException {
		String serviceUrl = "service:jmx:rmi:///jndi/rmi://localhost:5822/JMXRMAgent";
		Map<String, Object> env = Maps.newHashMap();
		String username = "userName";
		String password = "password";

		env.put(JMXConnector.CREDENTIALS, new String[] { username, password });

		Mockito.when(JMXConnectorFactory.connect(any(JMXServiceURL.class), anyMap())).thenReturn(jmxConnector);
		JMXConnector connector = new JMXConnectionProvider(serviceUrl, "username", "password").getJmxConnector();

		assertThat(connector, is(jmxConnector));

	}

	@Test(expected = RuntimeException.class)
	public void testIncorrectJmxAddress() {
		new JMXConnectionProvider("wrongaddress", "username", "password");
	}

	@Test(expected = RuntimeException.class)
	public void testNoPassword() {
		String serviceUrl = "service:jmx:rmi:///jndi/rmi://localhost:5822/JMXRMAgent";
		new JMXConnectionProvider(serviceUrl, "username", null);
	}

	@Test(expected = RuntimeException.class)
	public void testNoUsername() {
		String serviceUrl = "service:jmx:rmi:///jndi/rmi://localhost:5822/JMXRMAgent";
		new JMXConnectionProvider(serviceUrl, null, "password");
	}

}
