package org.ow2.proactive.cloud_watch.exceptions;

import org.ow2.proactive.cloud_watch.model.ErrorMessage;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class NodeIdNotFoundException extends WebApplicationException {

	private static final long serialVersionUID = 1L;

	public NodeIdNotFoundException(String message) {
		super(Response.status(Response.Status.NOT_FOUND.getStatusCode()).entity(new ErrorMessage(message))
				.type(MediaType.APPLICATION_JSON).build());
	}

}
