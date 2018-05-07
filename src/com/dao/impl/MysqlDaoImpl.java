package com.dao.impl;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.mysql.jdbc.Connection;
import java.lang.reflect.Field; 
import java.sql.PreparedStatement;
import com.mysql.jdbc.ResultSetMetaData;

public class MysqlDaoImpl {
		//�����ݿ⽨������
		public Connection openConnect()
		{
					Properties prop = new Properties();
					
					String driver = null;
					String url = null;
					String unm = null;
					String pwd = null;
					
					try{
						//���������ļ�
						prop.load(this.getClass().getClassLoader().getResourceAsStream("DBConfig.properties"));
						
						driver = prop.getProperty("driver");
						url = prop.getProperty("url");
						unm = prop.getProperty("username");
						pwd = prop.getProperty("password");
						
						Class.forName(driver);
						return (Connection) DriverManager.getConnection(url,unm,pwd);
						
					}catch(Exception e)
					{
						e.printStackTrace();
					}
					return null;
				

			

		}
		//�ͷ���Ӧ����Դ
		public void closeAll(ResultSet rs,PreparedStatement pstm,Connection conn){
			try{
					if(rs!=null)
					{
						rs.close();
					}
					if(conn!=null)
					{
						conn.close();
					}
					if(pstm!=null)
					{
						pstm.close();
					}
				}catch(Exception e){
					e.printStackTrace();
				}
		}
		//�������ɾ�������ݿ����еĲ���������true����false
		public boolean MysqlUpdate(String sql,List<Object> params) {//ִ�п��ܺ���ռλ����sql���
			int res=0;//Ӱ�������
			Connection conn=null;
			PreparedStatement pstm=null;
			ResultSet rs=null;
			try {
				conn=openConnect();//�������ݿ�����
				pstm=(PreparedStatement) conn.prepareStatement(sql);//װ�����ݿ����
				if(params!=null)
				{
					//�����У�����ִ��֮ǰ�ѣ�ռλ���滻��
					for(int i=0;i<params.size();i++)
					{
						pstm.setObject(i+1, params.get(i));
					}
				}
				res=pstm.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				closeAll(rs,pstm,conn);
			}
			return res>0?true:false;
		}
		//ʹ�÷��ͷ����ͷ�����ƽ��з�װ��������ݿ��ѯ���ܣ������ؽ����
		public <T> List<T> MysqlQuery(String sql,List<Object> params,Class<T> cls) throws InstantiationException, IllegalAccessException//���÷��͡�ִ�п��ܺ���ռλ����sql��䡢��ѯ�����װ��ʵ�������
, NoSuchFieldException, SecurityException
		{
			Connection conn=null;
			PreparedStatement pstm=null;
			ResultSet rs=null;
			List<T> data=new ArrayList<T>();//��������
			try {
				conn=openConnect();//�������ݿ�����
				pstm=(PreparedStatement) conn.prepareStatement(sql);//װ�����ݿ����
				if(params!=null)
				{
					//�����У�����ִ��֮ǰ�ѣ�ռλ���滻��
					for(int i=0;i<params.size();i++)
					{
						pstm.setObject(i+1, params.get(i));
					}
				}
				rs=pstm.executeQuery();
				
				//�Ѳ�ѯ�����ļ�¼����װ�ɶ�Ӧ��ʵ�������
				ResultSetMetaData rsd=(ResultSetMetaData) rs.getMetaData();//�õ���¼��Ԫ���ݶ���
				//ͨ���˶���õ���Ľṹ�������������� �������е���������
				while(rs.next())
				{
					T m = cls.newInstance();//�������Ͷ���
					for(int i=0;i<rsd.getColumnCount();i++)//��ȡ���ݣ���װ����Ӧ��ʵ���������
					{
						String col_name=rsd.getColumnName(i+1);//�������
						Object value=rs.getObject(col_name);//����ж�Ӧ��ֵ
						Field field=cls.getDeclaredField((col_name).toLowerCase());//���ָ�����ֵ�����
						field.setAccessible(true);//��˽������ͨ������������ÿɷ���Ȩ
						field.set(m, value);//�������˽�����Ը���valueֵ
					}
					data.add(m);
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				closeAll(rs,pstm,conn);
			}
			return data;
		}
}
