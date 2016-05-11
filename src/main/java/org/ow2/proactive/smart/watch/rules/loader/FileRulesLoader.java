package org.ow2.proactive.smart.watch.rules.loader;

import com.aol.micro.server.rest.jackson.JacksonUtil;
import jersey.repackaged.com.google.common.collect.Sets;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.ow2.proactive.smart.watch.model.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

@Component
public class FileRulesLoader implements RulesLoader {

	private final String rulesFilePath;
	private final JSONParser parser;

	@Autowired
	public FileRulesLoader(@Value("${rules.file.path}") String rulesFilePath) {
		this.rulesFilePath = rulesFilePath;
		this.parser = new JSONParser();
	}

	@Override
	public Set<Rule> load() {

		Set<Rule> rules = Sets.newHashSet();

		JSONArray allRules;
		try {
			allRules = (JSONArray) parser.parse(new FileReader(loadFile()));
			Iterator<JSONObject> loadedRules = allRules.iterator();
			loadedRules.forEachRemaining(rule -> rules.add(JacksonUtil.convertFromJson(rule.toString(), Rule.class)));

		} catch (IOException | ParseException e) {
			throw new RuntimeException("Error while loading the rules", e);
		}

		return rules;
	}

	private File loadFile() {
		return new File(rulesFilePath);
	}

}
