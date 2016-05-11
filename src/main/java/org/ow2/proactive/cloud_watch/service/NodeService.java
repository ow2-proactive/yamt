package org.ow2.proactive.cloud_watch.service;

import org.ow2.proactive.cloud_watch.model.NodeInformation;
import org.ow2.proactive.cloud_watch.rules.cache.SmartWatchCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class NodeService {

	@Autowired
	private SmartWatchCache smartWatchCache;

	public Map<String, NodeInformation> getAllNodesIds() {
		return smartWatchCache.getNodesId();
	}

	public Optional<NodeInformation> getProactiveNodeId(String alienNodeId) {
		return Optional.ofNullable(smartWatchCache.getNodesId().get(alienNodeId));

	}

}
