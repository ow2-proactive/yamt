package org.ow2.proactive.cloud_watch.model;

import com.aol.micro.server.rest.jackson.JacksonUtil;
import jersey.repackaged.com.google.common.collect.Sets;
import org.junit.Test;
import org.ow2.proactive.cloud_watch.fixtures.RuleFixtures;

import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class RuleTest {

	private final String jsonString = "{\"name\":\"ruleName\",\"nodes\":[{\"alienId\":\"one\"},{\"alienId\":\"two\"}],\"selectList\":\"select avg(cpu), avg(memory)\",\"searchCondition\":\"having avg(cpu) > 80 and avg(memory) > 80\",\"kpis\":[\"cpu\",\"memory\"],\"period\":2,\"windowTimeMin\":50,\"notifications\":[\"REST\"]}";

	@Test
	public void testEmptyConstructor() {
		Rule rule = new Rule();
		assertThat(rule.getName(), is(nullValue()));
		assertThat(rule.getSelectList(), is(nullValue()));
		assertThat(rule.getWindowTimeMin(), is(nullValue()));
		assertThat(rule.getSearchCondition(), is(nullValue()));
		assertThat(rule.getNodes(), is(nullValue()));
		assertThat(rule.getNotifications(), is(nullValue()));

	}

	@Test
	public void testConstructor() {
		Rule rule = RuleFixtures.getSimpleRule("ruleName");
		assertThat(rule.getName(), is("ruleName"));
		assertThat(rule.getWindowTimeMin(), is(50L));
		assertThat(rule.getPeriod(), is(2L));
		assertThat(rule.getSearchCondition(), is("having avg(cpu) > 80 and avg(memory) > 80"));
		assertThat(rule.getSelectList(), is("select avg(cpu), avg(memory)"));
		assertThat(rule.getNotifications(), is(Sets.newHashSet(Notification.REST)));

	}

	@Test
	public void testToString() {
		Rule rule = RuleFixtures.getSimpleRule("ruleName");
		assertThat(rule.toString(), is(
				"Rule(name=ruleName, nodes=[Node(alienId=one), Node(alienId=two)], selectList=select avg(cpu), avg(memory), searchCondition=having avg(cpu) > 80 and avg(memory) > 80, kpis=[cpu, memory], period=2, windowTimeMin=50, notifications=[REST])"));
	}

	@Test
	public void testEqualsAndHashcode() {
		Rule rule1 = RuleFixtures.getSimpleRule("ruleName");
		Rule rule2 = RuleFixtures.getSimpleRule("ruleName");
		Set<Rule> rules = Sets.newHashSet(rule1, rule2);
		assertThat(rules.size(), is(1));
		assertThat(rule1.equals(rule2), is(true));
	}

	@Test
	public void testToJson() {
		Rule rule = RuleFixtures.getSimpleRule("ruleName");
		assertThat(JacksonUtil.serializeToJson(rule), is(jsonString));
	}

	@Test
	public void testFromJson() {
		Rule rule = RuleFixtures.getSimpleRule("ruleName");
		assertThat(JacksonUtil.convertFromJson(jsonString, Rule.class), is(rule));

	}

}
