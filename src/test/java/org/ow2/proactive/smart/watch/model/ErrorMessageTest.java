package org.ow2.proactive.smart.watch.model;

import com.aol.micro.server.rest.jackson.JacksonUtil;
import jersey.repackaged.com.google.common.collect.Sets;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class ErrorMessageTest {

	@Test
	public void testEmptyConstructor() {
		ErrorMessage errorMessage = new ErrorMessage();
		assertThat(errorMessage.getMessage(), is(nullValue()));
	}

	@Test
	public void testConstructor() {
		ErrorMessage errorMessage = new ErrorMessage("testErrorMessage");
		assertThat(errorMessage.getMessage(), is("testErrorMessage"));
	}

	@Test
	public void testToString() {
		ErrorMessage errorMessage = new ErrorMessage("testErrorMessage");
		assertThat(errorMessage.toString(), is("ErrorMessage(message=testErrorMessage)"));
	}

	@Test
	public void testEqualsAndHashcode() {
		ErrorMessage errorMessage1 = new ErrorMessage("testErrorMessage");
		ErrorMessage errorMessage2 = new ErrorMessage("testErrorMessage");

		Set<ErrorMessage> errorMessages = Sets.newHashSet(errorMessage1, errorMessage2);

		assertThat(errorMessages.size(), is(1));
		assertThat(errorMessage1.equals(errorMessage2), is(true));
	}

	@Test
	public void testToJson() {
		ErrorMessage errorMessage = new ErrorMessage("testErrorMessage");

		assertThat(JacksonUtil.serializeToJson(errorMessage), is("{\"message\":\"testErrorMessage\"}"));
	}

	@Test
	public void testFromJson() {
		ErrorMessage errorMessage = new ErrorMessage("testErrorMessage");

		assertThat(JacksonUtil.convertFromJson("{\"message\":\"testErrorMessage\"}", ErrorMessage.class),
				is(errorMessage));
	}

}
