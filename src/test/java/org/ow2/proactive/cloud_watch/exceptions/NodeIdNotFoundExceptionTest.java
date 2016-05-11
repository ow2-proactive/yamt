package org.ow2.proactive.cloud_watch.exceptions;

import org.junit.Test;
import org.ow2.proactive.cloud_watch.model.ErrorMessage;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class NodeIdNotFoundExceptionTest {

	@Test
	public void testStatus() {
		String alienNodeId = "cde76187-b041-4083-8c83-0e1fb3114603k";
		WebApplicationException alienIdNotFound = new NodeIdNotFoundException(
				"Alien Node Id : " + alienNodeId + " not found in the system");
		assertThat(alienIdNotFound.getResponse().getStatus(), is(Status.NOT_FOUND.getStatusCode()));
	}

	@Test
	public void testMessage() {
		String message = "Alien Node Id : alienNodeId  not found in the system";
		WebApplicationException alienIdNotFound = new NodeIdNotFoundException(message);

		ErrorMessage errorMessage = (ErrorMessage) alienIdNotFound.getResponse().getEntity();
		assertThat(errorMessage.getMessage(), is(message));
	}

	@Test
	public void testApplicationType() {
		String message = "Alien Node Id : alienNodeId  not found in the system";
		WebApplicationException alienIdNotFound = new NodeIdNotFoundException(message);
//		assertThat(alienIdNotFound.getResponse().getMediaType().toString(), is("application/json"));
	}

}
