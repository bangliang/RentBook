package com.entity;

public class Rent {
	private int id;
	private int uid;
	private int bid;
	private String renttime;
	private String returntime;
	
	public Rent()
	{
		
	}
	public Rent(int uid, int bid, String renttime, String returntime) {
		super();
		this.uid = uid;
		this.bid = bid;
		this.renttime = renttime;
		this.returntime = returntime;
	}
	public Rent(int id, int uid, int bid, String renttime, String returntime) {
		super();
		this.id = id;
		this.uid = uid;
		this.bid = bid;
		this.renttime = renttime;
		this.returntime = returntime;
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
	public int getBid() {
		return bid;
	}
	public void setBid(int bid) {
		this.bid = bid;
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
