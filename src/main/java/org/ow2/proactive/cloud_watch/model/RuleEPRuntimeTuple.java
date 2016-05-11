package org.ow2.proactive.cloud_watch.model;

import com.espertech.esper.client.EPRuntime;
import lombok.Value;

@Value
public class RuleEPRuntimeTuple {

	private final Rule rule;
	private final EPRuntime eprRuntime;

}
