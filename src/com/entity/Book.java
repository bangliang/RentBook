package com.entity;

//�����ݿ����books���װ��һ��ʵ����󣬱��ڵ���

public class Book {
	private int id;
	private String bname;
	private String bcount;
	private int status;
	
	public Book()
	{
		
	}
	//���ɺ����������ֹ��췽��
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
