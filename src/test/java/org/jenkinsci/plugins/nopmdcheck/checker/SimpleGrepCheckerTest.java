package org.jenkinsci.plugins.nopmdcheck.checker;

import java.io.File;

import org.jenkinsci.plugins.nopmdcheck.model.CheckResult;
import org.jenkinsci.plugins.nopmdcheck.util.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SimpleGrepCheckerTest {

	@Before
	public void setup() {
		Logger.init(System.out, Logger.Level.DEBUG);
		suffix = new File("pom.xml").getAbsolutePath().replace("pom.xml", "");

	}

	private String suffix;

	@Test
	public void checkTest01() throws Exception {
		Checker checker = new SimpleGrepChecker();
		checker.setSuffix(suffix);
		CheckResult result = checker.check(new File("src/test/resources/inspectees/sample1.txt"));

		Assert.assertEquals("src/test/resources/inspectees/sample1.txt", result.getName());
		
		String actualComment = result.getLineHolders().get(0).getComment();
		Assert.assertEquals(" messages", actualComment);
	}
}
