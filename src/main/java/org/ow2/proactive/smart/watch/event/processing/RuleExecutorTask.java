package org.ow2.proactive.smart.watch.event.processing;

import com.espertech.esper.client.EPRuntime;
import org.ow2.proactive.smart.watch.external.ProactiveClient;
import org.ow2.proactive.smart.watch.model.NodeInformation;

import java.util.Map;
import java.util.Set;
import java.util.TimerTask;

public class RuleExecutorTask extends TimerTask {

	private final NodeInformation nodeInformation;
	private final ProactiveClient proactiveClient;
	private final EPRuntime epRuntime;
	private final Set<String> kpis;

	public RuleExecutorTask(NodeInformation nodeInformation, ProactiveClient proactiveClient, EPRuntime epRuntime,
			Set<String> kpis) {
		this.proactiveClient = proactiveClient;
		this.epRuntime = epRuntime;
		this.nodeInformation = nodeInformation;
		this.kpis = kpis;
	}

	@Override
	public void run() {
		Map<String, Object> metrics = proactiveClient.getProperties(nodeInformation, kpis);
		epRuntime.sendEvent(metrics, "Metric");

	}

}
