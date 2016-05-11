package org.ow2.proactive.cloud_watch.rules.cache;

import jersey.repackaged.com.google.common.collect.ImmutableMap;
import jersey.repackaged.com.google.common.collect.Maps;
import lombok.Getter;
import lombok.extern.log4j.Log4j;
import org.ow2.proactive.cloud_watch.model.NodeInformation;
import org.ow2.proactive.cloud_watch.model.Rule;
import org.ow2.proactive.cloud_watch.rules.loader.RulesLoader;
import org.ow2.proactive.cloud_watch.event.processing.MetricProcessingConfigProvider;
import org.ow2.proactive.cloud_watch.model.RuleEPRuntimeTuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Log4j
@Service
public class SmartWatchCache {

	@Getter
	private volatile ImmutableMap<String, RuleEPRuntimeTuple> rules;
	@Getter
	private volatile Map<String, NodeInformation> nodesId;

	private final RulesLoader rulesLoader;
	private final MetricProcessingConfigProvider metricProcessingConfigProvider;

	private final Function<String, NodeInformation> populateNodeIdsFunction;

	private static final Map<String, NodeInformation> memoiseProactiveNodeIdsFunction = new ConcurrentHashMap<String, NodeInformation>();

	@Autowired
	public SmartWatchCache(RulesLoader rulesLoader, MetricProcessingConfigProvider metricProcessingConfigProvider) {
		this.rules = ImmutableMap.of();
		this.rulesLoader = rulesLoader;
		this.metricProcessingConfigProvider = metricProcessingConfigProvider;
		this.nodesId = ImmutableMap.of();
		this.populateNodeIdsFunction = new Function<String, NodeInformation>() {
			@Override
			public NodeInformation apply(String s) {
				return null;
			}
		};

//		this.populateNodeIdsFunction = memoise(alienId -> siroccoRestClient.getProactiveId(alienId));
		init();
	}

	public void init() {
//		refreshRules();
	}

	public synchronized void refreshRules() {
		Map<String, NodeInformation> nodesIdLoading = Maps.newHashMap();

		Map<String, Rule> loadedRules = rulesLoader.load().stream()
				.peek(singleRule -> populateNodesIdsCache(nodesIdLoading, singleRule))
				.collect(Collectors.toMap(Rule::getName, Function.identity()));

		Map<String, RuleEPRuntimeTuple> rulesWithEPRunners = loadedRules.entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getKey, e -> new RuleEPRuntimeTuple(e.getValue(),
						metricProcessingConfigProvider.createConfigurationRuntime(e.getValue()))));

		rules = ImmutableMap.copyOf(rulesWithEPRunners);
		nodesId = ImmutableMap.copyOf(nodesIdLoading);

		log.info("Loaded " + rules.size() + " rules");

	}

	private void populateNodesIdsCache(Map<String, NodeInformation> nodesIdLoading, Rule singleRule) {

		singleRule.getNodes().stream().forEach(
				node -> nodesIdLoading.put(node.getAlienId(), populateNodeIdsFunction.apply(node.getAlienId())));
	}

	private static Function<String, NodeInformation> memoise(Function<String, NodeInformation> fn) {
		return (a) -> memoiseProactiveNodeIdsFunction.computeIfAbsent(a, fn);
	}

}
