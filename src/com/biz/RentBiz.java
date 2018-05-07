package com.biz;

import java.util.List;

import com.entity.Rent2;

public interface RentBiz {
	public List<Rent2> QueryUserRent(String uname);//查看指定用户的借阅记录
	public List<Rent2> QueryBookRent(String bname);//查看指定图书的借阅记录
	public List<Rent2> QueqyUserReturnRent(String uname);//查看指定用户的已归还记录
	public List<Rent2> QueqyUserNotReturnRent(String uname);//查看指定用户的未归还记录
	public List<Rent2> QueqyAllRent();//查看所有的借阅记录

}
