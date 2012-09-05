package org.jenkinsci.plugins.nopmdcheck.model;

import java.util.ArrayList;
import java.util.List;

public class CheckResult {
	
	private String name;
	
	private List<LineHolder> lineHolders = new ArrayList<LineHolder>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<LineHolder> getLineHolders() {
		return lineHolders;
	}

	public void setLineHolders(List<LineHolder> lineHolders) {
		this.lineHolders = lineHolders;
	}
}
