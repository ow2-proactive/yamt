package org.ow2.proactive.cloud_watch.model;

import lombok.Value;

@Value
public class RuleTaskKey {

	private final String ruleName;
	private final NodeInformation nodeInformation;

}
