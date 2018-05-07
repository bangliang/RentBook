package com.biz.impl;

import com.biz.UserBiz;
import com.dao.UserDao;
import com.dao.impl.UserDaoImpl;
import com.entity.User;

public class UserBizImpl implements UserBiz {
	private UserDao userDao=null;
	private boolean res;
	public UserBizImpl()
	{
		userDao=new UserDaoImpl();
	}
	@Override
	public User login(User user) {
		return userDao.queryUser(user);
	}

	@Override
	public int regist(User user) {
		if(userDao.queryUser(user)!=null)
		{
			return 1;//1代表此用户名已经存在
		}
		else
		{
			boolean res=userDao.saveUser(user);//不存在则插入数据看中
			if(res)
			{
				return 2;//注册成功
			}
			else
				return 3;//注册失败
		}
	}
}
