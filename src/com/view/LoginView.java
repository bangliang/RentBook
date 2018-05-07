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
	private JPanel panel_main=null;//主面板
	private JPanel panel_left=null;//左侧面板
	private JPanel panel_right=null;//右侧面板
	private JLabel lb_uname=null;//用户名标签
	private JLabel lb_upassword=null;//密码标签
	private JLabel lb_type=null;//登录类型标签
	private JLabel lb_img=null;//显示图片的标签
	private JTextField tf_uname=null;//用户名文本框
	private JPasswordField pf_password=null;//密码文本框	
	private JButton btn_login=null;//登录按钮
	private JButton btn_regist=null;//注册按钮
	private JComboBox<String> cb_type=null;//登录类型的下拉框	
	private UserBiz userBiz=null;
	//调用
	public LoginView() {
		init();
		userBiz=new UserBizImpl();
		registListener();
	}
	//通过监听器调用业务层方法
	private void registListener()
	{
		//登录按钮
		btn_login.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//获取用户名和密码、下拉框里的类型
				String uname=tf_uname.getText().trim();
				String upassword=new String(pf_password.getPassword());
				int type=cb_type.getSelectedIndex()+1;//1-普通用户，2-管理员
				if(uname.equals(""))//用户名不能为空
				{
					JOptionPane.showMessageDialog(LoginView.this, "请输入用户名!");
					return;
				}
				if(upassword.equals(""))//密码不能为空
				{
					JOptionPane.showMessageDialog(LoginView.this, "请输入密码!");
					return;
				}
				User user=new User(uname,upassword,type);
				user=userBiz.login(user);//调用业务层方法
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
						JOptionPane.showMessageDialog(LoginView.this, "请检查您的登录类型!");					
				}
				else 	
					JOptionPane.showMessageDialog(LoginView.this, "密码错误 ！请重新输入！");				
			}			
		});
		//注册按钮
		btn_regist.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new RegistView();
				LoginView.this.dispose();
			}
			
		});
	}	
	private void init() {
		//设置窗体
		this.setSize(320, 220);//设置窗体大小
		this.setResizable(false);//不可改变窗体大小
		this.setLocationRelativeTo(null);//窗体居中显示
		this.setTitle("登录---BO图书借阅系统");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//关闭窗体功能
		//初始化面板
		panel_main=new JPanel(new GridLayout(1,2));//初始化主面板，两行一列
		panel_left=new JPanel();//默认流式布局管理器
		panel_right=new JPanel(new GridLayout(4,2,0,25));//初始化右侧面板，四行两列，单元格水平距离为0，垂直距离为10
		//初始化控件
		tf_uname=new JTextField(8);
		pf_password=new JPasswordField(8);
		cb_type=new JComboBox<String>(new String[] {"普通用户","管理员"});
		btn_login=new JButton("登录");
		btn_regist=new JButton("注册");
		lb_uname=new JLabel("用  户:",JLabel.CENTER);
		lb_upassword=new JLabel("密  码:",JLabel.CENTER);
		lb_type=new JLabel("类  型:",JLabel.CENTER);
		lb_img=new JLabel(new ImageIcon(ClassLoader.getSystemResource("photo/login.jpg")));
		//把相应控件放到面板上去
		panel_left.add(lb_img);
		panel_right.add(lb_uname);
		panel_right.add(tf_uname);
		panel_right.add(lb_upassword);
		panel_right.add(pf_password);
		panel_right.add(lb_type);
		panel_right.add(cb_type);
		panel_right.add(btn_login);
		panel_right.add(btn_regist);
		//主面板中放左右两个面板
		panel_main.add(panel_left);
		panel_main.add(panel_right);
		//把主面板放入窗体中
		this.getContentPane().add(panel_main);//获取窗体内容面板
		this.pack();//收缩窗体，大小正好包裹整个内容
		this.setVisible(true);
	}
}
