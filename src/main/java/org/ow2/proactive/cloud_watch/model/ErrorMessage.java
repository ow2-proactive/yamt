package org.ow2.proactive.cloud_watch.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "errorMessage")
@XmlType(name = "")
@Getter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class ErrorMessage {

	private final String message;

	public ErrorMessage() {
		this.message = null;
	}

}
