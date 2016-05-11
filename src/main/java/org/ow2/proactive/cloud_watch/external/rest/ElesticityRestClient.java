package org.ow2.proactive.cloud_watch.external.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.net.URLEncoder;

@Component
public class ElesticityRestClient {

	@Autowired
	private WebTarget elasticityWebTarget;

	public void sendNotification(String ruleName, String metric) {
		try {
			elasticityWebTarget.queryParam("statistic", ruleName)
					.queryParam("VALUE", URLEncoder.encode(metric, "UTF-8")).request(MediaType.TEXT_PLAIN_TYPE)
					.get(String.class);
		} catch (Exception e) {
			throw new RuntimeException("Exception when sending notification to elasticity web", e);
		}
	}

}
