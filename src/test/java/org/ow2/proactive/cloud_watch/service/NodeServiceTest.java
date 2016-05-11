package org.ow2.proactive.cloud_watch.service;

import jersey.repackaged.com.google.common.collect.ImmutableMap;
import jersey.repackaged.com.google.common.collect.Maps;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.ow2.proactive.cloud_watch.model.NodeInformation;
import org.ow2.proactive.cloud_watch.rules.cache.SmartWatchCache;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class NodeServiceTest {

	@InjectMocks
	private NodeService nodeService;

	@Mock
	private SmartWatchCache smartWatchCache;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetNodesEmpty() {
		when(smartWatchCache.getNodesId()).thenReturn(ImmutableMap.of());
		assertThat(nodeService.getAllNodesIds().isEmpty(), is(true));
	}

	@Test
	public void testGetNodes() {
		Map<String, NodeInformation> nodesid = Maps.newHashMap();
		nodesid.put("alienid", new NodeInformation("proactiveid", "OPENSTACK"));

		when(smartWatchCache.getNodesId()).thenReturn(nodesid);
		assertThat(nodeService.getAllNodesIds().size(), is(1));
	}

	@Test
	public void testGetProactiveNodeId() {
		Map<String, NodeInformation> nodesid = Maps.newHashMap();
		nodesid.put("alienNodeId", new NodeInformation("proactiveid", "OPENSTACK"));

		when(smartWatchCache.getNodesId()).thenReturn(nodesid);
		assertThat(nodeService.getProactiveNodeId("alienNodeId").get(),
				is(new NodeInformation("proactiveid", "OPENSTACK")));
	}

	@Test
	public void testGetProactiveNodeIdAbsent() {
		Map<String, NodeInformation> nodesid = Maps.newHashMap();
		nodesid.put("alienNodeId", new NodeInformation("proactiveid", "OPENSTACK"));

		when(smartWatchCache.getNodesId()).thenReturn(nodesid);
		assertThat(nodeService.getProactiveNodeId("alienNodeIddddd").isPresent(), is(false));
	}

}
