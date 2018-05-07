package com.dao;

import java.util.List;

import com.entity.*;

//����ӿڣ�����ά��
public interface BookDao {
	public boolean savaBook(Book book);//���ͼ��
	public boolean delBook(int bid);//ɾ��ָ��ͼ��
	public boolean updateBook(Book book);//�޸�ͼ��
	public List<Book> queryBook();//���÷��ͣ���ѯ����ͼ��
	public List<Book> queryBookByName(String bname);//��ѯָ�����ֵ�ͼ��
	public Book queryBookById(int bid);//��ѯָ����ŵ�ͼ��
	public List<Book> queryBookByStatus(int status);//��ѯָ��״̬���ɽ衢���ɽ裩��ͼ��
	public List<Book> queryHotBook(int number);//��ѯ����ͼƬ
}
