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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.biz.BookBiz;
import com.biz.impl.BookBizImpl;
import com.entity.Book;
import com.entity.User;
import com.util.BookUtil;


//不能单独运行，在UserMainView里显示
public class UserBook extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4076935420746291017L;
	
	private JPanel paneltable=null;//用来保存Jtable的一个面板
	private JTable table=null;//声明Jtable
	private JPanel panelButton=null;//按钮面板
	private JButton btn_search=null;//查询按钮
	private JButton btn_rent=null;//借阅按钮
	private JButton btn_exit=null;//退出按钮
	private JComboBox<String> cb_type=null;//下拉列表框
	private JTextField tf_int=null;//输入
	private JLabel lb_type=null;//类型标签
	
	private BookBiz bookBiz=null;
	private List<Book> bookList=null;
	private BookInfoTableModel infoTableModel=null;
	private User user=null;
	
	public UserBook(User user)
	{
		this.user=user;
		bookBiz=new BookBizImpl();
		init();
		btnListener();
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
				String content=tf_int.getText().trim();
				
				//先清除数据，防止数据累加
				if(bookList!=null)
					bookList.clear();
				if(index==0) 
					JOptionPane.showInternalMessageDialog(
							UserBook.this, "请选择查询类型！");				
				else if(index==1) {
					bookList=bookBiz.QueryAllBook();
				}
				else if(index==2)
					bookList=bookBiz.QueryHotBook();
				else if(index==3)
					bookList=bookBiz.CanRentBook();
				else if(index==4)
					bookList=bookBiz.CanNotRentBook();
				else if(index==5) {
					if(content.equals(""))
						JOptionPane.showInternalMessageDialog(
								UserBook.this, "请输入查询内容！");
					else
						bookList=bookBiz.QueryBookByName(content);
				}
				refreshTable(bookList);
				if(index!=0&&bookList.size()==0)
					JOptionPane.showInternalMessageDialog(
							UserBook.this, "没有您要查询的内容！");
				
			}
			
		});
		cb_type.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				String item=e.getItem().toString();
				tf_int.setText("");//清空文本框内容
				if(!item.equals("按书名查询")) 
					tf_int.setEnabled(false);
				else
					tf_int.setEnabled(true);	
				if(item.equals("不可借图书"))
					btn_rent.setEnabled(false);
			}			
		});
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row=table.getSelectedRow();//得到你选中那行的下标
				String status=table.getValueAt(row, 3).toString();
				//假设选择一行，并且图书状态为“可借”，则借阅按钮可用
				if(table.getSelectedRow()!=-1&&status.equals("可借")) {
					//if(status=="可借")
						btn_rent.setEnabled(true);
				}
			}
		});
		btn_rent.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int row=table.getSelectedRow();
				int bid=(int)table.getValueAt(row, 0);
				int flag=JOptionPane.showInternalConfirmDialog(UserBook.this, 
						"确认借阅这本书？","确认信息",JOptionPane.YES_NO_OPTION);
				if(flag==JOptionPane.YES_OPTION) {
					int res=bookBiz.RentBook(bid, user.getId());
					if(res==2) {
						JOptionPane.showInternalMessageDialog(
								UserBook.this, "借阅成功！");
						btn_rent.setEnabled(false);
						btn_search.doClick();
					}
						
					
					else
						JOptionPane.showInternalMessageDialog(
								UserBook.this, "借阅失败！");
				}
				refreshTable(bookList);
			}
			
		});
		
	}
	
	private void init()
	{
		//窗体设置
		this.setTitle("图书信息查询");//设置标题
		this.setSize(640, 540);//设置窗体大小
		this.setIconifiable(true);//窗口可最小化
		this.setClosable(true);//窗口可被关闭
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		bookList=new ArrayList<Book>();
		table=new JTable();
		
		//让JTable绑定数据模型程序数据
		refreshTable(bookList);
		
		paneltable=new JPanel(new BorderLayout());//创建左侧面板
		//给左侧面板设置边框
		paneltable.setBorder(BorderFactory.createTitledBorder
				(BorderFactory.createEtchedBorder(null,null),"查询信息"));
		paneltable.add(new JScrollPane(table),BorderLayout.CENTER);//带滚动的内容面板
		this.add(paneltable,BorderLayout.CENTER);
		//设置右侧面板
		panelButton=new JPanel(new GridLayout(8,1,10,30));
		panelButton.setBorder(BorderFactory.createTitledBorder
				(BorderFactory.createEtchedBorder(null,null),"查询条件"));
		this.add(panelButton, BorderLayout.EAST);
		lb_type=new JLabel("查询类型");//初始化
		//添加控件
		panelButton.add(lb_type);
		cb_type=new JComboBox<String>(new String[] {"--选项--","全部图书","热门图书","可借图书","不可借图书","按书名查询"});
		panelButton.add(cb_type);
		tf_int=new JTextField(10);
		panelButton.add(tf_int);
		//初始化按钮并添加
		btn_search=new JButton("查询");
		panelButton.add(btn_search);
		btn_rent=new JButton("借书");
		btn_rent.setEnabled(false);//默认按钮不可用
		panelButton.add(btn_rent);
		panelButton.add(new JLabel());
		panelButton.add(new JLabel());
		//添加退出窗口
		btn_exit=new JButton("退出");
		panelButton.add(btn_exit);
		this.setVisible(true);//窗口设置不可见
				
	
	}
	//创建数据模型
		private class BookInfoTableModel implements TableModel{

			private List<Book> bookList=null;
			public BookInfoTableModel(List<Book> bookList) {
				this.bookList=bookList;
				
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
					return "图书编号";
				else if(columnIndex==1)
					return "书名";
				else if(columnIndex==2)
					return "借出次数";
				else if(columnIndex==3)	
					return "状态";
				else
					return "Error";
			}
			//JTable显示的数据行数
			@Override
			public int getRowCount() {
				// TODO Auto-generated method stub
				return bookList.size();
			}
			//获取JTable中指定行指定单元格的数据
			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {
				Book book=bookList.get(rowIndex);
				if(columnIndex==0)
					return book.getId();
				else if(columnIndex==1)
					return book.getBname();
				else if(columnIndex==2)
					return book.getBcount();
				else if(columnIndex==3)
					return ""+(book.getStatus()==1?"可借":"已借");
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
		private void refreshTable(List<Book> bookList) {
			infoTableModel=new BookInfoTableModel(bookList);
			table.setModel(infoTableModel);
		}
}
