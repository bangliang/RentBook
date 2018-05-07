package com.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.biz.BookBiz;
import com.biz.RentBiz;
import com.biz.impl.BookBizImpl;
import com.biz.impl.RentBizImpl;
import com.entity.Book;
import com.entity.Rent;
import com.entity.Rent2;
import com.entity.User;


public class UserBookRent extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8790539639265789433L;
	
	private JPanel paneltable=null;//用来保存Jtable的一个面板
	private JTable table=null;//声明Jtable
	private JPanel panelButton=null;//按钮面板
	private JButton btn_search=null;//查询按钮
	private JButton btn_return=null;//还书按钮
	private JButton btn_exit=null;//退出按钮
	private JComboBox<String> cb_type=null;//下拉列表框
	private JLabel lb_type=null;//类型标签
	
	private RentBiz rent2Biz=null;
	private List<Rent2> rent2List=null;
	private RentInfoTableModel infoTableModel=null;
	private User user=null;
	private BookBiz bookBiz=null;
	
	public UserBookRent(User user)
	{
		this.user=user;
		bookBiz=new BookBizImpl();
		rent2Biz=new RentBizImpl();
		init();
		btnListener();
	}
	
	private void init()
	{
		//窗体设置
		this.setTitle("图书借阅记录查询");//设置标题
		this.setSize(640, 540);//设置窗体大小
		this.setIconifiable(true);//窗口可最小化
		this.setClosable(true);//窗口可被关闭
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		rent2List=new ArrayList<Rent2>();
		table=new JTable();
		
		//让JTable绑定数据模型程序数据
		refreshTable(rent2List);
		
		paneltable=new JPanel(new BorderLayout());//创建左侧面板
		//给左侧面板设置边框
		paneltable.setBorder(BorderFactory.createTitledBorder
				(BorderFactory.createEtchedBorder(null,null),"本人借阅记录"));
		paneltable.add(table);
		this.add(paneltable,BorderLayout.CENTER);
		//设置右侧面板
		panelButton=new JPanel(new GridLayout(7,1,10,39));
		panelButton.setBorder(BorderFactory.createTitledBorder
				(BorderFactory.createEtchedBorder(null,null),"查询条件"));
		this.add(panelButton, BorderLayout.EAST);
		lb_type=new JLabel("查询类型");//初始化
		//添加控件
		panelButton.add(lb_type);
		cb_type=new JComboBox<String>(new String[] {"--选项--","全部借阅记录","已还图书","未还图书"});
		panelButton.add(cb_type);
		//初始化按钮并添加
		btn_search=new JButton("查询");
		panelButton.add(btn_search);
		btn_return=new JButton("还书");
		btn_return.setEnabled(false);//默认按钮不可用
		panelButton.add(btn_return);
		panelButton.add(new JLabel());
		panelButton.add(new JLabel());
		//添加退出窗口
		btn_exit=new JButton("退出");
		panelButton.add(btn_exit);
		this.setVisible(true);//窗口设置不可见
	}
	public void btnListener() {
		btn_exit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {//当鼠标点击时
				// TODO Auto-generated method stub
			   dispose();//仅退出当前视图
			}
		}); 
		btn_search.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int index=cb_type.getSelectedIndex();
				String content=user.getUname();
				
				//先清除数据，防止数据累加
				if(rent2List!=null)
					rent2List.clear();
				if(index==0) 
					JOptionPane.showInternalMessageDialog(
							UserBookRent.this, "请选择查询类型！");				
				else if(index==1) {
					rent2List=rent2Biz.QueryUserRent(content);
				}
				else if(index==2) {	
					rent2List=rent2Biz.QueqyUserReturnRent(content);
				}
				else if(index==3) {
					rent2List=rent2Biz.QueqyUserNotReturnRent(content);
				}	
							
				refreshTable(rent2List);
				if(index!=0&&rent2List.size()==0)
					JOptionPane.showInternalMessageDialog(
							UserBookRent.this, "没有您要查询的内容！");	
			
			}
		});
		cb_type.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				String item=e.getItem().toString();
				if(!item.equals("未还图书")) 
					btn_return.setEnabled(false);
								
			}			
		});
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {				
				//假设选择一行，则归还按钮可用
				if(table.getSelectedRow()!=-1) {
						btn_return.setEnabled(true);
				}
			}
		});
		btn_return.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {	
				int row=table.getSelectedRow();
				int rid=(int)table.getValueAt(row, 0);
				int flag=JOptionPane.showInternalConfirmDialog(UserBookRent.this, 
						"确认归还这本书？","确认信息",JOptionPane.YES_NO_OPTION);
				if(flag==JOptionPane.YES_OPTION) {
					int res=bookBiz.ReturnBook(rid);
					if(res==3) {
						JOptionPane.showInternalMessageDialog(
								UserBookRent.this, "归还成功！");						
						btn_search.doClick();
					}
					else
						JOptionPane.showInternalMessageDialog(
								UserBookRent.this, "归还失败！");
				}
				refreshTable(rent2List);
			}
			
		});
		
	}
	//创建数据模型
	private	class RentInfoTableModel implements TableModel{

			private List<Rent2> rent2List=null;
			public RentInfoTableModel(List<Rent2> rent2List) {
				this.rent2List=rent2List;
			}
			@Override
			public void addTableModelListener(TableModelListener l) {
				// TODO Auto-generated method stub
					
			}
			//得到列的数据类型
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				// TODO Auto-generated method stub
				return String.class;
				}
				//JTable数据的列数
				@Override
				public int getColumnCount() {
					// TODO Auto-generated method stub
					return 4;
				}
				//设置JTable显示的列名
				@Override
				public String getColumnName(int columnIndex) {
					// TODO Auto-generated method stub
					if(columnIndex==0)
						return "借阅编号";
					else if(columnIndex==1)
						return "书名";
					else if(columnIndex==2)
						return "借出时间";
					else if(columnIndex==3)	
						return "归还时间";
					else
						return "Error";
				}
				//JTable显示的数据行数
				@Override
				public int getRowCount() {
					// TODO Auto-generated method stub
					return rent2List.size();
				}
				//获取JTable中指定行指定单元格的数据
				@Override
				public Object getValueAt(int rowIndex, int columnIndex) {
					Rent2 rent2=rent2List.get(rowIndex);
					if(columnIndex==0)
						return rent2.getId();
					else if(columnIndex==1)
						return rent2.getBname();
					else if(columnIndex==2)
						return rent2.getRenttime();
					else if(columnIndex==3)
						return rent2.getReturntime();
					else
						return "Error";
				}
				//设置 单元格是否可编辑
				@Override
				public boolean isCellEditable(int rowIndex, int columnIndex) {
					// TODO Auto-generated method stub
					return false;
				}

				@Override
				public void removeTableModelListener(TableModelListener l) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
					// TODO Auto-generated method stub
					
				}
				
			}
		
			//刷新JTable并显示数据
			private void refreshTable(List<Rent2> rent2List) {
				infoTableModel=new RentInfoTableModel(rent2List);
				table.setModel(infoTableModel);
			}
}
