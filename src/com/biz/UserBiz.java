package com.biz;

import com.entity.User;

public interface UserBiz {
	public User login(User user);//用户登录，返回用户的信息（对象）
	public int regist(User user);//用户注册
}
