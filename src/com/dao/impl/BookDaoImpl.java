package com.dao.impl;

import java.util.ArrayList;
import java.util.List;

import com.dao.BookDao;
import com.entity.Book;

public class BookDaoImpl extends MysqlDaoImpl implements BookDao {

	@Override
	public boolean savaBook(Book book) {
		// TODO Auto-generated method stub
		String sql="insert into books(bname,bcount,status) values(?,?,?)";
		List<Object> params=new ArrayList<Object>();//泛型
		params.add(book.getBname());
		params.add(book.getBcount());
		params.add(book.getStatus());
		return this.MysqlUpdate(sql, params);
	}

	@Override
	public boolean delBook(int bid) {
		// TODO Auto-generated method stub
		String sql="delete from books where id=?";
		List<Object> params=new ArrayList<Object>();//泛型
		params.add(bid);
		return this.MysqlUpdate(sql, params);
	}

	@Override
	public boolean updateBook(Book book) {
		// TODO Auto-generated method stub
		String sql="update books set  bname=?,bcount=?,status=? where id=?";
		List<Object> params=new ArrayList<Object>();//泛型
		
		params.add(book.getBname());
		params.add(book.getBcount());
		params.add(book.getStatus());
		params.add(book.getId());
		return this.MysqlUpdate(sql, params);
	}

	@Override
	public List<Book> queryBook() {
		// TODO Auto-generated method stub
		String sql="select id,bname,bcount,status from books";
		List<Book> bList=null;
		try {
			bList=this.MysqlQuery(sql, null, Book.class);
		} catch (InstantiationException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bList;
	}

	@Override
	public List<Book> queryBookByName(String bname) {
		// TODO Auto-generated method stub
		String sql="select id,bname,bcount,status from books where bname=?";
		List<Object> params=new ArrayList<Object>();//泛型
		params.add(bname);
		List<Book> bList=null;
		try {
			bList=this.MysqlQuery(sql, params, Book.class);
		} catch (InstantiationException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bList;
	}

	@Override
	public Book queryBookById(int bid) {
		// TODO Auto-generated method stub
		String sql="select id,bname,bcount,status from books where id=?";
		List<Object> params=new ArrayList<Object>();//泛型
		params.add(bid);
		List<Book> bList=null;
		try {
			bList=this.MysqlQuery(sql, params, Book.class);
		} catch (InstantiationException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(bList.size()>0)
		{
			return bList.get(0);
		}
		return null;
	}

	@Override
	public List<Book> queryBookByStatus(int status) {
		// TODO Auto-generated method stub
		String sql="select id,bcount,status from books where status=?";
		List<Object> params=new ArrayList<Object>();//泛型
		params.add(status);
		List<Book> bList=null;
		try {
			bList=this.MysqlQuery(sql, params, Book.class);
		} catch (InstantiationException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bList;
	}

	@Override
	//查询热门图书，即借出次数前20位图书
	public List<Book> queryHotBook(int number) {
		// TODO Auto-generated method stub
		String sql="select id,bname,bcount,status from books order by bcount desc limit "+number;
		List<Book> bList=null;
		try {
			bList=this.MysqlQuery(sql, null, Book.class);
		} catch (InstantiationException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bList;
	}

}
