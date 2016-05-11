package org.ow2.proactive.cloud_watch.rules.cache;

import com.espertech.esper.client.EPRuntime;
import jersey.repackaged.com.google.common.collect.Sets;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.ow2.proactive.cloud_watch.fixtures.RuleFixtures;
import org.ow2.proactive.cloud_watch.model.Node;
import org.ow2.proactive.cloud_watch.model.NodeInformation;
import org.ow2.proactive.cloud_watch.model.Rule;
import org.ow2.proactive.cloud_watch.rules.loader.RulesLoader;
import org.ow2.proactive.cloud_watch.event.processing.MetricProcessingConfigProvider;
import org.ow2.proactive.cloud_watch.model.RuleEPRuntimeTuple;

import java.util.Map;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class SmartWatchCacheTest {

	private SmartWatchCache smartWatchCache;

	@Mock
	private RulesLoader rulesLoaderMock;

	@Mock
	private MetricProcessingConfigProvider metricProcessingConfigProviderMock;

	@Mock
	private EPRuntime epRuntime;

//	@Mock
//	private SiroccoRestClient siroccoRestClient;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		smartWatchCache = new SmartWatchCache(rulesLoaderMock, metricProcessingConfigProviderMock);
		when(metricProcessingConfigProviderMock.createConfigurationRuntime(any(Rule.class))).thenReturn(epRuntime);
//		when(siroccoRestClient.getProactiveId(anyString()))
//				.thenReturn(new NodeInformation("proactiveId1", "OPENSTACK"));
	}

	@Test
	public void testEmptyLoad() {
		Map<String, RuleEPRuntimeTuple> rules = smartWatchCache.getRules();
		assertThat(rules.isEmpty(), is(true));
	}

	@Test
	public void testFullLoadWithInit() {
		Set<Rule> values = Sets.newHashSet(RuleFixtures.getSimpleRule("rule1"));
		when(rulesLoaderMock.load()).thenReturn(values);
		smartWatchCache.init();
		Map<String, RuleEPRuntimeTuple> rules = smartWatchCache.getRules();
		assertThat(rules.size(), is(1));
		assertThat(rules.get("rule1").getRule(), CoreMatchers.is(RuleFixtures.getSimpleRule("rule1")));
		assertThat(rules.get("rule1").getEprRuntime(), is(epRuntime));
	}

	@Test
	public void testFullLoadWithRefreshData() {

		Set<Rule> values = Sets.newHashSet(RuleFixtures.getSimpleRule("rule3"));
		when(rulesLoaderMock.load()).thenReturn(values);
		smartWatchCache.refreshRules();
		Map<String, RuleEPRuntimeTuple> rules = smartWatchCache.getRules();
		assertThat(rules.size(), is(1));
		assertThat(rules.get("rule3").getRule(), CoreMatchers.is(RuleFixtures.getSimpleRule("rule3")));
	}

	@Test
	public void testFullLoadWithRefreshDataMultipleRules() {

		Set<Rule> values = Sets.newHashSet(RuleFixtures.getSimpleRule("rule4"), RuleFixtures.getSimpleRule("rule5"),
				RuleFixtures.getSimpleRule("rule6"));
		when(rulesLoaderMock.load()).thenReturn(values);
		smartWatchCache.refreshRules();
		Map<String, RuleEPRuntimeTuple> rules = smartWatchCache.getRules();
		assertThat(rules.size(), is(3));
		assertThat(rules.get("rule4").getRule(), CoreMatchers.is(RuleFixtures.getSimpleRule("rule4")));
		assertThat(rules.get("rule5").getRule(), CoreMatchers.is(RuleFixtures.getSimpleRule("rule5")));
		assertThat(rules.get("rule6").getRule(), CoreMatchers.is(RuleFixtures.getSimpleRule("rule6")));
	}

	@Test
	public void testFullLoadWithRefreshDataNodeIdsCache() {
		Set<Node> nodes = Sets.newHashSet(new Node("alienId1"), new Node("alienId2"));
		Set<Rule> values = Sets.newHashSet(RuleFixtures.getSimpleRuleWithNodes("rule", nodes));
		when(rulesLoaderMock.load()).thenReturn(values);
//		when(siroccoRestClient.getProactiveId("alienId1")).thenReturn(new NodeInformation("proactiveId1", "OPENSTACK"));
//		when(siroccoRestClient.getProactiveId("alienId2")).thenReturn(new NodeInformation("proactiveId2", "OPENSTACK"));

		smartWatchCache.refreshRules();
		Map<String, NodeInformation> nodesIds = smartWatchCache.getNodesId();
		assertThat(nodesIds.size(), is(2));
		assertThat(nodesIds.get("alienId1"), is(new NodeInformation("proactiveId1", "OPENSTACK")));
		assertThat(nodesIds.get("alienId2"), is(new NodeInformation("proactiveId2", "OPENSTACK")));
	}

	@Test
	public void testFullLoadWithRefreshDataNodeIdsCacheOptimizer() {
		Set<Node> nodes = Sets.newHashSet(new Node("alienId1"), new Node("alienId2"));
		Set<Node> nodes2 = Sets.newHashSet(new Node("alienId1"), new Node("alienId3"));

		Set<Rule> values = Sets.newHashSet(RuleFixtures.getSimpleRuleWithNodes("rule", nodes),
				RuleFixtures.getSimpleRuleWithNodes("rule2", nodes2));
		when(rulesLoaderMock.load()).thenReturn(values);
//		when(siroccoRestClient.getProactiveId("alienId1")).thenReturn(new NodeInformation("proactiveId1", "OPENSTACK"));
//		when(siroccoRestClient.getProactiveId("alienId2")).thenReturn(new NodeInformation("proactiveId2", "OPENSTACK"));
//		when(siroccoRestClient.getProactiveId("alienId3")).thenReturn(new NodeInformation("proactiveId3", "OPENSTACK"));

		smartWatchCache.refreshRules();
		Map<String, NodeInformation> nodesIds = smartWatchCache.getNodesId();
		assertThat(nodesIds.size(), is(3));
		assertThat(nodesIds.get("alienId1"), is(new NodeInformation("proactiveId1", "OPENSTACK")));
		assertThat(nodesIds.get("alienId2"), is(new NodeInformation("proactiveId2", "OPENSTACK")));
		assertThat(nodesIds.get("alienId3"), is(new NodeInformation("proactiveId3", "OPENSTACK")));

//		verify(siroccoRestClient, times(3)).getProactiveId(anyString());
	}

	@Test
	public void testFullLoadWithRefreshDataNodeIdsCacheOptimizerClassLevel() {
		Set<Node> nodes = Sets.newHashSet(new Node("alienId1"), new Node("alienId2"));
		Set<Node> nodes2 = Sets.newHashSet(new Node("alienId1"), new Node("alienId3"));

		Set<Rule> values = Sets.newHashSet(RuleFixtures.getSimpleRuleWithNodes("rule", nodes),
				RuleFixtures.getSimpleRuleWithNodes("rule2", nodes2));
		when(rulesLoaderMock.load()).thenReturn(values);
//		when(siroccoRestClient.getProactiveId("alienId1")).thenReturn(new NodeInformation("proactiveId1", "OPENSTACK"));
//		when(siroccoRestClient.getProactiveId("alienId2")).thenReturn(new NodeInformation("proactiveId2", "OPENSTACK"));
//		when(siroccoRestClient.getProactiveId("alienId3")).thenReturn(new NodeInformation("proactiveId3", "OPENSTACK"));

		smartWatchCache.refreshRules();
		Map<String, NodeInformation> nodesIds = smartWatchCache.getNodesId();
		assertThat(nodesIds.size(), is(3));
		assertThat(nodesIds.get("alienId1"), is(new NodeInformation("proactiveId1", "OPENSTACK")));
		assertThat(nodesIds.get("alienId2"), is(new NodeInformation("proactiveId2", "OPENSTACK")));
		assertThat(nodesIds.get("alienId3"), is(new NodeInformation("proactiveId3", "OPENSTACK")));

//		verify(siroccoRestClient, times(0)).getProactiveId(anyString());

		smartWatchCache.refreshRules();

	}

	@Test
	public void testFullLoadWithInitAndRefreshData() {

		testFullLoadWithInit();
		testFullLoadWithRefreshData();
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testImmutabilityOfTheCache() {

		Map<String, RuleEPRuntimeTuple> rules = smartWatchCache.getRules();
		rules.put("same new value", new RuleEPRuntimeTuple(RuleFixtures.getSimpleRule("rule1"), epRuntime));
	}

}
