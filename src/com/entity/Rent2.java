package com.entity;

public class Rent2 {
	private int id;//��¼id
	private int bid;//ͼ��id
	private String uname;//��¼�û���
	private String bname;//��¼ͼ����
	private String renttime;//���ʱ��
	private String returntime;//�黹ʱ��
	
	public Rent2()
	{
		
	}
	public Rent2(int id, String uname, String bname, String renttime, String returntime) {
		super();
		this.id = id;
		this.uname = uname;
		this.bname = bname;
		this.renttime = renttime;
		this.returntime = returntime;
	}
	public Rent2(String uname, String bname, String renttime, String returntime) {
		super();
		this.uname = uname;
		this.bname = bname;
		this.renttime = renttime;
		this.returntime = returntime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBid() {
		return bid;
	}
	public void setBid(int bid) {
		this.bid = bid;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getBname() {
		return bname;
	}
	public void setBname(String bname) {
		this.bname = bname;
	}
	public String getRenttime() {
		return renttime;
	}
	public void setRenttime(String renttime) {
		this.renttime = renttime;
	}
	public String getReturntime() {
		return returntime;
	}
	public void setReturntime(String returntime) {
		this.returntime = returntime;
	}	
}
