package com.entity;

public class User {
	private int id;
	private String uname;
	private String upassword;
	private int type;
	//�޲����Ĺ��췽��
	public User()
	{
		
	}
	//��uname��upassword��type�Ĺ��췽��
	public User(String uname, String upassword, int type) {
		super();
		this.uname = uname;
		this.upassword = upassword;
		this.type = type;
	}
	//�����ĸ������Ĺ��췽��
	public User(int id, String uname, String upassword, int type) {
		super();
		this.id = id;
		this.uname = uname;
		this.upassword = upassword;
		this.type = type;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getUpassword() {
		return upassword;
	}
	public void setUpassword(String upassword) {
		this.upassword = upassword;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}	
}
