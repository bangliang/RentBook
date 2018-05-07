package com.biz.impl;

import java.util.List;

import com.biz.RentBiz;
import com.dao.RentDao;
import com.dao.impl.RentDaoImpl;
import com.entity.Rent2;

public class RentBizImpl implements RentBiz {
	private RentDao rentDao=null;
	public RentBizImpl()
	{
		rentDao=new RentDaoImpl();
	}
	@Override
	public List<Rent2> QueryUserRent(String uname) {
		// TODO Auto-generated method stub
		return rentDao.queryRentByUname(uname);
	}
	@Override
	public List<Rent2> QueryBookRent(String bname) {
		// TODO Auto-generated method stub
		return rentDao.queryRentByBname(bname);
	}
	@Override
	public List<Rent2> QueqyUserReturnRent(String uname) {
		// TODO Auto-generated method stub
		return rentDao.queryUserRentByReturnTime(true, uname);
	}
	@Override
	public List<Rent2> QueqyUserNotReturnRent(String uname) {
		// TODO Auto-generated method stub
		return rentDao.queryUserRentByReturnTime(false, uname);
	}
	@Override
	public List<Rent2> QueqyAllRent() {
		// TODO Auto-generated method stub
		return rentDao.queryAll();
	}
}
