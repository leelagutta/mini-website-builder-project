package com.dhanya.mini.websitebuilder.utils;

/**
 * Model (json) object for tracking upload status of the file.
 * 
 * @author Leela
 */
public class UploadStatus {

	private String status;

	private String url;

	public UploadStatus(String status, String url) {
		this.status = status;
		this.url = url;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
