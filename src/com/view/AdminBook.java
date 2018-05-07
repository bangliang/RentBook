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
//import com.entity.Book;
import com.util.BookUtil;

//不能单独运行，在UserMainView里显示
public class AdminBook extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4076935420746291017L;
	
	private JPanel paneltable=null;//用来保存Jtable的一个面板
	private JTable table=null;//声明Jtable
	private JPanel panelput=null;//输入面板
	private JPanel panelButton=null;//按钮面板
	private JButton btn_search=null;//查询按钮
	private JButton btn_add=null;//添加按钮
	private JButton btn_mod=null;//更改按钮
	private JButton btn_del=null;//删除按钮
	private JButton btn_exit=null;//退出按钮
	private JComboBox<String> cb_type=null;//下拉列表框
	private JTextField tf_int=null;//输入框
	private JLabel lb_type=null;//类型标签
	private JLabel lb_name=null;//
	private JLabel lb_count=null;//
	private JLabel lb_status=null;//
	private JTextField tf_name=null;//
	private JTextField tf_count=null;//
	private JComboBox<String> cb_status=null;//
	
	private BookBiz bookBiz=null;
	private List<Book> bookList=null;
	private BookInfoTableModel infoTableModel=null;
	//初始化构造器
	public AdminBook()
	{
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
		btn_add.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String bname=tf_name.getText().trim();
				String bcount=tf_count.getText().trim();
				int status=cb_status.getSelectedIndex();//0-不可借，1-借出
				if(bname.equals(""))
				{
					JOptionPane.showInternalMessageDialog(AdminBook.this, "请输入书名！");;
					return;
				}
				if(bcount.equals(""))
				{
					JOptionPane.showInternalMessageDialog(AdminBook.this, "请输入借出次数！");
					return;
				}
				if(!BookUtil.isNumber(bcount))
				{
					JOptionPane.showInternalMessageDialog(AdminBook.this, "请输入数字！");
					return;
				}
				int flag=JOptionPane.showInternalConfirmDialog(AdminBook.this, 
						"确认添加图书？","确认信息",JOptionPane.YES_NO_OPTION);
				if(flag==JOptionPane.YES_OPTION)
				{
					boolean res=bookBiz.AddBook(new Book(bname,bcount,status));
					if(res) {
						JOptionPane.showInternalMessageDialog(
								AdminBook.this, "添加成功！");
						btn_search.doClick();
					}
					else
						JOptionPane.showInternalMessageDialog(
								AdminBook.this, "添加失败！请联系管理员！");
				}
				refreshTable(bookList);
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
							AdminBook.this, "请选择查询类型！");				
				else if(index==1) {
					bookList=bookBiz.QueryAllBook();
				}
				else if(index==2)
					bookList=bookBiz.QueryHotBook();
				else if(index==3) {
					if(index!=0&&content.equals(""))
						JOptionPane.showInternalMessageDialog(
								AdminBook.this, "请输入查询内容！");
					else
						bookList=bookBiz.QueryBookByName(content);
				}
					
				else if(index==4) {
					if(index!=0&&content.equals("")) 
						JOptionPane.showInternalMessageDialog(
								AdminBook.this, "请输入查询内容！");
					else {
						if(BookUtil.isNumber(content)) {
							Book book=bookBiz.QueryBookById(Integer.parseInt(content));
							if(book!=null) 
								bookList.add(book);
						}
						else
						{
							JOptionPane.showInternalMessageDialog(
									AdminBook.this, "编号请输入数字！");
							return;
						}
					}
						
				}
				refreshTable(bookList);
				btn_del.setEnabled(false);
				btn_mod.setEnabled(false);
				if(index!=0&&bookList.size()==0)
					JOptionPane.showInternalMessageDialog(
							AdminBook.this, "没有您要查询的内容！");
				
			}
			
		});
		cb_type.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				String item=e.getItem().toString();
				tf_int.setText("");//清空文本框内容
				if(item.equals("全部图书")) 
					tf_int.setEnabled(false);
				else
					tf_int.setEnabled(true);				
			}			
		});
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				//假设选择一行，修改、删除按钮可用
				if(table.getSelectedRow()!=-1) {
					btn_del.setEnabled(true);
					btn_mod.setEnabled(true);
				}
				int row=table.getSelectedRow();//得到你选中那行的下标
				String bname=table.getValueAt(row, 1).toString();
				String bcount=table.getValueAt(row, 2).toString();
				String status=table.getValueAt(row, 3).toString();
				tf_name.setText(bname);
				tf_count.setText(bcount);
				cb_status.setSelectedItem(status);
			}
		});
		btn_mod.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String bname=tf_name.getText().trim();
				String bcount=tf_count.getText().trim();
				int status=cb_status.getSelectedIndex();
				if(bname.equals("")) {
					JOptionPane.showInternalMessageDialog(
							AdminBook.this, "请输入书名！");
					return;
				}
				if(!BookUtil.isNumber(bcount)) {
					JOptionPane.showInternalMessageDialog(
							AdminBook.this, "借阅次数请输入数字！");
					return ;
				}
				int flag=JOptionPane.showInternalConfirmDialog(AdminBook.this, 
						"是否确认修改?","确认信息",JOptionPane.YES_NO_OPTION);
				if(flag==JOptionPane.YES_OPTION) {
					int row=table.getSelectedRow();
					boolean res=bookBiz.ModifyBook(
							new Book((int)table.getValueAt(row, 0),bname,bcount,
									status));
					if(res) {
						JOptionPane.showInternalMessageDialog(
								AdminBook.this, "修改成功！");
						btn_search.doClick();
					}
					else
						JOptionPane.showInternalMessageDialog(
							AdminBook.this, "修改失败！请联系管理员");
				}
				
			}
			
		});
		btn_del.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int row=table.getSelectedRow();
				int bid=(int) table.getValueAt(row, 0);
				int flag=JOptionPane.showInternalConfirmDialog(AdminBook.this, 
						"是否确认删除?","确认信息",JOptionPane.YES_NO_OPTION);
				if(flag==JOptionPane.YES_OPTION) {
					boolean res=bookBiz.DelBook(bid);
					if(res) {
						JOptionPane.showInternalMessageDialog(
								AdminBook.this, "删除成功！");
						btn_search.doClick();
					}
					else
						JOptionPane.showInternalMessageDialog(
							AdminBook.this, "删除失败！请联系管理员");
				}
				
			}
			
		});
	}
	

	private void init()
	{
		//窗体设置
		this.setTitle("管理员图书操作");//设置标题
		this.setSize(640, 540);//设置窗体大小
		this.setIconifiable(true);//窗口可最小化
		this.setClosable(true);//窗口可被关闭
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		bookList=new ArrayList<Book>();
		table=new JTable();
		
		//让JTable绑定数据模型程序数据
		refreshTable(bookList);
		
		paneltable=new JPanel(new BorderLayout());//创建面板
		//给左侧面板设置边框
		paneltable.setBorder(BorderFactory.createTitledBorder
				(BorderFactory.createEtchedBorder(null,null),"图书查询信息"));
		paneltable.add(new JScrollPane(table),BorderLayout.CENTER);//带滚动的内容面板
		
		this.add(paneltable, BorderLayout.CENTER);
		
		panelput=new JPanel(new GridLayout(1,6,5,0));
		panelput.setBorder(BorderFactory.createEtchedBorder
				());
		lb_name=new JLabel("书名：");
		panelput.add(lb_name);
		tf_name=new JTextField(8);
		panelput.add(tf_name);
		lb_count=new JLabel("借阅次数：");
		panelput.add(lb_count);
		tf_count=new JTextField(8);
		panelput.add(tf_count);
		lb_status=new JLabel("状态：");
		panelput.add(lb_status);
		cb_status=new JComboBox<String>(new String[] {"已借","可借"});
		panelput.add(cb_status);
		//panelcenter.add(panelput,BorderLayout.SOUTH);
		//this.add(panelcenter, BorderLayout.CENTER);
		this.add(panelput, BorderLayout.SOUTH);
		//设置右侧面板
		panelButton=new JPanel(new GridLayout(9,1,10,19));
		panelButton.setBorder(BorderFactory.createTitledBorder
				(BorderFactory.createEtchedBorder(null,null),"查询条件"));
		this.add(panelButton, BorderLayout.EAST);
		lb_type=new JLabel("查询类型");//初始化
		//添加控件
		panelButton.add(lb_type);
		cb_type=new JComboBox<String>(new String[] {"--选项--","全部图书","热门图书","按书名查询","按图书编号查询"});
		panelButton.add(cb_type);
		tf_int=new JTextField(10);
		panelButton.add(tf_int);
		//初始化按钮并添加
		btn_search=new JButton("查询");
		panelButton.add(btn_search);
		btn_add=new JButton("添加图书");
		btn_mod=new JButton("更改图书");
		btn_del=new JButton("删除图书");
		panelButton.add(btn_add);
		panelButton.add(btn_mod);
		panelButton.add(btn_del);
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
