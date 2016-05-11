package org.ow2.proactive.cloud_watch.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Node")
@XmlType(name = "")
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Node {

	@XmlElement(name = "alienId")
	@Getter
	private final String alienId;

	public Node() {
		this.alienId = null;
	}

}
