package com.biz;

import java.util.List;

import com.entity.Book;

public interface BookBiz {
	public boolean AddBook(Book book);//���ͼ��
	public boolean DelBook(int bid);//ɾ��ͼ��
	public boolean ModifyBook(Book book);//�޸�ͼ��
	public List<Book> QueryAllBook();//��ѯ����ͼ����Ϣ
	public List<Book> QueryHotBook();//�鿴ǰ�屾���ܻ�ӭ��ͼ��
	public List<Book> QueryBookByName(String bname);//����ͼ����������ѯ
	public Book QueryBookById(int bid);//����ͼ��id����ѯ
	public int RentBook(int bid,int uid);//��ͼ��id���û�id������
	public int ReturnBook(int rid);//����Ĺ���
	public List<Book> CanRentBook();//�ɽ�ͼ��
	public List<Book> CanNotRentBook();//���ɽ�ͼ��
}
