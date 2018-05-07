package com.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.dao.UserDao;
import com.entity.User;

//实现UserDao这个借口
public class UserDaoImpl extends MysqlDaoImpl implements UserDao {

	@Override
	public boolean saveUser(User user) {
		// TODO Auto-generated method stub
		String sql="insert into users(uname,upassword,type) values(?,?,?)";
		List<Object> params=new ArrayList<Object>();//泛型
		params.add(user.getUname());
		params.add(user.getUpassword());
		params.add(user.getType());
		return this.MysqlUpdate(sql, params);
	}

	@Override
	public boolean delUser(int id) {
		// TODO Auto-generated method stub
		String sql="delete from users where id=?";
		List<Object> params=new ArrayList<Object>();//泛型
		params.add(id);
		return this.MysqlUpdate(sql, params);
	}

	@Override
	public boolean updateUser(User user) {
		// TODO Auto-generated method stub
		String sql="update users set uname=?,upassword=?,type=? where id=?";
		List<Object> params=new ArrayList<Object>();//泛型
		params.add(user.getUname());
		params.add(user.getUpassword());
		params.add(user.getType());
		params.add(user.getId());
		return this.MysqlUpdate(sql, params);
	}

	@Override
	public User queryUser(User user) {
		// TODO Auto-generated method stub
		List<User> uList=null;
		String sql="select id,uname,upassword,type from users where uname=? and upassword=?";
		List<Object> params=new ArrayList<Object>();//泛型
		params.add(user.getUname());	
		params.add(user.getUpassword());
		try {
			uList= this.MysqlQuery(sql, params, User.class);
		} catch (InstantiationException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(uList.size()>0)
		{
			return uList.get(0);
		}
		return null; 
	}

}
