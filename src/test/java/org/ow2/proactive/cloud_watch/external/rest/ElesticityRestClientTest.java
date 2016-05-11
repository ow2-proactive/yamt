package org.ow2.proactive.cloud_watch.external.rest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class ElesticityRestClientTest {

	@InjectMocks
	private ElesticityRestClient elesticityRestClient;

	@Mock
	private WebTarget elasticityWebTarget;

	@Mock
	private Builder builder;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testSendNotification() {
		when(elasticityWebTarget.queryParam(anyString(), anyString())).thenReturn(elasticityWebTarget);

		when(elasticityWebTarget.request(MediaType.TEXT_PLAIN_TYPE)).thenReturn(builder);

		elesticityRestClient.sendNotification("RuleA", "some_notification");

		verify(elasticityWebTarget, times(1)).queryParam("statistic", "RuleA");
		verify(elasticityWebTarget, times(1)).queryParam("VALUE", "some_notification");

	}

	@Test(expected = RuntimeException.class)
	public void testSendNotificationException() {

		elesticityRestClient.sendNotification("RuleA", "some_notification");

	}

}
