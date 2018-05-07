package com.view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.biz.BookBiz;
import com.entity.User;

public class AdminMainView extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -270943784636690351L;
	
	private JPanel main_panel=null;//主面板
	private JPanel wel_panel=null;//欢迎面板
	private JPanel btn_panel=null;//按钮组面板
	private JDesktopPane funDesktop=null;//桌面面板
	
	private JButton btn_admin_book=null;//管理员图书按钮
	private JButton btn_book_rent=null;//查看图书借阅记录按钮
	private JButton btn_rent_overtime=null;//管理借阅逾期记录按钮
	private JButton btn_advice=null;//管理意见反馈按钮
	private JButton btn_exit=null;//退出按钮
	
	private JLabel lb_wel=null;//欢迎标题
	private JLabel lb_photo=null;//存放图片的Label
	
	private User user=null;
	
	public AdminMainView(User user)
	{
		
		this.user=user;
		init();
		btnListener();
	}
	
	private void init()
	{
		main_panel=new JPanel(new BorderLayout());
		btn_panel=new JPanel(new GridLayout(11,1,0,15));
		btn_admin_book=new JButton("管理员图书操作");
		btn_book_rent=new JButton("图书借阅记录查询");
		btn_rent_overtime=new JButton("管理逾期记录");
		btn_advice=new JButton("处理意见反馈");
		btn_exit=new JButton("退出");
		//用来填充的标签控件
		btn_panel.add(new JLabel());
		btn_panel.add(new JLabel());
		btn_panel.add(btn_admin_book);
		btn_panel.add(btn_book_rent);
		btn_panel.add(btn_rent_overtime);
		btn_panel.add(btn_advice);
		btn_panel.add(btn_exit);
		btn_panel.add(new JLabel());
		btn_panel.add(new JLabel());
		//设置面板边框外观
		btn_panel.setBorder(BorderFactory.createTitledBorder
				(BorderFactory.createRaisedBevelBorder(),"使用功能区"));
		//初始化欢迎面板
		wel_panel=new JPanel();
		lb_wel=new JLabel(" 欢   迎   "+user.getUname()+"   使   用   BO   图   书   借   阅   系   统   ");
		lb_wel.setFont(new Font("微软雅黑",Font.BOLD,23));
		lb_wel.setForeground(Color.BLUE);
		wel_panel.add(lb_wel);
		//初始化图片显示面板
		funDesktop=new JDesktopPane();
		ImageIcon image=new ImageIcon("src/photo/UserMainView.jpg");
		lb_photo=new JLabel(image);
		lb_photo.setBounds(0, 0, image.getIconWidth(), image.getIconHeight());
		funDesktop.add(lb_photo, new Integer(Integer.MIN_VALUE));//将图片放入最底部
		
		main_panel.add(btn_panel,BorderLayout.EAST);
		main_panel.add(wel_panel,BorderLayout.NORTH);
		main_panel.add(funDesktop,BorderLayout.CENTER);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Thread(new DynaminThread()).start();
			}
		});
		
		this.setTitle("BO图书借阅系统--管理员版");
		this.getContentPane().add(main_panel);
		this.setSize(800, 650);
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
	
	}
	//监听器
	public void btnListener()
	{
		btn_admin_book.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				AdminBook qrb=new AdminBook();
				funDesktop.add(qrb);//把指定的视图添加到桌面容器中
				qrb.toFront();//视图显示在最前
			}
		});
		
		btn_book_rent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				AdminBookRent abr=new AdminBookRent();
				funDesktop.add(abr);//把指定的视图添加到桌面容器中
				abr.toFront();//视图显示在最前
			}
		});
		
		btn_exit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {//当鼠标点击时
				// TODO Auto-generated method stub
				System.exit(0);//退出
			}

			
		}); 
		
	}
	//线程类，让欢迎面板滚动
	private class DynaminThread implements Runnable
	{
		public void run()
		{
			while(true)
			{
				for(int i=1000;i>-750;i--)
				{
					try {
						Thread.sleep(10);
					}catch(InterruptedException e){
						e.printStackTrace();
					}
					lb_wel.setLocation(i, 5);
				}
			}
		}
	}
	
}
