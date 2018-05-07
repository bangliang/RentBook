package com.entity;

//�����ݿ����advice���װ��һ��ʵ����󣬱��ڵ���

public class Advice {
	private int id;
	private int uid;
	private String content;
	private String time;
	private int status;
	
	public Advice(String content, String time, int status) {
		super();
		this.content = content;
		this.time = time;
		this.status = status;
	}
	
	public Advice(int id, int uid, String content, String time, int status) {
		super();
		this.id = id;
		this.uid = uid;
		this.content = content;
		this.time = time;
		this.status = status;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
}
