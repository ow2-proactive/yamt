package org.ow2.proactive.cloud_watch.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.xml.bind.annotation.*;
import java.util.Set;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Rule")
@XmlType(name = "")
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Rule {

	@XmlElement(name = "name")
	@Getter
	private final String name;

	@XmlElement(name = "nodes")
	@Getter
	private final Set<Node> nodes;

	@XmlElement(name = "selectList")
	@Getter
	private final String selectList;

	@XmlElement(name = "searchCondition")
	@Getter
	private final String searchCondition;

	@XmlElement(name = "kpis")
	@Getter
	private final Set<String> kpis;

	@XmlElement(name = "period")
	@Getter
	private final Long period;

	@XmlElement(name = "windowTimeMin")
	@Getter
	private final Long windowTimeMin;

	@XmlElement(name = "notifications")
	@Getter
	private final Set<Notification> notifications;

	public Rule() {
		this.name = null;
		this.nodes = null;
		this.selectList = null;
		this.searchCondition = null;
		this.windowTimeMin = null;
		this.period = null;
		this.notifications = null;
		this.kpis = null;
	}

}
