package com.view;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.biz.UserBiz;
import com.biz.impl.UserBizImpl;
import com.entity.User;

public class LoginView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4046064586349278126L;	
	private JPanel panel_main=null;//�����
	private JPanel panel_left=null;//������
	private JPanel panel_right=null;//�Ҳ����
	private JLabel lb_uname=null;//�û�����ǩ
	private JLabel lb_upassword=null;//�����ǩ
	private JLabel lb_type=null;//��¼���ͱ�ǩ
	private JLabel lb_img=null;//��ʾͼƬ�ı�ǩ
	private JTextField tf_uname=null;//�û����ı���
	private JPasswordField pf_password=null;//�����ı���	
	private JButton btn_login=null;//��¼��ť
	private JButton btn_regist=null;//ע�ᰴť
	private JComboBox<String> cb_type=null;//��¼���͵�������	
	private UserBiz userBiz=null;
	//����
	public LoginView() {
		init();
		userBiz=new UserBizImpl();
		registListener();
	}
	//ͨ������������ҵ��㷽��
	private void registListener()
	{
		//��¼��ť
		btn_login.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//��ȡ�û��������롢�������������
				String uname=tf_uname.getText().trim();
				String upassword=new String(pf_password.getPassword());
				int type=cb_type.getSelectedIndex()+1;//1-��ͨ�û���2-����Ա
				if(uname.equals(""))//�û�������Ϊ��
				{
					JOptionPane.showMessageDialog(LoginView.this, "�������û���!");
					return;
				}
				if(upassword.equals(""))//���벻��Ϊ��
				{
					JOptionPane.showMessageDialog(LoginView.this, "����������!");
					return;
				}
				User user=new User(uname,upassword,type);
				user=userBiz.login(user);//����ҵ��㷽��
				if(user!=null)
				{
					if(type == user.getType())
					{
						if(type == 1)
							new UserMainView(user);
						else
							new AdminMainView(user);
						LoginView.this.dispose();
					}
					else
						JOptionPane.showMessageDialog(LoginView.this, "�������ĵ�¼����!");					
				}
				else 	
					JOptionPane.showMessageDialog(LoginView.this, "������� �����������룡");				
			}			
		});
		//ע�ᰴť
		btn_regist.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new RegistView();
				LoginView.this.dispose();
			}
			
		});
	}	
	private void init() {
		//���ô���
		this.setSize(320, 220);//���ô����С
		this.setResizable(false);//���ɸı䴰���С
		this.setLocationRelativeTo(null);//���������ʾ
		this.setTitle("��¼---BOͼ�����ϵͳ");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//�رմ��幦��
		//��ʼ�����
		panel_main=new JPanel(new GridLayout(1,2));//��ʼ������壬����һ��
		panel_left=new JPanel();//Ĭ����ʽ���ֹ�����
		panel_right=new JPanel(new GridLayout(4,2,0,25));//��ʼ���Ҳ���壬�������У���Ԫ��ˮƽ����Ϊ0����ֱ����Ϊ10
		//��ʼ���ؼ�
		tf_uname=new JTextField(8);
		pf_password=new JPasswordField(8);
		cb_type=new JComboBox<String>(new String[] {"��ͨ�û�","����Ա"});
		btn_login=new JButton("��¼");
		btn_regist=new JButton("ע��");
		lb_uname=new JLabel("��  ��:",JLabel.CENTER);
		lb_upassword=new JLabel("��  ��:",JLabel.CENTER);
		lb_type=new JLabel("��  ��:",JLabel.CENTER);
		lb_img=new JLabel(new ImageIcon(ClassLoader.getSystemResource("photo/login.jpg")));
		//����Ӧ�ؼ��ŵ������ȥ
		panel_left.add(lb_img);
		panel_right.add(lb_uname);
		panel_right.add(tf_uname);
		panel_right.add(lb_upassword);
		panel_right.add(pf_password);
		panel_right.add(lb_type);
		panel_right.add(cb_type);
		panel_right.add(btn_login);
		panel_right.add(btn_regist);
		//������з������������
		panel_main.add(panel_left);
		panel_main.add(panel_right);
		//���������봰����
		this.getContentPane().add(panel_main);//��ȡ�����������
		this.pack();//�������壬��С���ð�����������
		this.setVisible(true);
	}
}
