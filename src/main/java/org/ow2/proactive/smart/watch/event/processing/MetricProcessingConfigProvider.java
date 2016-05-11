package org.ow2.proactive.smart.watch.event.processing;

import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;
import lombok.extern.log4j.Log4j;
import org.ow2.proactive.smart.watch.model.Rule;
import org.ow2.proactive.smart.watch.notificators.NotificatorMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Log4j
@Component
public class MetricProcessingConfigProvider {

	private static String WINDOWS_TYPE = "time_batch";

	private static String PROVIDER_URI_PREFIX = "SmartwatchCEPEngineRule";

	@Autowired
	private NotificatorMapping notificatorMapping;

	public EPRuntime createConfigurationRuntime(Rule rule) {

		EPServiceProviderManager.getProvider(PROVIDER_URI_PREFIX + rule.getName()).destroy();

		EPServiceProvider epServiceProvider = EPServiceProviderManager.getProvider(PROVIDER_URI_PREFIX + rule.getName(),
				buildCepConfig(rule.getKpis()));

		EPRuntime cepRT = epServiceProvider.getEPRuntime();

		String query = buildQuery(rule, WINDOWS_TYPE);

		log.info("Query for rule " + rule.getName() + " : " + query);

		final EPStatement cepStatement = epServiceProvider.getEPAdministrator().createEPL(query);

		addListeners(rule, cepStatement);

		return cepRT;
	}

	private com.espertech.esper.client.Configuration buildCepConfig(Set<String> set) {
		com.espertech.esper.client.Configuration cepConfig = new com.espertech.esper.client.Configuration();
		Map<String, Object> kpis = set.stream().collect(Collectors.toMap(kpi -> kpi, kpi -> double.class));
		cepConfig.addEventType("Metric", kpis);
		return cepConfig;
	}

	private void addListeners(Rule rule, final EPStatement cepStatement) {
		rule.getNotifications().stream().forEach(singleNotificator -> cepStatement
				.addListener(notificatorMapping.getUpdateListener(singleNotificator).get()));
	}

	private String buildQuery(Rule rule, String windowsType) {
		return rule.getSelectList() + " from Metric.win:" + windowsType + "(" + rule.getWindowTimeMin() + ") "
				+ rule.getSearchCondition();
	}
}
