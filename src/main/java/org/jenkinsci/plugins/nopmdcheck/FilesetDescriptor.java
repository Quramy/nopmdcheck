package org.jenkinsci.plugins.nopmdcheck;

import java.io.IOException;

import javax.servlet.ServletException;

import hudson.Extension;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Descriptor;
import hudson.util.FormValidation;

import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;

public class FilesetDescriptor extends AbstractDescribableImpl<FilesetDescriptor> {

	private String pattern;

	private String excludePattern;

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getExcludePattern() {
		return excludePattern;
	}

	public void setExcludePattern(String excludePattern) {
		this.excludePattern = excludePattern;
	}

	@DataBoundConstructor
	public FilesetDescriptor(String pattern, String excludePattern) {
		this.pattern = pattern;

		if (excludePattern != null) {
			this.excludePattern = excludePattern;
		} else {
			this.excludePattern = "";
		}
	}

	@Extension
	public static final class DescriptorImpl extends Descriptor<FilesetDescriptor> {

		@Override
		public String getDisplayName() {
			return "";
		}

		public FormValidation doCheckPattern(@QueryParameter String value) throws ServletException, IOException {
			//TODO implement validation logic.
			return FormValidation.ok();
		}

		public FormValidation doCheckExcludePattern(@QueryParameter String value) throws ServletException, IOException {
			return FormValidation.ok();
		}

	}
}
