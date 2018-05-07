package com.dao;

import java.util.List;

import com.entity.*;

public interface RentDao {
	public boolean savaRent(Rent rent);//����軹��¼
	public boolean updateRent(Rent rent);//�޸Ľ軹��¼
	public Rent queryRentById(int rid);//��ѯָ��id�Ľ軹��¼
	public List<Rent2> queryAll();//��ѯ����ͼ��Ľ軹��¼
	public List<Rent2> queryRentByUname(String uname);//��ѯָ���û����Ľ軹��¼
	public List<Rent2> queryRentByBname(String bname);//��ѯָ��ͼ�����Ľ軹��¼
	public List<Rent2> queryUserRentByReturnTime(boolean flag,String uname);//�鿴�û��黹��¼����/δ�黹��
	
	
}
