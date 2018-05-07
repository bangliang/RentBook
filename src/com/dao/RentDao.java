package com.dao;

import java.util.List;

import com.entity.*;

public interface RentDao {
	public boolean savaRent(Rent rent);//保存借还记录
	public boolean updateRent(Rent rent);//修改借还记录
	public Rent queryRentById(int rid);//查询指定id的借还记录
	public List<Rent2> queryAll();//查询所有图书的借还记录
	public List<Rent2> queryRentByUname(String uname);//查询指定用户名的借还记录
	public List<Rent2> queryRentByBname(String bname);//查询指定图书名的借还记录
	public List<Rent2> queryUserRentByReturnTime(boolean flag,String uname);//查看用户归还记录（已/未归还）
	
	
}
