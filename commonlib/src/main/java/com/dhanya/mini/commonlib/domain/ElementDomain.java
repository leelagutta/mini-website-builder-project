package com.dhanya.mini.commonlib.domain;

/**
 * Element Domain Object which is persisted in database.
 * 
 * @author Leela
 */
public class ElementDomain {
	
	private int id;
	
	private String elementUniqueId;
	
	private String pageUniqueId;
	
	private String xcord;
	
	private String ycord;
	
	private String content;
	
	private String type;
	

	public int getId() {
		return id;
	}
	
	public String getElementUniqueId() {
		return elementUniqueId;
	}
	
	public void setElementUniqueId(String elementUniqueId) {
		this.elementUniqueId = elementUniqueId;
	}
	
	public String getXcord() {
		return xcord;
	}
	
	public void setXcord(String xcord) {
		this.xcord = xcord;
	}
	
	public String getYcord() {
		return ycord;
	}
	
	public void setYcord(String ycord) {
		this.ycord = ycord;
	}
	
	public String getContent() { 
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getPageUniqueId() {
		return pageUniqueId;
	}

	public void setPageUniqueId(String pageUniqueId) {
		this.pageUniqueId = pageUniqueId;
	}
}
	
