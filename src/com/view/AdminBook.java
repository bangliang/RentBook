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

//���ܵ������У���UserMainView����ʾ
public class AdminBook extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4076935420746291017L;
	
	private JPanel paneltable=null;//��������Jtable��һ�����
	private JTable table=null;//����Jtable
	private JPanel panelput=null;//�������
	private JPanel panelButton=null;//��ť���
	private JButton btn_search=null;//��ѯ��ť
	private JButton btn_add=null;//��Ӱ�ť
	private JButton btn_mod=null;//���İ�ť
	private JButton btn_del=null;//ɾ����ť
	private JButton btn_exit=null;//�˳���ť
	private JComboBox<String> cb_type=null;//�����б��
	private JTextField tf_int=null;//�����
	private JLabel lb_type=null;//���ͱ�ǩ
	private JLabel lb_name=null;//
	private JLabel lb_count=null;//
	private JLabel lb_status=null;//
	private JTextField tf_name=null;//
	private JTextField tf_count=null;//
	private JComboBox<String> cb_status=null;//
	
	private BookBiz bookBiz=null;
	private List<Book> bookList=null;
	private BookInfoTableModel infoTableModel=null;
	//��ʼ��������
	public AdminBook()
	{
		bookBiz=new BookBizImpl();
		init();
		btnListener();
	}
	
	public void btnListener() {
		btn_exit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {//�������ʱ
				// TODO Auto-generated method stub
				dispose();//���˳���ǰ��ͼ
			}
		}); 
		btn_add.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String bname=tf_name.getText().trim();
				String bcount=tf_count.getText().trim();
				int status=cb_status.getSelectedIndex();//0-���ɽ裬1-���
				if(bname.equals(""))
				{
					JOptionPane.showInternalMessageDialog(AdminBook.this, "������������");;
					return;
				}
				if(bcount.equals(""))
				{
					JOptionPane.showInternalMessageDialog(AdminBook.this, "��������������");
					return;
				}
				if(!BookUtil.isNumber(bcount))
				{
					JOptionPane.showInternalMessageDialog(AdminBook.this, "���������֣�");
					return;
				}
				int flag=JOptionPane.showInternalConfirmDialog(AdminBook.this, 
						"ȷ�����ͼ�飿","ȷ����Ϣ",JOptionPane.YES_NO_OPTION);
				if(flag==JOptionPane.YES_OPTION)
				{
					boolean res=bookBiz.AddBook(new Book(bname,bcount,status));
					if(res) {
						JOptionPane.showInternalMessageDialog(
								AdminBook.this, "��ӳɹ���");
						btn_search.doClick();
					}
					else
						JOptionPane.showInternalMessageDialog(
								AdminBook.this, "���ʧ�ܣ�����ϵ����Ա��");
				}
				refreshTable(bookList);
			}
			
		});
		btn_search.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int index=cb_type.getSelectedIndex();
				String content=tf_int.getText().trim();
				
				//��������ݣ���ֹ�����ۼ�
				if(bookList!=null)
					bookList.clear();
				if(index==0) 
					JOptionPane.showInternalMessageDialog(
							AdminBook.this, "��ѡ���ѯ���ͣ�");				
				else if(index==1) {
					bookList=bookBiz.QueryAllBook();
				}
				else if(index==2)
					bookList=bookBiz.QueryHotBook();
				else if(index==3) {
					if(index!=0&&content.equals(""))
						JOptionPane.showInternalMessageDialog(
								AdminBook.this, "�������ѯ���ݣ�");
					else
						bookList=bookBiz.QueryBookByName(content);
				}
					
				else if(index==4) {
					if(index!=0&&content.equals("")) 
						JOptionPane.showInternalMessageDialog(
								AdminBook.this, "�������ѯ���ݣ�");
					else {
						if(BookUtil.isNumber(content)) {
							Book book=bookBiz.QueryBookById(Integer.parseInt(content));
							if(book!=null) 
								bookList.add(book);
						}
						else
						{
							JOptionPane.showInternalMessageDialog(
									AdminBook.this, "������������֣�");
							return;
						}
					}
						
				}
				refreshTable(bookList);
				btn_del.setEnabled(false);
				btn_mod.setEnabled(false);
				if(index!=0&&bookList.size()==0)
					JOptionPane.showInternalMessageDialog(
							AdminBook.this, "û����Ҫ��ѯ�����ݣ�");
				
			}
			
		});
		cb_type.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				String item=e.getItem().toString();
				tf_int.setText("");//����ı�������
				if(item.equals("ȫ��ͼ��")) 
					tf_int.setEnabled(false);
				else
					tf_int.setEnabled(true);				
			}			
		});
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				//����ѡ��һ�У��޸ġ�ɾ����ť����
				if(table.getSelectedRow()!=-1) {
					btn_del.setEnabled(true);
					btn_mod.setEnabled(true);
				}
				int row=table.getSelectedRow();//�õ���ѡ�����е��±�
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
							AdminBook.this, "������������");
					return;
				}
				if(!BookUtil.isNumber(bcount)) {
					JOptionPane.showInternalMessageDialog(
							AdminBook.this, "���Ĵ������������֣�");
					return ;
				}
				int flag=JOptionPane.showInternalConfirmDialog(AdminBook.this, 
						"�Ƿ�ȷ���޸�?","ȷ����Ϣ",JOptionPane.YES_NO_OPTION);
				if(flag==JOptionPane.YES_OPTION) {
					int row=table.getSelectedRow();
					boolean res=bookBiz.ModifyBook(
							new Book((int)table.getValueAt(row, 0),bname,bcount,
									status));
					if(res) {
						JOptionPane.showInternalMessageDialog(
								AdminBook.this, "�޸ĳɹ���");
						btn_search.doClick();
					}
					else
						JOptionPane.showInternalMessageDialog(
							AdminBook.this, "�޸�ʧ�ܣ�����ϵ����Ա");
				}
				
			}
			
		});
		btn_del.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int row=table.getSelectedRow();
				int bid=(int) table.getValueAt(row, 0);
				int flag=JOptionPane.showInternalConfirmDialog(AdminBook.this, 
						"�Ƿ�ȷ��ɾ��?","ȷ����Ϣ",JOptionPane.YES_NO_OPTION);
				if(flag==JOptionPane.YES_OPTION) {
					boolean res=bookBiz.DelBook(bid);
					if(res) {
						JOptionPane.showInternalMessageDialog(
								AdminBook.this, "ɾ���ɹ���");
						btn_search.doClick();
					}
					else
						JOptionPane.showInternalMessageDialog(
							AdminBook.this, "ɾ��ʧ�ܣ�����ϵ����Ա");
				}
				
			}
			
		});
	}
	

	private void init()
	{
		//��������
		this.setTitle("����Աͼ�����");//���ñ���
		this.setSize(640, 540);//���ô����С
		this.setIconifiable(true);//���ڿ���С��
		this.setClosable(true);//���ڿɱ��ر�
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		bookList=new ArrayList<Book>();
		table=new JTable();
		
		//��JTable������ģ�ͳ�������
		refreshTable(bookList);
		
		paneltable=new JPanel(new BorderLayout());//�������
		//�����������ñ߿�
		paneltable.setBorder(BorderFactory.createTitledBorder
				(BorderFactory.createEtchedBorder(null,null),"ͼ���ѯ��Ϣ"));
		paneltable.add(new JScrollPane(table),BorderLayout.CENTER);//���������������
		
		this.add(paneltable, BorderLayout.CENTER);
		
		panelput=new JPanel(new GridLayout(1,6,5,0));
		panelput.setBorder(BorderFactory.createEtchedBorder
				());
		lb_name=new JLabel("������");
		panelput.add(lb_name);
		tf_name=new JTextField(8);
		panelput.add(tf_name);
		lb_count=new JLabel("���Ĵ�����");
		panelput.add(lb_count);
		tf_count=new JTextField(8);
		panelput.add(tf_count);
		lb_status=new JLabel("״̬��");
		panelput.add(lb_status);
		cb_status=new JComboBox<String>(new String[] {"�ѽ�","�ɽ�"});
		panelput.add(cb_status);
		//panelcenter.add(panelput,BorderLayout.SOUTH);
		//this.add(panelcenter, BorderLayout.CENTER);
		this.add(panelput, BorderLayout.SOUTH);
		//�����Ҳ����
		panelButton=new JPanel(new GridLayout(9,1,10,19));
		panelButton.setBorder(BorderFactory.createTitledBorder
				(BorderFactory.createEtchedBorder(null,null),"��ѯ����"));
		this.add(panelButton, BorderLayout.EAST);
		lb_type=new JLabel("��ѯ����");//��ʼ��
		//��ӿؼ�
		panelButton.add(lb_type);
		cb_type=new JComboBox<String>(new String[] {"--ѡ��--","ȫ��ͼ��","����ͼ��","��������ѯ","��ͼ���Ų�ѯ"});
		panelButton.add(cb_type);
		tf_int=new JTextField(10);
		panelButton.add(tf_int);
		//��ʼ����ť�����
		btn_search=new JButton("��ѯ");
		panelButton.add(btn_search);
		btn_add=new JButton("���ͼ��");
		btn_mod=new JButton("����ͼ��");
		btn_del=new JButton("ɾ��ͼ��");
		panelButton.add(btn_add);
		panelButton.add(btn_mod);
		panelButton.add(btn_del);
		panelButton.add(new JLabel());
		//����˳�����
		btn_exit=new JButton("�˳�");
		panelButton.add(btn_exit);
		this.setVisible(true);//�������ò��ɼ�
	}
	
	//��������ģ��
	private class BookInfoTableModel implements TableModel{

		private List<Book> bookList=null;
		public BookInfoTableModel(List<Book> bookList) {
			this.bookList=bookList;
			
		}
		@Override
		public void addTableModelListener(TableModelListener l) {
			// TODO Auto-generated method stub
			
		}
		//�õ��е���������
		@Override
		public Class<?> getColumnClass(int columnIndex) {
			// TODO Auto-generated method stub
			return String.class;
		}
		//JTable���ݵ�����
		@Override
		public int getColumnCount() {
			// TODO Auto-generated method stub
			return 4;
		}
		//����JTable��ʾ������
		@Override
		public String getColumnName(int columnIndex) {
			// TODO Auto-generated method stub
			if(columnIndex==0)
				return "ͼ����";
			else if(columnIndex==1)
				return "����";
			else if(columnIndex==2)
				return "�������";
			else if(columnIndex==3)	
				return "״̬";
			else
				return "Error";
		}
		//JTable��ʾ����������
		@Override
		public int getRowCount() {
			// TODO Auto-generated method stub
			return bookList.size();
		}
		//��ȡJTable��ָ����ָ����Ԫ�������
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
				return ""+(book.getStatus()==1?"�ɽ�":"�ѽ�");
			else
				return "Error";
		}
		//���� ��Ԫ���Ƿ�ɱ༭
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
	//ˢ��JTable����ʾ����
	private void refreshTable(List<Book> bookList) {
		infoTableModel=new BookInfoTableModel(bookList);
		table.setModel(infoTableModel);
	}
}
