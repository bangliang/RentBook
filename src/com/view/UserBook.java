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


//���ܵ������У���UserMainView����ʾ
public class UserBook extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4076935420746291017L;
	
	private JPanel paneltable=null;//��������Jtable��һ�����
	private JTable table=null;//����Jtable
	private JPanel panelButton=null;//��ť���
	private JButton btn_search=null;//��ѯ��ť
	private JButton btn_rent=null;//���İ�ť
	private JButton btn_exit=null;//�˳���ť
	private JComboBox<String> cb_type=null;//�����б��
	private JTextField tf_int=null;//����
	private JLabel lb_type=null;//���ͱ�ǩ
	
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
			public void mouseClicked(MouseEvent e) {//�������ʱ
				// TODO Auto-generated method stub
				dispose();//���˳���ǰ��ͼ
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
							UserBook.this, "��ѡ���ѯ���ͣ�");				
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
								UserBook.this, "�������ѯ���ݣ�");
					else
						bookList=bookBiz.QueryBookByName(content);
				}
				refreshTable(bookList);
				if(index!=0&&bookList.size()==0)
					JOptionPane.showInternalMessageDialog(
							UserBook.this, "û����Ҫ��ѯ�����ݣ�");
				
			}
			
		});
		cb_type.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				String item=e.getItem().toString();
				tf_int.setText("");//����ı�������
				if(!item.equals("��������ѯ")) 
					tf_int.setEnabled(false);
				else
					tf_int.setEnabled(true);	
				if(item.equals("���ɽ�ͼ��"))
					btn_rent.setEnabled(false);
			}			
		});
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row=table.getSelectedRow();//�õ���ѡ�����е��±�
				String status=table.getValueAt(row, 3).toString();
				//����ѡ��һ�У�����ͼ��״̬Ϊ���ɽ衱������İ�ť����
				if(table.getSelectedRow()!=-1&&status.equals("�ɽ�")) {
					//if(status=="�ɽ�")
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
						"ȷ�Ͻ����Ȿ�飿","ȷ����Ϣ",JOptionPane.YES_NO_OPTION);
				if(flag==JOptionPane.YES_OPTION) {
					int res=bookBiz.RentBook(bid, user.getId());
					if(res==2) {
						JOptionPane.showInternalMessageDialog(
								UserBook.this, "���ĳɹ���");
						btn_rent.setEnabled(false);
						btn_search.doClick();
					}
						
					
					else
						JOptionPane.showInternalMessageDialog(
								UserBook.this, "����ʧ�ܣ�");
				}
				refreshTable(bookList);
			}
			
		});
		
	}
	
	private void init()
	{
		//��������
		this.setTitle("ͼ����Ϣ��ѯ");//���ñ���
		this.setSize(640, 540);//���ô����С
		this.setIconifiable(true);//���ڿ���С��
		this.setClosable(true);//���ڿɱ��ر�
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		bookList=new ArrayList<Book>();
		table=new JTable();
		
		//��JTable������ģ�ͳ�������
		refreshTable(bookList);
		
		paneltable=new JPanel(new BorderLayout());//����������
		//�����������ñ߿�
		paneltable.setBorder(BorderFactory.createTitledBorder
				(BorderFactory.createEtchedBorder(null,null),"��ѯ��Ϣ"));
		paneltable.add(new JScrollPane(table),BorderLayout.CENTER);//���������������
		this.add(paneltable,BorderLayout.CENTER);
		//�����Ҳ����
		panelButton=new JPanel(new GridLayout(8,1,10,30));
		panelButton.setBorder(BorderFactory.createTitledBorder
				(BorderFactory.createEtchedBorder(null,null),"��ѯ����"));
		this.add(panelButton, BorderLayout.EAST);
		lb_type=new JLabel("��ѯ����");//��ʼ��
		//��ӿؼ�
		panelButton.add(lb_type);
		cb_type=new JComboBox<String>(new String[] {"--ѡ��--","ȫ��ͼ��","����ͼ��","�ɽ�ͼ��","���ɽ�ͼ��","��������ѯ"});
		panelButton.add(cb_type);
		tf_int=new JTextField(10);
		panelButton.add(tf_int);
		//��ʼ����ť�����
		btn_search=new JButton("��ѯ");
		panelButton.add(btn_search);
		btn_rent=new JButton("����");
		btn_rent.setEnabled(false);//Ĭ�ϰ�ť������
		panelButton.add(btn_rent);
		panelButton.add(new JLabel());
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
