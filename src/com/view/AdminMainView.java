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
	
	private JPanel main_panel=null;//�����
	private JPanel wel_panel=null;//��ӭ���
	private JPanel btn_panel=null;//��ť�����
	private JDesktopPane funDesktop=null;//�������
	
	private JButton btn_admin_book=null;//����Աͼ�鰴ť
	private JButton btn_book_rent=null;//�鿴ͼ����ļ�¼��ť
	private JButton btn_rent_overtime=null;//����������ڼ�¼��ť
	private JButton btn_advice=null;//�������������ť
	private JButton btn_exit=null;//�˳���ť
	
	private JLabel lb_wel=null;//��ӭ����
	private JLabel lb_photo=null;//���ͼƬ��Label
	
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
		btn_admin_book=new JButton("����Աͼ�����");
		btn_book_rent=new JButton("ͼ����ļ�¼��ѯ");
		btn_rent_overtime=new JButton("�������ڼ�¼");
		btn_advice=new JButton("�����������");
		btn_exit=new JButton("�˳�");
		//�������ı�ǩ�ؼ�
		btn_panel.add(new JLabel());
		btn_panel.add(new JLabel());
		btn_panel.add(btn_admin_book);
		btn_panel.add(btn_book_rent);
		btn_panel.add(btn_rent_overtime);
		btn_panel.add(btn_advice);
		btn_panel.add(btn_exit);
		btn_panel.add(new JLabel());
		btn_panel.add(new JLabel());
		//�������߿����
		btn_panel.setBorder(BorderFactory.createTitledBorder
				(BorderFactory.createRaisedBevelBorder(),"ʹ�ù�����"));
		//��ʼ����ӭ���
		wel_panel=new JPanel();
		lb_wel=new JLabel(" ��   ӭ   "+user.getUname()+"   ʹ   ��   BO   ͼ   ��   ��   ��   ϵ   ͳ   ");
		lb_wel.setFont(new Font("΢���ź�",Font.BOLD,23));
		lb_wel.setForeground(Color.BLUE);
		wel_panel.add(lb_wel);
		//��ʼ��ͼƬ��ʾ���
		funDesktop=new JDesktopPane();
		ImageIcon image=new ImageIcon("src/photo/UserMainView.jpg");
		lb_photo=new JLabel(image);
		lb_photo.setBounds(0, 0, image.getIconWidth(), image.getIconHeight());
		funDesktop.add(lb_photo, new Integer(Integer.MIN_VALUE));//��ͼƬ������ײ�
		
		main_panel.add(btn_panel,BorderLayout.EAST);
		main_panel.add(wel_panel,BorderLayout.NORTH);
		main_panel.add(funDesktop,BorderLayout.CENTER);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Thread(new DynaminThread()).start();
			}
		});
		
		this.setTitle("BOͼ�����ϵͳ--����Ա��");
		this.getContentPane().add(main_panel);
		this.setSize(800, 650);
		this.setVisible(true);
		this.setResizable(false);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
	
	}
	//������
	public void btnListener()
	{
		btn_admin_book.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				AdminBook qrb=new AdminBook();
				funDesktop.add(qrb);//��ָ������ͼ��ӵ�����������
				qrb.toFront();//��ͼ��ʾ����ǰ
			}
		});
		
		btn_book_rent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				AdminBookRent abr=new AdminBookRent();
				funDesktop.add(abr);//��ָ������ͼ��ӵ�����������
				abr.toFront();//��ͼ��ʾ����ǰ
			}
		});
		
		btn_exit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {//�������ʱ
				// TODO Auto-generated method stub
				System.exit(0);//�˳�
			}

			
		}); 
		
	}
	//�߳��࣬�û�ӭ������
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
