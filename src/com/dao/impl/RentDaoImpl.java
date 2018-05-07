package com.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.dao.RentDao;
import com.entity.Book;
import com.entity.Rent;
import com.entity.Rent2;

public class RentDaoImpl extends MysqlDaoImpl implements RentDao {

	@Override
	public boolean savaRent(Rent rent) {
		// TODO Auto-generated method stub
		String sql="insert into rent(uid,bid,RentTime,ReturnTime) values(?,?,?,?) ";
		List<Object> params=new ArrayList<Object>();
		params.add(rent.getUid());
		params.add(rent.getBid());
		params.add(rent.getRenttime());
		params.add(rent.getReturntime());
		return this.MysqlUpdate(sql, params);
	}

	@Override
	public boolean updateRent(Rent rent) {
		// TODO Auto-generated method stub
		String sql="update rent set uid=?,bid=?,RentTime=?,ReturnTime=? where id=?";
		List<Object> params=new ArrayList<Object>();
		params.add(rent.getUid());
		params.add(rent.getBid());
		params.add(rent.getRenttime());
		params.add(rent.getReturntime());
		params.add(rent.getId());
		return this.MysqlUpdate(sql, params);
	}

	@Override
	public Rent queryRentById(int rid) {
		// TODO Auto-generated method stub
		String sql="select id,uid,bid,RentTime,ReturnTime from rent where id=?";
		List<Object> params=new ArrayList<Object>();//泛型
		params.add(rid);
		List<Rent> rList=null;
		try {
			rList=this.MysqlQuery(sql, params, Rent.class);
		} catch (InstantiationException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(rList.size()>0)
		{
			return rList.get(0);
		}
		return null;
	}

	@Override
	public List<Rent2> queryAll() {
		// TODO Auto-generated method stub
		List<Rent2> data=null;
		String sql="select r.id,u.uname,b.bname,r.RentTime,r.ReturnTime from "
				+ "users  u , books b,rent r where u.id=r.uid and b.id=r.bid";//三表连接的查询，给每个表都取了别名
		try {
			data=this.MysqlQuery(sql, null, Rent2.class);
		} catch (InstantiationException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	@Override
	public List<Rent2> queryRentByUname(String uname) {
		// TODO Auto-generated method stub
		List<Rent2> data=null;
		String sql="select r.id,u.uname,b.bname,r.RentTime,r.ReturnTime from "
				+ "users u,books b,rent r where u.id=r.uid and b.id=r.bid and uname=?";//三表连接的查询，给每个表都取了别名
		List<Object> params=new ArrayList<Object>();
		params.add(uname);
		try {
			data=this.MysqlQuery(sql, params, Rent2.class);
		} catch (InstantiationException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}

	@Override
	public List<Rent2> queryRentByBname(String bname) {
		// TODO Auto-generated method stub
		List<Rent2> data=null;
		String sql="select r.id,u.uname,b.bname,r.RentTime,r.ReturnTime from "
				+ "users u,books b,rent r where u.id=r.uid and b.id=r.bid and bname=?";//三表连接的查询，给每个表都取了别名
		List<Object> params=new ArrayList<Object>();
		params.add(bname);
		try {
			data=this.MysqlQuery(sql, params, Rent2.class);
		} catch (InstantiationException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
	//查看用户借还记录
	@Override
	public List<Rent2> queryUserRentByReturnTime(boolean flag, String uname) {
		// TODO Auto-generated method stub
		List<Rent2> rList=null;
		String sql=null;
		if(flag)
		{
			sql="select r.id,b.bname,r.RentTime,r.ReturnTime from "
					+ "users u,books b,rent r where u.id=r.uid and b.id=r.bid and ReturnTime is not null and uname=?";
		}
		else
			sql="select r.id,b.id as bid,u.uname,b.bname,r.RentTime,r.ReturnTime from "
			+ "users u,books b,rent r where u.id=r.uid and b.id=r.bid and ReturnTime is null and uname=?";
		List<Object> params=new ArrayList<Object>();
		params.add(uname);
		try {
			rList=this.MysqlQuery(sql, params, Rent2.class);
		} catch (InstantiationException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rList;
	}

}
