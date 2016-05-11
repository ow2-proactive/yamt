package org.ow2.proactive.cloud_watch.rules.cache;

import org.junit.Before;
import org.junit.Test;
import org.ow2.proactive.cloud_watch.model.NodeInformation;
import org.ow2.proactive.cloud_watch.model.RuleTaskKey;

import java.util.Map;
import java.util.Timer;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class TaskExecutorCacheTest {

	private TaskExecutorsCache taskExecutorsCache;

	@Before
	public void init() {

		taskExecutorsCache = new TaskExecutorsCache();
	}

	@Test
	public void testEmptyLoad() {
		Map<RuleTaskKey, Timer> rules = taskExecutorsCache.getLiveRuleTimerTasks();
		assertThat(rules.isEmpty(), is(true));
	}

	@Test
	public void testcancelAndRemoveAllTasks() {
		Map<RuleTaskKey, Timer> rules = taskExecutorsCache.getLiveRuleTimerTasks();

		Timer timerMock = mock(Timer.class);

		rules.put(new RuleTaskKey("ruleName", new NodeInformation("proactiveNodeId", "OPENSTACK")), timerMock);
		rules.put(new RuleTaskKey("ruleName2", new NodeInformation("proactiveNodeId", "OPENSTACK")), timerMock);
		rules.put(new RuleTaskKey("ruleName3", new NodeInformation("proactiveNodeId", "OPENSTACK")), timerMock);

		assertThat(taskExecutorsCache.getLiveRuleTimerTasks().size(), is(3));

		taskExecutorsCache.cancelAndRemoveAllTasks();

		assertThat(taskExecutorsCache.getLiveRuleTimerTasks().isEmpty(), is(true));

		verify(timerMock, times(3)).cancel();
	}

	@Test
	public void testcancelAndRemoveTask() {
		Map<RuleTaskKey, Timer> rules = taskExecutorsCache.getLiveRuleTimerTasks();

		Timer timerMock = mock(Timer.class);
		Timer timerMock2 = mock(Timer.class);
		Timer timerMock3 = mock(Timer.class);

		rules.put(new RuleTaskKey("ruleName", new NodeInformation("proactiveNodeId", "OPENSTACK")), timerMock);
		rules.put(new RuleTaskKey("ruleName2", new NodeInformation("proactiveNodeId", "OPENSTACK")), timerMock2);
		rules.put(new RuleTaskKey("ruleName3", new NodeInformation("proactiveNodeId", "OPENSTACK")), timerMock3);

		assertThat(taskExecutorsCache.getLiveRuleTimerTasks().size(), is(3));

		taskExecutorsCache.cancelAndRemoveTasks(
				new RuleTaskKey("ruleName2", new NodeInformation("proactiveNodeId", "OPENSTACK")));

		assertThat(taskExecutorsCache.getLiveRuleTimerTasks().size(), is(2));

		assertThat(taskExecutorsCache.getLiveRuleTimerTasks().containsKey(
				new RuleTaskKey("ruleName2", new NodeInformation("proactiveNodeId", "OPENSTACK"))), is(false));

		verify(timerMock2, times(1)).cancel();

	}

}
