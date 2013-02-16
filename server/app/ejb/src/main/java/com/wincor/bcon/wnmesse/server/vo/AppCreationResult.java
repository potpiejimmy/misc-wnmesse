package com.wincor.bcon.wnmesse.server.vo;

public class AppCreationResult {
	private String link;
	private String output;
	
	public AppCreationResult(String link, String output) {
		this.link = link;
		this.output = output;
	}
	
	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
	public String getOutput() {
		return output;
	}
	
	public void setOutput(String output) {
		this.output = output;
	}
}
