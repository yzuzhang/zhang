package com.feicent.zhang.project.webside.entity;

import java.io.Serializable;

/**
 * 
 * jquery select2 插件模型
 * @author gaogang
 * 2016年7月12日 下午4:16:05
 *
 */
public class Select2Entity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String text;
	
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
