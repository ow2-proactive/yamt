package org.ow2.proactive.smart.watch.service;

import com.espertech.esper.client.EPRuntime;
import org.ow2.proactive.smart.watch.event.processing.RuleExecutorTask;
import org.ow2.proactive.smart.watch.external.ProactiveClient;
import org.ow2.proactive.smart.watch.model.Rule;
import org.ow2.proactive.smart.watch.model.RuleTaskKey;
import org.ow2.proactive.smart.watch.rules.cache.SmartWatchCache;
import org.ow2.proactive.smart.watch.rules.cache.TaskExecutorsCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Timer;
import java.util.TimerTask;

@Component
public class MetricTasksService {

	@Autowired
	private SmartWatchCache smartWatchCache;

	@Autowired
	private TaskExecutorsCache taskExecutorsCache;

	@Autowired
	private ProactiveClient proactiveClient;

	@PostConstruct
	public void refreshTasks() {

		taskExecutorsCache.cancelAndRemoveAllTasks();

		smartWatchCache.getRules().forEach((key, value) -> {
			value.getRule().getNodes().stream().forEach(alienNodeId -> {
				RuleTaskKey ruleTaskKey = new RuleTaskKey(key,
						smartWatchCache.getNodesId().get(alienNodeId.getAlienId()));
				addNewTask(ruleTaskKey, value.getRule(), value.getEprRuntime());
			});
		});
	}

	private void addNewTask(RuleTaskKey key, Rule rule, EPRuntime epRuntime) {
		TimerTask timerTask = new RuleExecutorTask(key.getNodeInformation(), proactiveClient, epRuntime,
				rule.getKpis());
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(timerTask, 0, rule.getPeriod() * 60 * 1000);
		taskExecutorsCache.getLiveRuleTimerTasks().put(key, timer);
	}

}
