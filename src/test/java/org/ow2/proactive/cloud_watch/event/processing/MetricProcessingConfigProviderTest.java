package org.ow2.proactive.cloud_watch.event.processing;

import com.espertech.esper.client.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.ow2.proactive.cloud_watch.fixtures.RuleFixtures;
import org.ow2.proactive.cloud_watch.model.Rule;
import org.ow2.proactive.cloud_watch.notificators.NotificatorMapping;
import org.ow2.proactive.cloud_watch.notificators.RestNotificator;
import org.ow2.proactive.cloud_watch.model.Notification;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(EPServiceProviderManager.class)
public class MetricProcessingConfigProviderTest {

	@InjectMocks
	private MetricProcessingConfigProvider metricProcessingConfigProvider;

	@Mock
	private EPServiceProvider epsServiceProvider;

	@Mock
	private EPRuntime cepRTMock;

	@Mock
	private EPAdministrator epAdministratorMock;

	@Mock
	private EPStatement cepStatementMock;

	@Mock
	private NotificatorMapping notificatorMapping;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		PowerMockito.mockStatic(EPServiceProviderManager.class);

		when(EPServiceProviderManager.getProvider(anyString())).thenReturn(epsServiceProvider);

		when(EPServiceProviderManager.getProvider(anyString(), any(com.espertech.esper.client.Configuration.class)))
				.thenReturn(epsServiceProvider);
		when(epsServiceProvider.getEPRuntime()).thenReturn(cepRTMock);
		when(epsServiceProvider.getEPAdministrator()).thenReturn(epAdministratorMock);

	}

	@Test
	public void testCreateConfigurationRuntime() {
		Rule rule = RuleFixtures.getSimpleRule("ruleName");
		when(notificatorMapping.getUpdateListener(Notification.REST)).thenReturn(Optional.of(new RestNotificator()));

		when(epAdministratorMock.createEPL(anyString())).thenReturn(cepStatementMock);

		EPRuntime epr = metricProcessingConfigProvider.createConfigurationRuntime(rule);
		assertThat(epr, is(not(nullValue())));
	}

	@Test
	public void testQuery() {
		Rule rule = RuleFixtures.getSimpleRule("ruleName");

		when(epAdministratorMock.createEPL(
				"select avg(cpu), avg(memory) from Metric.win:time_batch(50) having avg(cpu) > 80 and avg(memory) > 80"))
						.thenReturn(cepStatementMock);

		when(notificatorMapping.getUpdateListener(Notification.REST)).thenReturn(Optional.of(new RestNotificator()));

		EPRuntime epr = metricProcessingConfigProvider.createConfigurationRuntime(rule);
		assertThat(epr, is(not(nullValue())));
	}

	@Test
	public void testNotificators() {
		Rule rule = RuleFixtures.getSimpleRule("ruleName");

		when(epAdministratorMock.createEPL(anyString())).thenReturn(cepStatementMock);

		when(notificatorMapping.getUpdateListener(Notification.REST)).thenReturn(Optional.of(new RestNotificator()));

		EPRuntime epr = metricProcessingConfigProvider.createConfigurationRuntime(rule);
		assertThat(epr, is(not(nullValue())));

		verify(cepStatementMock, times(1)).addListener(any(UpdateListener.class));
	}

}
