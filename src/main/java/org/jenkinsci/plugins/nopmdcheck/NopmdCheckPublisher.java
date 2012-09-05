package org.jenkinsci.plugins.nopmdcheck;

import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.XmlFile;
import hudson.model.BuildListener;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.Descriptor;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Publisher;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.jenkinsci.plugins.nopmdcheck.checker.Checker;
import org.jenkinsci.plugins.nopmdcheck.checker.SimpleGrepChecker;
import org.jenkinsci.plugins.nopmdcheck.model.CheckResult;
import org.jenkinsci.plugins.nopmdcheck.util.Logger;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;

public class NopmdCheckPublisher extends Publisher {

	private List<FilesetDescriptor> filesetList;

	public List<FilesetDescriptor> getFilesetList() {
		return this.filesetList;
	}

	private Logger logger;

	/**
	 * 
	 * @param filesetList
	 */
	@DataBoundConstructor
	@SuppressWarnings("deprecation")
	public NopmdCheckPublisher(List<FilesetDescriptor> filesetList) {
		this.filesetList = filesetList;
	}

	public BuildStepMonitor getRequiredMonitorService() {
		return BuildStepMonitor.NONE;
	}

	@Override
	public boolean perform(AbstractBuild<?, ?> build, Launcher launcher, BuildListener listener)
			throws InterruptedException {

		Logger.init(listener);
		logger = Logger.getInstance();
		logger.info("start nopmd check");
		
		 List<CheckResult> resultList = new ArrayList<CheckResult>();
		try {
			for (FilesetDescriptor fileset : filesetList) {
				FilePath[] files = build.getWorkspace().list(fileset.getPattern(), fileset.getExcludePattern());
				Checker checker;

				// TODO make enable to choose implementor of Checker.
				checker = new SimpleGrepChecker();
				
				checker.setSuffix(build.getWorkspace().getRemote());
				logger.debug(checker.getSuffix());
				if (files != null && files.length > 0) {
					for (FilePath file : files) {
						logger.debug(file.getRemote(), file.getName());
						CheckResult result = file.act(checker);
						if(result.getLineHolders() != null && result.getLineHolders().size() > 0){
							resultList.add(result);
						}
					}

				}

			}
			
			// output check results.
			//TODO make result file abstract.
			XmlFile xmlFile = new XmlFile(new File(build.getRootDir(), "nopmd_check_result.xml"));
			xmlFile.write(resultList);
			logger.info("output to ", xmlFile.getFile().getAbsolutePath());
			
			//add Action to build.
			NopmdCheckResultAction action = new NopmdCheckResultAction(build);
			build.addAction(action);
			
			
		} catch (IOException e) {
			return false;
		}
		logger.info("end nopmd check.");
		return true;
	}

	@Override
	public DescriptorImpl getDescriptor() {
		return (DescriptorImpl) super.getDescriptor();
	}

	@Extension
	public static final class DescriptorImpl extends Descriptor<Publisher> {

		public boolean isApplicable(Class<? extends AbstractProject<?, ?>> aClass) {
			return true;
		}

		public DescriptorImpl() {
			super(NopmdCheckPublisher.class);
		}

		@Override
		public String getDisplayName() {
			return "nopmd checker";
		}

		@Override
		public boolean configure(StaplerRequest req, JSONObject formData) throws FormException {
			// To persist global configuration information,
			// set that to properties and call save().
			// ^Can also use req.bindJSON(this, formData);
			// (easier when there are many fields; need set* methods for this,
			// like setUseFrench)
			save();
			return super.configure(req, formData);
		}

	}
}
