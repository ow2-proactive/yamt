package org.ow2.proactive.smart.watch.model;

import com.aol.micro.server.rest.jackson.JacksonUtil;
import jersey.repackaged.com.google.common.collect.Sets;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class NodeTest {

	@Test
	public void testEmptyConstructor() {
		Node node = new Node();
		assertThat(node.getAlienId(), is(nullValue()));
	}

	@Test
	public void testConstructor() {
		Node node = new Node("testNode");
		assertThat(node.getAlienId(), is("testNode"));
	}

	@Test
	public void testToString() {
		Node node = new Node("testNode");
		assertThat(node.toString(), is("Node(alienId=testNode)"));
	}

	@Test
	public void testEqualsAndHashcode() {
		Node node1 = new Node("testNode");
		Node node2 = new Node("testNode");

		Set<Node> nodes = Sets.newHashSet(node1, node2);

		assertThat(nodes.size(), is(1));
		assertThat(node1.equals(node2), is(true));
	}

	@Test
	public void testToJson() {
		Node node = new Node("testNode");

		assertThat(JacksonUtil.serializeToJson(node), is("{\"alienId\":\"testNode\"}"));
	}

	@Test
	public void testFromJson() {
		Node node = new Node("testNode");

		assertThat(JacksonUtil.convertFromJson("{\"alienId\":\"testNode\"}", Node.class), is(node));
	}

}
