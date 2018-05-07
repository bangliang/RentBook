package com.entity;

//将数据库里的books表封装成一个实体对象，便于调用

public class Book {
	private int id;
	private String bname;
	private String bcount;
	private int status;
	
	public Book()
	{
		
	}
	//生成含参数的两种构造方法
	public Book(int id, String bname, String bcount, int status) {
		super();
		this.id = id;
		this.bname = bname;
		this.bcount = bcount;
		this.status = status;
	}
	public Book(String bname, String bcount, int status) {
		super();
		this.bname = bname;
		this.bcount = bcount;
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBname() {
		return bname;
	}
	public void setBname(String bname) {
		this.bname = bname;
	}
	public String getBcount() {
		return bcount;
	}
	public void setBcount(String bcount) {
		this.bcount = bcount;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
}
