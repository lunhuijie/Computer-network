
//package 第一版;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.io.*;

import javax.swing.*;
public class host2 extends JFrame implements ActionListener{
    static JTextField userText = new JTextField(20);
    JTextField passwordText = new JTextField(20);
    JTextField DataText = new JTextField(20);
    static JTextField DataBackText = new JTextField(20);
    JButton jb1,jb2;
    static Socket socket = null;
    static InputStream is = null;
    static OutputStream os = null;
    //服务器端IP地址
    static String serverIP = "127.0.0.1";
    //服务器端端口号
    static int port = 101;
    //JTextField jtf = null;

    // Cat cat = null;
    public host2() {
        jb1 = new JButton("mac_101");
        jb2 = new JButton("SEND");
        //cat = new Cat();
        JPanel panel = new JPanel();
        //this.add(panel);
        // jtf = new JTextField();
          this.add(jb1,BorderLayout.NORTH);
        this.add(panel,BorderLayout.CENTER);
        placeComponents(panel);
        jb2.setBounds(10, 120, 80, 25);
        this.add(jb2,BorderLayout.SOUTH);

        this.setBackground(Color.blue);

        //注册监听
        jb1.addActionListener(this);
        //jb1.addActionListener(cat); // 可以实现多个注册监听
        //制定action命令,传入事件响应
        jb1.setActionCommand("按钮A");

        jb2.addActionListener(this);
        // jb2.addActionListener(cat);
        jb2.setActionCommand("按钮B");

        this.setSize(300, 300); // 设置框体大小
        this.setLocation(90,150); //设置框体显示的位置
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置框体退出
        this.setVisible(true); // 显示框体


    }
    public void Send_Sth(String data) throws IOException {
        try {
            //OutputStream os;
            //os = socket.getOutputStream();
           // System.out.println("data="+data);
            os.write(data.getBytes());
            //os.close();
            //lianjie();
        }catch (Exception e){

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) { // 接收事件
//		// TODO Auto-generated method stub
        if (e.getActionCommand().equals("按钮A")) // 多态的思想
        {

        }
        if (e.getActionCommand().equals("按钮B")) // 多态的思想
        {

            String gt = new String(userText.getText()+" "+passwordText.getText()+" "+DataText.getText());//获取文本框内容
            try {
                Send_Sth(gt);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            // System.out.println("no");
        }
    }
    public static void lianjie(){
        try {
            //建立连接
            socket = new Socket(serverIP,port);
            //发送数据
             os = socket.getOutputStream();
            //  os.write(data.getBytes());
            //接收数据
            is = socket.getInputStream();

            // Scanner sc1=new Scanner(System.in);
            //String dat1=sc1.nextLine();
            while(true)
            {
                try {
                    byte[] b = new byte[1024];
                    int n = is.read(b);
                    //输出反馈数据
                    String ty=new String(b);
                    System.out.println("当前处理*"+ty+"*");
                    String[]  str_get= ty.split(" ");
                    Data Get_Sth=new Data();
                    Get_Sth.YU_MAC=str_get[0];
                    Get_Sth.MD_MAC=str_get[1];
                    try {
                        Get_Sth.data = str_get[2];
                    }catch (Exception e){
                        Get_Sth.data = null;
                    }
                    if(Get_Sth.MD_MAC.equals(userText.getText()))
                    DataBackText.setText(Get_Sth.data);
                    //System.out.println("服务器反馈：" + new String(b, 0, n));
                }catch (Exception e)
                {

                }
            }
            // is.close();

        } catch (Exception e) {
            e.printStackTrace(); //打印异常信息
        }finally{
            try {
                //关闭流和连接
                //is.close();
                //socket.close();
            } catch (Exception e2) {}
        }
    }
    public static void main(String[] args) {
        // 创建 JFrame 实例
        host2 d = new host2();
        lianjie();
        //发送内容
        //System.out.println("输入需要传输的数据");//自己的mac 目的的mac 需要传送的数据
        //Scanner sc=new Scanner("a d c");
        //String data=sc.nextLine();
        //String data = "Hello";


    }


    //String data = "Hello";





    private void placeComponents(JPanel panel) {

        /* 布局部分我们这边不多做介绍
         * 这边设置布局为 null
         */
        panel.setLayout(null);

        // 创建 JLabel
        JLabel userLabel = new JLabel("YU_MAC");
        /* 这个方法定义了组件的位置。
         * setBounds(x, y, width, height)
         * x 和 y 指定左上角的新位置，由 width 和 height 指定新的大小。
         */
        userLabel.setBounds(10,20,80,25);
        panel.add(userLabel);

        /*
         * 创建文本域用于用户输入
         */

        userText.setBounds(100,20,165,25);
        panel.add(userText);

        // 输入密码的文本域
        JLabel passwordLabel = new JLabel("MD_MAC");
        passwordLabel.setBounds(10,50,80,25);
        panel.add(passwordLabel);

        /*
         *这个类似用于输入的文本域
         * 但是输入的信息会以点号代替，用于包含密码的安全性
         */

        passwordText.setBounds(100,50,165,25);
        panel.add(passwordText);


        JLabel DataLabel = new JLabel("DATA");
        DataLabel.setBounds(10,80,80,25);
        panel.add(DataLabel);

        /*
         *这个类似用于输入的文本域
         * 但是输入的信息会以点号代替，用于包含密码的安全性
         */

        DataText.setBounds(100,80,165,25);
        panel.add(DataText);

        JLabel DataLabe2 = new JLabel("数据反馈");
        DataLabe2.setBounds(10,110,80,25);
        panel.add(DataLabe2);

        /*
         *这个类似用于输入的文本域
         * 但是输入的信息会以点号代替，用于包含密码的安全性
         */

        DataBackText.setBounds(100,110,165,25);
        panel.add(DataBackText);
        // 创建登录按钮
      /*  JButton loginButton = new JButton("login");
        loginButton.setBounds(10, 120, 80, 25);
        panel.add(loginButton);*/
    }

}

/*class Cat implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) { // 接收事件
        // TODO Auto-generated method stub
        if (e.getActionCommand().equals("按钮A")) // 多态的思想
            System.out.println("猫也按钮A");
        if (e.getActionCommand().equals("按钮B")) // 多态的思想
            System.out.println("猫也按钮B");
    }

}*/