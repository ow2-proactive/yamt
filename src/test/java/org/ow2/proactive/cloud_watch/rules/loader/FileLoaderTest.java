package org.ow2.proactive.cloud_watch.rules.loader;

import org.junit.Before;
import org.junit.Test;
import org.ow2.proactive.cloud_watch.model.Rule;

import java.io.File;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FileLoaderTest {

	private RulesLoader ruleLoader;

	@Before
	public void init() {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource("rules-test.json").getFile());
		this.ruleLoader = new FileRulesLoader(file.getAbsolutePath());
	}

	@Test
	public void testLoad() {

		Set<Rule> rulesLoaded = ruleLoader.load();
		assertThat(rulesLoaded.isEmpty(), is(false));
		assertThat(rulesLoaded.size(), is(2));

	}

	@Test(expected = RuntimeException.class)
	public void testLoadException() {
		new FileRulesLoader("").load();

	}

}
