package com.biz;

import java.util.List;

import com.entity.Book;

public interface BookBiz {
	public boolean AddBook(Book book);//添加图书
	public boolean DelBook(int bid);//删除图书
	public boolean ModifyBook(Book book);//修改图书
	public List<Book> QueryAllBook();//查询所有图书信息
	public List<Book> QueryHotBook();//查看前五本最受欢迎的图书
	public List<Book> QueryBookByName(String bname);//根据图书名字来查询
	public Book QueryBookById(int bid);//根据图书id来查询
	public int RentBook(int bid,int uid);//按图书id和用户id来借书
	public int ReturnBook(int rid);//还书的功能
	public List<Book> CanRentBook();//可借图书
	public List<Book> CanNotRentBook();//不可借图书
}
