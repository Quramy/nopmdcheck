package org.jenkinsci.plugins.nopmdcheck.model;

public class LineHolder {
	
	private String hashcode;
	private int number;
	private String wholeLine;
	private String comment;

	public String getHashcode() {
		return hashcode;
	}

	public void setHashcode(String hashcode) {
		this.hashcode = hashcode;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getWholeLine() {
		return wholeLine;
	}

	public void setWholeLine(String wholeLine) {
		this.wholeLine = wholeLine;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
