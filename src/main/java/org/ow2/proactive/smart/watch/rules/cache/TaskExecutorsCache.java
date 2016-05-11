package org.ow2.proactive.smart.watch.rules.cache;

import jersey.repackaged.com.google.common.collect.Maps;
import lombok.Getter;
import org.ow2.proactive.smart.watch.model.RuleTaskKey;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Timer;

@Component
public class TaskExecutorsCache {

	@Getter
	private final Map<RuleTaskKey, Timer> liveRuleTimerTasks;

	public TaskExecutorsCache() {
		this.liveRuleTimerTasks = Maps.newConcurrentMap();
	}

	public void cancelAndRemoveAllTasks() {
		cancelAllTaks();
		removeTasks();
	}

	public void cancelAndRemoveTasks(RuleTaskKey key) {
		cancelTask(key);
		removeTask(key);
	}

	private void cancelTask(RuleTaskKey key) {
		liveRuleTimerTasks.get(key).cancel();
	}

	private void removeTask(RuleTaskKey key) {
		liveRuleTimerTasks.remove(key);
	}

	private void cancelAllTaks() {
		liveRuleTimerTasks.keySet().stream().forEach(key -> cancelTask(key));
	}

	private void removeTasks() {
		liveRuleTimerTasks.clear();
	}

}
