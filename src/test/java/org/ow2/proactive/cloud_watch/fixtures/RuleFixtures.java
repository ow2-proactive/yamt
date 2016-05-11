package org.ow2.proactive.cloud_watch.fixtures;

import jersey.repackaged.com.google.common.collect.Sets;
import org.ow2.proactive.cloud_watch.model.Node;
import org.ow2.proactive.cloud_watch.model.Notification;
import org.ow2.proactive.cloud_watch.model.Rule;

import java.util.Set;

public class RuleFixtures {

	public static Rule getSimpleRuleOneNode(String ruleName, String nodeName) {

		Set<Node> nodes = Sets.newHashSet(new Node(nodeName));
		return getSimpleRuleWithNodes(ruleName, nodes);

	}

	public static Rule getSimpleRule(String ruleName) {

		Set<Node> nodes = Sets.newHashSet(new Node("one"), new Node("two"));
		return getSimpleRuleWithNodes(ruleName, nodes);

	}

	public static Rule getSimpleRuleWithNodes(String ruleName, Set<Node> nodes) {

		String select = "select avg(cpu), avg(memory)";
		String searchCondition = "having avg(cpu) > 80 and avg(memory) > 80";
		Set<Notification> notifications = Sets.newHashSet(Notification.REST);
		Set<String> kpis = Sets.newHashSet("cpu", "memory");
		return new Rule(ruleName, nodes, select, searchCondition, kpis, 2L, 50L, notifications);

	}

}
