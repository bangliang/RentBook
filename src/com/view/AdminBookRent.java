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
import com.biz.RentBiz;
import com.biz.impl.BookBizImpl;
import com.biz.impl.RentBizImpl;
import com.entity.Book;
import com.entity.Rent;
import com.entity.Rent2;
import com.util.BookUtil;


public class AdminBookRent extends JInternalFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8790539639265789433L;
	
	private JPanel paneltable=null;//��������Jtable��һ�����
	private JTable table=null;//����Jtable
	private JPanel panelButton=null;//��ť���
	private JButton btn_search=null;//��ѯ��ť
	private JButton btn_exit=null;//�˳���ť
	private JComboBox<String> cb_type=null;//�����б��
	private JLabel lb_type=null;//���ͱ�ǩ
	private JTextField tf_int=null;//�����ı���
	
	private RentBiz rent2Biz=null;
	private List<Rent2> rent2List=null;
	private RentInfoTableModel infoTableModel=null;
	
	public AdminBookRent()
	{
		rent2Biz=new RentBizImpl();
		init();
		btnListener();
	}
	
	private void init()
	{
		//��������
		this.setTitle("ͼ����ļ�¼��ѯ--����Ա��");//���ñ���
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
				(BorderFactory.createEtchedBorder(null,null),"ͼ����ļ�¼"));
		paneltable.add(new JScrollPane(table),BorderLayout.CENTER);
		this.add(paneltable,BorderLayout.CENTER);
		//�����Ҳ����
		panelButton=new JPanel(new GridLayout(6,1,5,40));
		panelButton.setBorder(BorderFactory.createTitledBorder
				(BorderFactory.createEtchedBorder(null,null),"��ѯ����"));
		this.add(panelButton, BorderLayout.EAST);
		lb_type=new JLabel("��ѯ����");//��ʼ��
		panelButton.add(lb_type);//��ӿؼ�
		cb_type=new JComboBox<String>(new String[] {"--ѡ��--","ȫ�����ļ�¼","ָ���û����ļ�¼","ָ���������ļ�¼"});
		panelButton.add(cb_type);
		//��ʼ����ť�����
		tf_int=new JTextField(10);
		panelButton.add(tf_int);
		btn_search=new JButton("��ѯ");
		panelButton.add(btn_search);
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
				String content=tf_int.getText().trim();
				
				//��������ݣ���ֹ�����ۼ�
				if(rent2List!=null)
					rent2List.clear();
				if(index==0) 
					JOptionPane.showInternalMessageDialog(
							AdminBookRent.this, "��ѡ���ѯ���ͣ�");				
				if(index==1) {
					rent2List=rent2Biz.QueqyAllRent();
				}
				else if(index==2) {	
					if(content.equals(""))
						JOptionPane.showInternalMessageDialog(
								AdminBookRent.this, "�������ѯ���ݣ�");
					else
						rent2List=rent2Biz.QueryUserRent(content);
				}
				else if(index==3) {
					if(content.equals(""))
						JOptionPane.showInternalMessageDialog(
								AdminBookRent.this, "�������ѯ���ݣ�");
					else
						rent2List=rent2Biz.QueryBookRent(content);
				}	
							
				refreshTable(rent2List);	
				if(index!=0&&rent2List.size()==0)
				{
						JOptionPane.showInternalMessageDialog(
								AdminBookRent.this, "û����Ҫ��ѯ�����ݣ�");
				}
			}
		});
		cb_type.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				String item=e.getItem().toString();
				tf_int.setText("");//����ı�������
				if(item.equals("ȫ�����ļ�¼")) 
					tf_int.setEnabled(false);
				else
					tf_int.setEnabled(true);				
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
				return 5;
			}
			//����JTable��ʾ������
			@Override
			public String getColumnName(int columnIndex) {
				// TODO Auto-generated method stub
				if(columnIndex==0)
					return "���ı��";
				else if(columnIndex==1)	
					return "������";
				else if(columnIndex==2)	
					return "����";
				else if(columnIndex==3)	
					return "���ʱ��";
				else if(columnIndex==4)	
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
					return rent2.getUname();
				else if(columnIndex==2)
					return rent2.getBname();
				else if(columnIndex==3)
					return rent2.getRenttime();
				else if(columnIndex==4)
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
