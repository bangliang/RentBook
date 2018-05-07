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
	
	private JPanel paneltable=null;//��������Jtable��һ�����
	private JTable table=null;//����Jtable
	private JPanel panelButton=null;//��ť���
	private JButton btn_search=null;//��ѯ��ť
	private JButton btn_return=null;//���鰴ť
	private JButton btn_exit=null;//�˳���ť
	private JComboBox<String> cb_type=null;//�����б��
	private JLabel lb_type=null;//���ͱ�ǩ
	
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
		//��������
		this.setTitle("ͼ����ļ�¼��ѯ");//���ñ���
		this.setSize(640, 540);//���ô����С
		this.setIconifiable(true);//���ڿ���С��
		this.setClosable(true);//���ڿɱ��ر�
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		rent2List=new ArrayList<Rent2>();
		table=new JTable();
		
		//��JTable������ģ�ͳ�������
		refreshTable(rent2List);
		
		paneltable=new JPanel(new BorderLayout());//����������
		//�����������ñ߿�
		paneltable.setBorder(BorderFactory.createTitledBorder
				(BorderFactory.createEtchedBorder(null,null),"���˽��ļ�¼"));
		paneltable.add(table);
		this.add(paneltable,BorderLayout.CENTER);
		//�����Ҳ����
		panelButton=new JPanel(new GridLayout(7,1,10,39));
		panelButton.setBorder(BorderFactory.createTitledBorder
				(BorderFactory.createEtchedBorder(null,null),"��ѯ����"));
		this.add(panelButton, BorderLayout.EAST);
		lb_type=new JLabel("��ѯ����");//��ʼ��
		//��ӿؼ�
		panelButton.add(lb_type);
		cb_type=new JComboBox<String>(new String[] {"--ѡ��--","ȫ�����ļ�¼","�ѻ�ͼ��","δ��ͼ��"});
		panelButton.add(cb_type);
		//��ʼ����ť�����
		btn_search=new JButton("��ѯ");
		panelButton.add(btn_search);
		btn_return=new JButton("����");
		btn_return.setEnabled(false);//Ĭ�ϰ�ť������
		panelButton.add(btn_return);
		panelButton.add(new JLabel());
		panelButton.add(new JLabel());
		//����˳�����
		btn_exit=new JButton("�˳�");
		panelButton.add(btn_exit);
		this.setVisible(true);//�������ò��ɼ�
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
				String content=user.getUname();
				
				//��������ݣ���ֹ�����ۼ�
				if(rent2List!=null)
					rent2List.clear();
				if(index==0) 
					JOptionPane.showInternalMessageDialog(
							UserBookRent.this, "��ѡ���ѯ���ͣ�");				
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
							UserBookRent.this, "û����Ҫ��ѯ�����ݣ�");	
			
			}
		});
		cb_type.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				String item=e.getItem().toString();
				if(!item.equals("δ��ͼ��")) 
					btn_return.setEnabled(false);
								
			}			
		});
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {				
				//����ѡ��һ�У���黹��ť����
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
						"ȷ�Ϲ黹�Ȿ�飿","ȷ����Ϣ",JOptionPane.YES_NO_OPTION);
				if(flag==JOptionPane.YES_OPTION) {
					int res=bookBiz.ReturnBook(rid);
					if(res==3) {
						JOptionPane.showInternalMessageDialog(
								UserBookRent.this, "�黹�ɹ���");						
						btn_search.doClick();
					}
					else
						JOptionPane.showInternalMessageDialog(
								UserBookRent.this, "�黹ʧ�ܣ�");
				}
				refreshTable(rent2List);
			}
			
		});
		
	}
	//��������ģ��
	private	class RentInfoTableModel implements TableModel{

			private List<Rent2> rent2List=null;
			public RentInfoTableModel(List<Rent2> rent2List) {
				this.rent2List=rent2List;
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
						return "���ı��";
					else if(columnIndex==1)
						return "����";
					else if(columnIndex==2)
						return "���ʱ��";
					else if(columnIndex==3)	
						return "�黹ʱ��";
					else
						return "Error";
				}
				//JTable��ʾ����������
				@Override
				public int getRowCount() {
					// TODO Auto-generated method stub
					return rent2List.size();
				}
				//��ȡJTable��ָ����ָ����Ԫ�������
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
			private void refreshTable(List<Rent2> rent2List) {
				infoTableModel=new RentInfoTableModel(rent2List);
				table.setModel(infoTableModel);
			}
}
