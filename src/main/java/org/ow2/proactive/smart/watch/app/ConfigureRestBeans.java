package org.ow2.proactive.smart.watch.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

@Configuration
public class ConfigureRestBeans {

	@Value("${elasticity.url}")
	private String elasticityUrl;

	@Bean
	public WebTarget elasticityWebTarget() {
		Client client = ClientBuilder.newClient();
		return client.target(elasticityUrl);
	}

}
