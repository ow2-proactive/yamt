package org.ow2.proactive.cloud_watch.service;

import com.beust.jcommander.internal.Maps;
import com.espertech.esper.client.EPRuntime;
import jersey.repackaged.com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.ow2.proactive.cloud_watch.fixtures.RuleFixtures;
import org.ow2.proactive.cloud_watch.model.NodeInformation;
import org.ow2.proactive.cloud_watch.rules.cache.SmartWatchCache;
import org.ow2.proactive.cloud_watch.external.ProactiveClient;
import org.ow2.proactive.cloud_watch.model.RuleEPRuntimeTuple;
import org.ow2.proactive.cloud_watch.model.RuleTaskKey;
import org.ow2.proactive.cloud_watch.rules.cache.TaskExecutorsCache;

import java.util.Map;
import java.util.Timer;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class MetricTasksServiceTest {

	@InjectMocks
	private MetricTasksService metricTasksService;

	@Mock
	private SmartWatchCache smartWatchCache;

	@Mock
	private TaskExecutorsCache taskExecutorsCache;

	@Mock
	private ProactiveClient proactiveClient;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testRefreshTasksEmpty() {
		Map<RuleTaskKey, Timer> liveRulesTimerTasks = Maps.newHashMap();
		when(taskExecutorsCache.getLiveRuleTimerTasks()).thenReturn(liveRulesTimerTasks);
		ImmutableMap<String, RuleEPRuntimeTuple> rules = ImmutableMap.of();
		when(smartWatchCache.getRules()).thenReturn(rules);
		metricTasksService.refreshTasks();
		verify(smartWatchCache, times(0)).getNodesId();
	}

	@Test
	public void testRefreshTasksNonEmpty() {
		Map<RuleTaskKey, Timer> liveRulesTimerTasks = Maps.newHashMap();
		when(taskExecutorsCache.getLiveRuleTimerTasks()).thenReturn(liveRulesTimerTasks);

		Map<String, RuleEPRuntimeTuple> rulesMap = Maps.newHashMap();

		EPRuntime eprRuntime = mock(EPRuntime.class);
		rulesMap.put("ruleAAA",
				new RuleEPRuntimeTuple(RuleFixtures.getSimpleRuleOneNode("ruleAAA", "nodeid111"), eprRuntime));

		ImmutableMap<String, RuleEPRuntimeTuple> rules = ImmutableMap.copyOf(rulesMap);

		when(smartWatchCache.getRules()).thenReturn(rules);

		Map<String, NodeInformation> nodesId = Maps.newHashMap();

		nodesId.put("nodeid111", new NodeInformation("proactiveid123", "OPENSTACK"));

		when(smartWatchCache.getNodesId()).thenReturn(nodesId);

		metricTasksService.refreshTasks();

		assertThat(liveRulesTimerTasks.size(), is(1));

		RuleTaskKey ruleTaskKey = new RuleTaskKey("ruleAAA", new NodeInformation("proactiveid123", "OPENSTACK"));

		liveRulesTimerTasks.get(ruleTaskKey).cancel();
	}

}
