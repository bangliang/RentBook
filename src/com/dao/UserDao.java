package com.dao;
 
import com.entity.*;

public interface UserDao {
	public boolean saveUser(User user);//添加用户
	public boolean delUser(int id);//删除用户
	public boolean updateUser(User user);//修改用户
	public User queryUser(User user);//查询用户
}
