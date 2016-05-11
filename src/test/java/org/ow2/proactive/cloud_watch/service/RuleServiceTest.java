package org.ow2.proactive.cloud_watch.service;

import jersey.repackaged.com.google.common.collect.ImmutableMap;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.ow2.proactive.cloud_watch.rules.cache.SmartWatchCache;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

public class RuleServiceTest {

	@InjectMocks
	private RuleService ruleService;

	@Mock
	private SmartWatchCache smartWatchCache;

	@Mock
	private MetricTasksService metricProcessingService;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testGetAllrules() {
		when(smartWatchCache.getRules()).thenReturn(ImmutableMap.of());
		assertThat(ruleService.getAllRules().isEmpty(), is(true));
	}

	@Test
	public void testReloadRules() {
		ruleService.reloadRules();
		verify(smartWatchCache, times(1)).refreshRules();
		verify(metricProcessingService, times(1)).refreshTasks();

	}

}
