package org.jenkinsci.plugins.nopmdcheck.checker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jenkinsci.plugins.nopmdcheck.model.CheckResult;
import org.jenkinsci.plugins.nopmdcheck.model.LineHolder;

public class SimpleGrepChecker extends Checker {

	Pattern pattern;

	private static final long serialVersionUID = 1L;

	public SimpleGrepChecker() {
		super();
		pattern = Pattern.compile("NOPMD(.*)$");
	}

	@Override
	public CheckResult check(File file) throws IOException {

		Reader reader = new FileReader(file);
		BufferedReader bf = new BufferedReader(reader);
		CheckResult result = canonicalName(file);

		String line;
		int count = 1;
		while ((line = bf.readLine()) != null) {
			Matcher matcher = pattern.matcher(line);
			if (matcher.find()) {
				logger.debug("hit!", ":", result.getName(), ":", line);
				LineHolder holder = new LineHolder();
				holder.setWholeLine(line);
				holder.setComment(matcher.group(1));
				holder.setNumber(count);
				result.getLineHolders().add(holder);
			}
			count++;
		}

		bf.close();
		reader.close();

		return result;
	}

}
