package org.ow2.proactive.smart.watch.event.processing;

import com.espertech.esper.client.EPRuntime;
import jersey.repackaged.com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.ow2.proactive.smart.watch.event.processing.RuleExecutorTask;
import org.ow2.proactive.smart.watch.external.ProactiveClient;
import org.ow2.proactive.smart.watch.model.NodeInformation;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;

public class RuleExecutorTaskTest {

	private RuleExecutorTask ruleExecutorTask;

	private NodeInformation nodeInformation;

	@Mock
	private ProactiveClient proactiveClient;
	@Mock
	private EPRuntime epRuntime;

	private final Set<String> kpis = Sets.newHashSet("cpu.usage", "memory.actualfree");

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);

		nodeInformation = new NodeInformation("nodeId", "providerName");

		this.ruleExecutorTask = new RuleExecutorTask(nodeInformation, proactiveClient, epRuntime, kpis);
	}

	@Test
	public void testRun() {
		Map<String, Object> metrics = kpis.stream().collect(Collectors.toMap(kpi -> kpi, kpi -> new Double(12)));

		when(proactiveClient.getProperties(nodeInformation, kpis)).thenReturn(metrics);
		ruleExecutorTask.run();
		verify(epRuntime, times(1)).sendEvent(metrics, "Metric");
	}
}
