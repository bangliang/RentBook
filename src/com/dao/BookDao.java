package com.dao;

import java.util.List;

import com.entity.*;

//定义接口，利于维护
public interface BookDao {
	public boolean savaBook(Book book);//添加图书
	public boolean delBook(int bid);//删除指定图书
	public boolean updateBook(Book book);//修改图书
	public List<Book> queryBook();//利用泛型，查询所有图书
	public List<Book> queryBookByName(String bname);//查询指定名字的图书
	public Book queryBookById(int bid);//查询指定编号的图书
	public List<Book> queryBookByStatus(int status);//查询指定状态（可借、不可借）的图书
	public List<Book> queryHotBook(int number);//查询热门图片
}
