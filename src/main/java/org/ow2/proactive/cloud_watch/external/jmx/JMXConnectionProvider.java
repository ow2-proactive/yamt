package org.ow2.proactive.cloud_watch.external.jmx;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//@Profile("prod")
@Component
public class JMXConnectionProvider {

	private final String serviceUrl;
	private final String serviceUsername;
	private final String servicePassword;

	@Getter
	private final JMXConnector jmxConnector;

	@Autowired
	public JMXConnectionProvider(@Value("${rm.jmx.url}") String serviceUrl,
								 @Value("${rm.jmx.username}") String serviceUsername, @Value("${rm.jmx.password}") String servicePassword) {

		this.serviceUrl = serviceUrl;
		this.serviceUsername = serviceUsername;
		this.servicePassword = servicePassword;

		this.jmxConnector = buildJMXConnector();

	}

	private JMXConnector buildJMXConnector() {
		JMXConnector connector = null;
		Map<String, Object> env = initializeEnv(serviceUsername, servicePassword);
		try {
			connector = JMXConnectorFactory.connect(new JMXServiceURL(serviceUrl), env);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return connector;

	}

	/**
	 * Initialize the JMX environment to be used.
	 * 
	 * @param username
	 *            login to get connected to the RM.
	 * @param password
	 *            password to get connected to the RM.
	 * @return the JMX environment map.
	 */
	private Map<String, Object> initializeEnv(String username, String password) {
		Map<String, Object> env = new HashMap<String, Object>();
		if (username != null && password != null) {
			env.put(JMXConnector.CREDENTIALS, new String[] { username, password });
		} else {
			throw new RuntimeException("No athentication is specified for JMX client.");

		}
		return env;
	}

}
