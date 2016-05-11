package org.ow2.proactive.cloud_watch.rest;

import jersey.repackaged.com.google.common.collect.Maps;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.ow2.proactive.cloud_watch.exceptions.NodeIdNotFoundException;
import org.ow2.proactive.cloud_watch.model.NodeInformation;
import org.ow2.proactive.cloud_watch.service.NodeService;

import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

public class NodeRestTest {

	@InjectMocks
	private NodeRest nodeRest;

	@Mock
	private NodeService nodeService;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetAllnodes() {
		Map<String, NodeInformation> nodeids = Maps.newConcurrentMap();
		nodeids.put("alien", new NodeInformation("proactive", "OPENSTACK"));
		when(nodeService.getAllNodesIds()).thenReturn(nodeids);
		assertThat(nodeRest.getAllNodesIds().getStatus(), is(Response.Status.OK.getStatusCode()));
	}

	@Test
	public void testConverAlienId() {
		when(nodeService.getProactiveNodeId("alien"))
				.thenReturn(Optional.of(new NodeInformation("proactive", "OPENSTACK")));
		assertThat(nodeRest.convertAlienNodeId("alien"), is("proactive"));
	}

	@Test(expected = NodeIdNotFoundException.class)
	public void testConverAlienIdNoMatching() {
		when(nodeService.getProactiveNodeId("alien")).thenReturn(Optional.empty());
		nodeRest.convertAlienNodeId("alien");
	}

}
