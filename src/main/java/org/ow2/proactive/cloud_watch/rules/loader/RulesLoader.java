package org.ow2.proactive.cloud_watch.rules.loader;

import org.ow2.proactive.cloud_watch.model.Rule;

import java.util.Set;

public interface RulesLoader {

	public Set<Rule> load();

}
