package com.biz;

import java.util.List;

import com.entity.Rent2;

public interface RentBiz {
	public List<Rent2> QueryUserRent(String uname);//�鿴ָ���û��Ľ��ļ�¼
	public List<Rent2> QueryBookRent(String bname);//�鿴ָ��ͼ��Ľ��ļ�¼
	public List<Rent2> QueqyUserReturnRent(String uname);//�鿴ָ���û����ѹ黹��¼
	public List<Rent2> QueqyUserNotReturnRent(String uname);//�鿴ָ���û���δ�黹��¼
	public List<Rent2> QueqyAllRent();//�鿴���еĽ��ļ�¼

}
