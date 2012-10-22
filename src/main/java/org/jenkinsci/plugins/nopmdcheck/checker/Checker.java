package org.jenkinsci.plugins.nopmdcheck.checker;

import hudson.FilePath.FileCallable;
import hudson.remoting.VirtualChannel;

import java.io.File;
import java.io.IOException;

import org.jenkinsci.plugins.nopmdcheck.model.CheckResult;
import org.jenkinsci.plugins.nopmdcheck.model.LineHolder;
import org.jenkinsci.plugins.nopmdcheck.util.HashGenerator;
import org.jenkinsci.plugins.nopmdcheck.util.Logger;

public abstract class Checker implements FileCallable<CheckResult> {

	private static final long serialVersionUID = 1L;

	protected Logger logger;

	private String suffix = "";

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public Checker() {
		init();
	}

	public CheckResult invoke(File file, VirtualChannel channel)
			throws IOException, InterruptedException {

		// set a hash code to results
		CheckResult result = check(file);
		if (result.getLineHolders() != null
				&& result.getLineHolders().size() > 0) {
			for (LineHolder holder : result.getLineHolders()) {
				holder.setHashcode(HashGenerator.getSHA(result.getName()
						+ holder.getWholeLine()));
			}
		}

		return result;
	}

	protected CheckResult canonicalName(File src) {
		CheckResult result = new CheckResult();
		String absolutePath = src.getAbsolutePath();
		
		String sp = System.getProperty("file.separator");

		if (suffix != null && !suffix.equals("")) {
			// result.setName(absolutePath.replaceFirst(suffix, ""));
			if (absolutePath.substring(0, suffix.length()).equals(suffix)) {
				String rPath = absolutePath.substring(suffix.length());
				if(sp.equals("\\")){
					result.setName(rPath.replaceAll("\\\\", "/"));
				}else{
					result.setName(rPath);
				}
			}
		} else {
			result.setName(absolutePath);
		}
		return result;
	}

	private void init() {
		logger = Logger.getInstance();
	}

	public abstract CheckResult check(File file) throws IOException;

}
