package org.ow2.proactive.smart.watch.external;

import jersey.repackaged.com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.ow2.proactive.smart.watch.model.NodeInformation;

import java.util.Map;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class RandomProactiveClientTest {

	private RandomProactiveClient randomProactiveClient;

	private final Set<String> kpis = Sets.newHashSet("cpu.usage", "memory.actualfree");

	@Before
	public void init() {
		this.randomProactiveClient = new RandomProactiveClient();
	}

	@Test
	public void testGetPropertyRandom() {
		String proactiveNodeId = "proactiveNodeId";
		Map<String, Object> randomMetrics = this.randomProactiveClient
				.getProperties(new NodeInformation(proactiveNodeId, "OPENSTACK"), kpis);
		assertThat(randomMetrics, is(not(nullValue())));
	}

	@Test
	public void testRangeFrom0to100() {
		String proactiveNodeId = "proactiveNodeId";
		Map<String, Object> randomMetrics = this.randomProactiveClient
				.getProperties(new NodeInformation(proactiveNodeId, "OPENSTACK"), kpis);
		assertThat(new Double(randomMetrics.get("cpu.usage").toString()), is(lessThanOrEqualTo(100D)));
		assertThat(new Double(randomMetrics.get("cpu.usage").toString()), is(greaterThanOrEqualTo(0D)));

	}

}
