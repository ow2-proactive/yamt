package org.ow2.proactive.smart.watch.rules.loader;

import org.ow2.proactive.smart.watch.model.Rule;

import java.util.Set;

public interface RulesLoader {

	public Set<Rule> load();

}
