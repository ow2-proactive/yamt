package org.ow2.proactive.cloud_watch.external.jmx;

//import jersey.repackaged.com.google.common.collect.Maps;
import lombok.extern.log4j.Log4j;
import org.ow2.proactive.cloud_watch.external.ProactiveClient;
import org.ow2.proactive.cloud_watch.model.NodeInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.management.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j
@Component
@Profile("prod")
public class ProactiveJMXClient implements ProactiveClient {

	private static final String MBEAN_NAME_PREFIX = "ProActiveResourceManager:name=IaasMonitoring-";

	@Autowired
	private JMXConnectionProvider jmxConnectionProvider;

	@Override
	public Map<String, Object> getProperties(NodeInformation nodeInformation, Set<String> kpis) {

		MBeanServerConnection connection;
		Map<String, Object> metrics = new HashMap<>();

		try {

			connection = jmxConnectionProvider.getJmxConnector().getMBeanServerConnection();

			ObjectName obj = new ObjectName(MBEAN_NAME_PREFIX + nodeInformation.getProviderName());

			Object[] params = new Object[] { nodeInformation.getNodeId() };
			String[] signature = new String[] { String.class.getName() };

			final Map<String, String> properties = invokeMBean(connection, obj, params, signature, "getVMProperties");

			metrics = kpis.stream().collect(Collectors.toMap(kpi -> kpi, kpi -> new Double(properties.get(kpi))));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return metrics;
	}

	private Map<String, String> invokeMBean(MBeanServerConnection connection, ObjectName obj, Object[] params,
			String[] signature, String method)
					throws InstanceNotFoundException, MBeanException, ReflectionException, IOException {
		Map<String, String> properties = new HashMap<>();

		properties = (Map<String, String>) connection.invoke(obj, method, params, signature);
		printProperties(method, properties);

		return properties;
	}

	private void printProperties(String method, Map<String, String> properties) {
		log.debug("*********************" + method + "**********************");
		properties.entrySet().stream().forEach(entry -> log.debug(entry));
		log.debug("*******************************************");
	}

}
