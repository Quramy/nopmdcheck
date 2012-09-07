package org.jenkinsci.plugins.nopmdcheck;

import hudson.model.Action;
import hudson.model.AbstractBuild;

import java.io.IOException;
import java.util.List;

import net.sf.json.JSONArray;

import org.jenkinsci.plugins.nopmdcheck.model.CheckResult;

public class NopmdCheckResultAction implements Action {

	private AbstractBuild<?, ?> owner;
	
	private List<CheckResult> resultList;

	public NopmdCheckResultAction(AbstractBuild<?, ?> owner) throws IOException {
		this.owner = owner;
		
//		XmlFile xmlFile = new XmlFile(new File(owner.getRootDir(), "nopmd_check_result.xml"));
//		List<CheckResult> resultList = (List<CheckResult>) xmlFile.read();
//		this.resultList = resultList;
	}

	public AbstractBuild<?, ?> getOwner() {
		return this.owner;
	}
	
	public void setResultList(List<CheckResult> resultList){
		this.resultList = resultList;
	}
	
	public List<CheckResult> getResultList(){
		return resultList;
	}
	
	public String getCount(){
		return resultList.size() + "";
	}
	
	public String getResultAsJson(){
		JSONArray array = JSONArray.fromObject(resultList);
		return array.toString();
	}

	public String getIconFileName() {
		return "document.gif";
	}

	public String getDisplayName() {
		return "Nopmd check result";
	}

	public String getUrlName() {
		return "nopmdCheckResult";
	}

}
