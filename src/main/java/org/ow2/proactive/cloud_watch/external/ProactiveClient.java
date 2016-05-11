package org.ow2.proactive.cloud_watch.external;

import org.ow2.proactive.cloud_watch.model.NodeInformation;

import java.util.Map;
import java.util.Set;

public interface ProactiveClient {
	Map<String, Object> getProperties(NodeInformation nodeInformation, Set<String> kpis);
}
