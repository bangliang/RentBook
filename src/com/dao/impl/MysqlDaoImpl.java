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
		//与数据库建立连接
		public Connection openConnect()
		{
					Properties prop = new Properties();
					
					String driver = null;
					String url = null;
					String unm = null;
					String pwd = null;
					
					try{
						//加载配置文件
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
		//释放相应的资源
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
		//完成增、删、改数据库所有的操作，返回true或者false
		public boolean MysqlUpdate(String sql,List<Object> params) {//执行可能含有占位符的sql语句
			int res=0;//影响的行数
			Connection conn=null;
			PreparedStatement pstm=null;
			ResultSet rs=null;
			try {
				conn=openConnect();//建立数据库连接
				pstm=(PreparedStatement) conn.prepareStatement(sql);//装载数据库语句
				if(params!=null)
				{
					//假如有？，在执行之前把？占位符替换掉
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
		//使用泛型方法和反射机制进行封装，完成数据库查询功能，并返回结果集
		public <T> List<T> MysqlQuery(String sql,List<Object> params,Class<T> cls) throws InstantiationException, IllegalAccessException//利用泛型、执行可能含有占位符的sql语句、查询结果分装成实体类对象
, NoSuchFieldException, SecurityException
		{
			Connection conn=null;
			PreparedStatement pstm=null;
			ResultSet rs=null;
			List<T> data=new ArrayList<T>();//泛型数组
			try {
				conn=openConnect();//建立数据库连接
				pstm=(PreparedStatement) conn.prepareStatement(sql);//装载数据库语句
				if(params!=null)
				{
					//假如有？，在执行之前把？占位符替换掉
					for(int i=0;i<params.size();i++)
					{
						pstm.setObject(i+1, params.get(i));
					}
				}
				rs=pstm.executeQuery();
				
				//把查询出来的记录，封装成对应的实体类对象
				ResultSetMetaData rsd=(ResultSetMetaData) rs.getMetaData();//得到记录集元数据对象
				//通过此对象得到表的结构，包括列名、列 个数、列的数据类型
				while(rs.next())
				{
					T m = cls.newInstance();//产生泛型对象
					for(int i=0;i<rsd.getColumnCount();i++)//获取数据，封装到对应的实体类对象中
					{
						String col_name=rsd.getColumnName(i+1);//获得列名
						Object value=rs.getObject(col_name);//获得列对应的值
						Field field=cls.getDeclaredField((col_name).toLowerCase());//获得指定名字的属性
						field.setAccessible(true);//对私有属性通过反射机制设置可访问权
						field.set(m, value);//给对象的私有属性附上value值
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
