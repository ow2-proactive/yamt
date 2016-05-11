package org.ow2.proactive.smart.watch.external;

import org.ow2.proactive.smart.watch.model.NodeInformation;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Profile("local")
public class RandomProactiveClient implements ProactiveClient {

	private final Random random = new Random();

	@Override
	public Map<String, Object> getProperties(NodeInformation nodeInformation, Set<String> kpis) {

		return kpis.stream().collect(Collectors.toMap(kpi -> kpi, kpi -> 0D + (100D - 0D) * random.nextDouble()));

	}

}
