package org.ow2.proactive.smart.watch.rest;

import jersey.repackaged.com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.ow2.proactive.smart.watch.service.RuleService;

import javax.ws.rs.core.Response;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class RuleRestTest {

	@InjectMocks
	private RuleRest ruleRest;

	@Mock
	private RuleService ruleService;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetAllrules() {
		when(ruleService.getAllRules()).thenReturn(Sets.newHashSet());
		assertThat(ruleRest.getAllRules().getStatus(), is(Response.Status.OK.getStatusCode()));
		verify(ruleService, times(1)).getAllRules();
	}

	@Test
	public void testReloadrules() {
		when(ruleService.getAllRules()).thenReturn(Sets.newHashSet());
		assertThat(ruleRest.reloadRules().getStatus(), is(Response.Status.OK.getStatusCode()));
		verify(ruleService, times(1)).reloadRules();
		verify(ruleService, times(1)).getAllRules();
	}

}
