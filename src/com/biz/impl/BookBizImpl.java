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
		Book book=bookDao.queryBookById(bid);//��ѯ
		if(book==null)
			return 0;//û���ҵ�Ҫ���ͼ��
		else
		{
			if(book.getStatus()==0)			
				return 1;//���ɽ�			
			else 
			{
				book.setStatus(0);//����״̬�����Լ����
				book.setBcount(new Integer(Integer.valueOf(book.getBcount())+1).toString());
				boolean flag1=bookDao.updateBook(book);//����ͼ��
				Rent rent=new Rent(uid, book.getId(), new SimpleDateFormat("yyyy-MM-dd").format(new Date()), null);
				boolean flag2=rentDao.savaRent(rent);//���������Ϣ
				if(flag1&&flag2)				
					return 2;//����ɹ�				
				else
					return 3;//���ʧ��
			}
		}
	}

	@Override
	public int ReturnBook(int rid) {
		Rent rent=rentDao.queryRentById(rid);
		if(rent==null)
			return 1;//���벻��ȷ
		else if(rent.getReturntime()!=null)
			return 2;//ͼ���Ѿ��黹
		else
		{
			rent.setReturntime( new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			boolean flag1=rentDao.updateRent(rent);//���¼�¼��
			Book book=bookDao.queryBookById(rent.getBid());//�ҵ���Ӧ��ͼ��
			book.setStatus(1);//�ɽ�״̬
			boolean flag2=bookDao.updateBook(book);//����ͼ��
			if(flag1&&flag2)
				return 3;//�黹�ɹ�
			else
				return 4;//�黹ʧ��			
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
