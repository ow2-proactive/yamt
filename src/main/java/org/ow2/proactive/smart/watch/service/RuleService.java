package org.ow2.proactive.smart.watch.service;

import org.ow2.proactive.smart.watch.model.Rule;
import org.ow2.proactive.smart.watch.rules.cache.SmartWatchCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RuleService {

	@Autowired
	private SmartWatchCache smartWatchCache;

	@Autowired
	private MetricTasksService metricProcessingService;

	public Set<Rule> getAllRules() {
		return smartWatchCache.getRules().values().stream().map(value -> value.getRule()).collect(Collectors.toSet());
	}

	public void reloadRules() {
		smartWatchCache.refreshRules();
		metricProcessingService.refreshTasks();
	}

}
