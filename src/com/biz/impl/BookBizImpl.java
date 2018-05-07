package com.biz.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.biz.BookBiz;
import com.dao.BookDao;
import com.dao.RentDao;
import com.dao.impl.BookDaoImpl;
import com.dao.impl.RentDaoImpl;
import com.entity.Book;
import com.entity.Rent;

public class BookBizImpl implements BookBiz {
	private BookDao bookDao=null;
	private RentDao rentDao=null;
	public BookBizImpl()
	{
		bookDao=new BookDaoImpl();
		rentDao=new RentDaoImpl();
	}

	@Override
	public boolean AddBook(Book book) {
		// TODO Auto-generated method stub
		return bookDao.savaBook(book);
	}

	@Override
	public boolean DelBook(int bid) {
		// TODO Auto-generated method stub
		return bookDao.delBook(bid);
	}

	@Override
	public boolean ModifyBook(Book book) {
		// TODO Auto-generated method stub
		return bookDao.updateBook(book);
	}

	@Override
	public List<Book> QueryAllBook() {
		// TODO Auto-generated method stub
		return bookDao.queryBook();
	}

	@Override
	public List<Book> QueryHotBook() {
		// TODO Auto-generated method stub		
		return bookDao.queryHotBook(5);
	}

	@Override
	public List<Book> QueryBookByName(String bname) {
		// TODO Auto-generated method stub
		return bookDao.queryBookByName(bname);
	}

	@Override
	public Book QueryBookById(int bid) {
		// TODO Auto-generated method stub
		return bookDao.queryBookById(bid);
	}

	@Override
	public int RentBook(int bid, int uid) {
		// TODO Auto-generated method stub
		Book book=bookDao.queryBookById(bid);//查询
		if(book==null)
			return 0;//没有找到要借的图书
		else
		{
			if(book.getStatus()==0)			
				return 1;//不可借			
			else 
			{
				book.setStatus(0);//更新状态代表以及借出
				book.setBcount(new Integer(Integer.valueOf(book.getBcount())+1).toString());
				boolean flag1=bookDao.updateBook(book);//更新图书
				Rent rent=new Rent(uid, book.getId(), new SimpleDateFormat("yyyy-MM-dd").format(new Date()), null);
				boolean flag2=rentDao.savaRent(rent);//插入借阅信息
				if(flag1&&flag2)				
					return 2;//借出成功				
				else
					return 3;//借出失败
			}
		}
	}

	@Override
	public int ReturnBook(int rid) {
		Rent rent=rentDao.queryRentById(rid);
		if(rent==null)
			return 1;//输入不正确
		else if(rent.getReturntime()!=null)
			return 2;//图书已经归还
		else
		{
			rent.setReturntime( new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			boolean flag1=rentDao.updateRent(rent);//更新记录表
			Book book=bookDao.queryBookById(rent.getBid());//找到对应的图书
			book.setStatus(1);//可借状态
			boolean flag2=bookDao.updateBook(book);//更新图书
			if(flag1&&flag2)
				return 3;//归还成功
			else
				return 4;//归还失败			
		}
	}

	@Override
	public List<Book> CanRentBook() {
		return bookDao.queryBookByStatus(1);
	}

	@Override
	public List<Book> CanNotRentBook() {
		return bookDao.queryBookByStatus(0);
	}

}
