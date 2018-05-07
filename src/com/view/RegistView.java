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
	private JPanel panel_main=null;//主面板
	private JPanel panel1=null;
	private JPanel panel2=null;
	private JPanel panel3=null;
	private JPanel panel4=null;
	private JPanel panel5=null;
	private JLabel lb_name=null;//用户名
	private JLabel lb_init_pw=null;//初始密码
	private JLabel lb_confirm_pw=null;//确认密码
	private JTextField tf_uname=null;//用户名框
	private JPasswordField userPwInit=null;//初始密码框
	private JPasswordField userPwConfirm=null;//确认密码框
	private JButton btn_confirm=null;//确认按钮
	private JButton btn_back=null;//返回按钮
	private UserBiz userBiz=null;
	//调用
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
		btn_confirm=new JButton("确定提交");
		btn_back=new JButton("退出");
		lb_name=new JLabel("用户名：        ");
		lb_init_pw=new JLabel("初始化密码：");
		lb_confirm_pw=new JLabel("确认密码：   ");
		lb_name.setFont(new Font("微软雅黑",Font.BOLD,15));//设置字体
		lb_init_pw.setFont(new Font("微软雅黑",Font.BOLD,15));
		lb_confirm_pw.setFont(new Font("微软雅黑",Font.BOLD,15));
		panel_main=new JPanel(new GridLayout(5,1));//主面板		
		panel1=new JPanel();//第一行
		panel2=new JPanel();//第二行
		panel3=new JPanel();//第三行
		panel4=new JPanel();//第四行
		panel5=new JPanel();//第五行
		//控件添加到面板
		panel2.add(lb_name);
		panel2.add(tf_uname);
		panel3.add(lb_init_pw);
		panel3.add(userPwInit);
		panel4.add(lb_confirm_pw);
		panel4.add(userPwConfirm);
		panel5.add(btn_confirm);
		panel5.add(btn_back);
		//面板添加到主面板
		panel_main.add(panel1);
		panel_main.add(panel2);
		panel_main.add(panel3);
		panel_main.add(panel4);
		panel_main.add(panel5);
		//主面板添加到窗体
		this.getContentPane().add(panel_main);
		this.setTitle("用户注册");
		this.setSize(450, 260);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.getRootPane().setDefaultButton(btn_confirm);//默认获得焦点的按钮
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
				//获取用户名、密码和第二次输入密码
				String uname=tf_uname.getText().trim();
				String upassword1=String.valueOf(userPwInit.getPassword());
				String upassword2=String.valueOf(userPwConfirm.getPassword());
				int type=1;
				if(uname.equals(""))//用户名不能为空
				{
					JOptionPane.showMessageDialog(RegistView.this, "请输入用户名!");
					return;
				}
				if(upassword1.equals(""))//密码不能为空
				{
					JOptionPane.showMessageDialog(RegistView.this, "请输入密码!");
					return;
				}
				if(upassword2.equals(""))//密码不能为空
				{
					JOptionPane.showMessageDialog(RegistView.this, "请再次输入密码!");
					return;
				}
				//int flag=JOptionPane.showInternalConfirmDialog(RegistView.this, 
				//		"确认注册？","确认信息",JOptionPane.YES_NO_OPTION);	
				//if(flag==JOptionPane.YES_OPTION)
				//{
				if(upassword1!=upassword2) {
					JOptionPane.showMessageDialog(RegistView.this, "两次密码输入不一致!");
					return;
				}
					User user=new User();
					user.setUname(uname);
					user.setUpassword(upassword2);
					user.setType(type);
					int res=userBiz.regist(user);
					if(res==1)
						JOptionPane.showMessageDialog(
								RegistView.this, "用户名已存在！");
					else if(res==2)
						JOptionPane.showMessageDialog(
								RegistView.this, "注册成功！");
					else if(res==3)
						JOptionPane.showMessageDialog(
								RegistView.this, "注册失败！请联系管理员！");
				//}			
		}
	});
	}
}
