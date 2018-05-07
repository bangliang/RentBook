package com.view;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import com.biz.UserBiz;
import com.biz.impl.UserBizImpl;
import com.entity.Book;
import com.entity.User;
public class RegistView extends JFrame {
	private JPanel panel_main=null;//�����
	private JPanel panel1=null;
	private JPanel panel2=null;
	private JPanel panel3=null;
	private JPanel panel4=null;
	private JPanel panel5=null;
	private JLabel lb_name=null;//�û���
	private JLabel lb_init_pw=null;//��ʼ����
	private JLabel lb_confirm_pw=null;//ȷ������
	private JTextField tf_uname=null;//�û�����
	private JPasswordField userPwInit=null;//��ʼ�����
	private JPasswordField userPwConfirm=null;//ȷ�������
	private JButton btn_confirm=null;//ȷ�ϰ�ť
	private JButton btn_back=null;//���ذ�ť
	private UserBiz userBiz=null;
	//����
	public RegistView()
	{
		userBiz=new UserBizImpl();
		init();
		btnListener();
	}	
	private void init()
	{
		tf_uname=new JTextField(15);
		userPwInit=new JPasswordField(15);
		userPwConfirm=new JPasswordField(15);
		btn_confirm=new JButton("ȷ���ύ");
		btn_back=new JButton("�˳�");
		lb_name=new JLabel("�û�����        ");
		lb_init_pw=new JLabel("��ʼ�����룺");
		lb_confirm_pw=new JLabel("ȷ�����룺   ");
		lb_name.setFont(new Font("΢���ź�",Font.BOLD,15));//��������
		lb_init_pw.setFont(new Font("΢���ź�",Font.BOLD,15));
		lb_confirm_pw.setFont(new Font("΢���ź�",Font.BOLD,15));
		panel_main=new JPanel(new GridLayout(5,1));//�����		
		panel1=new JPanel();//��һ��
		panel2=new JPanel();//�ڶ���
		panel3=new JPanel();//������
		panel4=new JPanel();//������
		panel5=new JPanel();//������
		//�ؼ���ӵ����
		panel2.add(lb_name);
		panel2.add(tf_uname);
		panel3.add(lb_init_pw);
		panel3.add(userPwInit);
		panel4.add(lb_confirm_pw);
		panel4.add(userPwConfirm);
		panel5.add(btn_confirm);
		panel5.add(btn_back);
		//�����ӵ������
		panel_main.add(panel1);
		panel_main.add(panel2);
		panel_main.add(panel3);
		panel_main.add(panel4);
		panel_main.add(panel5);
		//�������ӵ�����
		this.getContentPane().add(panel_main);
		this.setTitle("�û�ע��");
		this.setSize(450, 260);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.getRootPane().setDefaultButton(btn_confirm);//Ĭ�ϻ�ý���İ�ť
		this.setVisible(true);
	}
	public void btnListener() {
		btn_back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new LoginView();
				RegistView.this.dispose();
			}
		});
		btn_confirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//��ȡ�û���������͵ڶ�����������
				String uname=tf_uname.getText().trim();
				String upassword1=String.valueOf(userPwInit.getPassword());
				String upassword2=String.valueOf(userPwConfirm.getPassword());
				int type=1;
				if(uname.equals(""))//�û�������Ϊ��
				{
					JOptionPane.showMessageDialog(RegistView.this, "�������û���!");
					return;
				}
				if(upassword1.equals(""))//���벻��Ϊ��
				{
					JOptionPane.showMessageDialog(RegistView.this, "����������!");
					return;
				}
				if(upassword2.equals(""))//���벻��Ϊ��
				{
					JOptionPane.showMessageDialog(RegistView.this, "���ٴ���������!");
					return;
				}
				//int flag=JOptionPane.showInternalConfirmDialog(RegistView.this, 
				//		"ȷ��ע�᣿","ȷ����Ϣ",JOptionPane.YES_NO_OPTION);	
				//if(flag==JOptionPane.YES_OPTION)
				//{
				if(upassword1!=upassword2) {
					JOptionPane.showMessageDialog(RegistView.this, "�����������벻һ��!");
					return;
				}
					User user=new User();
					user.setUname(uname);
					user.setUpassword(upassword2);
					user.setType(type);
					int res=userBiz.regist(user);
					if(res==1)
						JOptionPane.showMessageDialog(
								RegistView.this, "�û����Ѵ��ڣ�");
					else if(res==2)
						JOptionPane.showMessageDialog(
								RegistView.this, "ע��ɹ���");
					else if(res==3)
						JOptionPane.showMessageDialog(
								RegistView.this, "ע��ʧ�ܣ�����ϵ����Ա��");
				//}			
		}
	});
	}
}
