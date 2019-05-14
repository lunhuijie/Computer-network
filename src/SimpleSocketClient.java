import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * 支持多客户端的服务器端实现
 */
public class SimpleSocketClient {

    static JTextArea DataBackText = new JTextArea(20,20);
    static JTextArea DataBackText1 = new JTextArea(20,20);
    static JTextArea DataBackText2 = new JTextArea(20,20);
    static JButton userLabel = new JButton("99");
    static JButton userLabe2 = new JButton("100");
    static JButton userLabe3 = new JButton("101");
    static JButton userLabe4 = new JButton("102");
    static JButton userLabe5 = new JButton("103");
    static JButton userLabe6 = new JButton("连接交换机");
    public static void MyFrame(){
        Graphics g = null;
        //super.paint(g);
        JFrame frame = new JFrame("一号交换机");
        // Setting the width and height of frame
        frame.setSize(500, 450);
        frame.setLocation(400,320);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        /* 创建面板，这个类似于 HTML 的 div 标签
         * 我们可以创建多个面板并在 JFrame 中指定位置
         * 面板中我们可以添加文本字段，按钮及其他组件。
         */
        JPanel panel = new JPanel();
        // 添加面板
        frame.add(panel);
        userLabe6.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //this.goon=1;
                change();
            }
        });
        // jb2.addActionListener(cat);
        userLabe6.setActionCommand("连接交换机");
        placeComponents(panel);
        frame.setVisible(true);
    }
    public static void change(){
        System.out.print(goon);
        goon=1;
    }
    private static void placeComponents(JPanel panel) {



        panel.setLayout(null);


        userLabel.setBounds(0,100,60,25);
        //Component userLabe1;
        userLabel.setBackground(Color.RED);
        panel.add(userLabel);


        userLabe2.setBounds(0,150,60,25);
        userLabe2.setBackground(Color.RED);
        panel.add(userLabe2);


        userLabe3.setBounds(0,200,60,25);
        userLabe3.setBackground(Color.RED);
        panel.add(userLabe3);


        userLabe4.setBounds(0,250,60,25);
        panel.add(userLabe4);
        userLabe4.setBackground(Color.RED);


        userLabe5.setBounds(420,175,60,45);
        panel.add(userLabe5);
        userLabe5.setBackground(Color.RED);

        userLabe6.setBounds(200,350,100,25);
        userLabe6.setBackground(Color.GREEN);
        panel.add(userLabe6);


        DataBackText.setBounds(120,100,250,25);
        panel.add(DataBackText);
        DataBackText1.setBounds(120,155,250,25);
        panel.add(DataBackText1);
        DataBackText2.setBounds(120,210,250,100);
        panel.add(DataBackText2);
        // 创建登录按钮
       /* JButton loginButton = new JButton("login");
        loginButton.setBounds(10, 120, 80, 25);
        panel.add(loginButton);*/
    }
    //@Override
   /* public void actionPerformed(ActionEvent e) { // 接收事件
//		// TODO Auto-generated method stub
        if (e.getActionCommand().equals("按钮A")) // 多态的思想
        {

        }
        if (e.getActionCommand().equals("连接交换机")) // 多态的思想
        {


        }
    }*/
    public static int goon;
    public static void main(String[] args) {
        MyFrame();
        int port;
        ServerSocket[] serverSocket = new ServerSocket[10];
        Socket[] socket = new Socket[10];
        //监听端口号

        //int port = 100;
        try {
            //asd
            //建立连接
            for (port = 99; port < 104; port++) {
                serverSocket[port - 99] = new ServerSocket(port);
                System.out.println("监听" + port + "端口服务器已启动");
            }

            // while (true) {
            port = 99;

            //获得连接
            int flag_host = 0;
            String serverIP = "127.0.0.1";
            Socket socket1 = null;

            socket[4] = serverSocket[4].accept();

            System.out.print("交换机开启成功");
            /*System.out.print("输入开启下一个");
            // BufferedReader br = new BufferedReader(new InputStreamReader(System.in)) ;

            Scanner sc = new Scanner(System.in);
            String data = sc.nextLine();*/
            //goon=0;
            System.out.print(goon);
            int i=0;
            while(true)
            {

                System.out.print(goon);
               if(goon==1)
                    break;
            }
            userLabe5.setBackground(Color.YELLOW);
            try {
                socket1 = new Socket(serverIP, 108);
                System.out.println("socket1有值");
                LogicalThread log1 = new LogicalThread(socket, 103, socket1,DataBackText,DataBackText1,DataBackText2);
                flag_host = 1;
            } catch (Exception e) {

            }

            int flag_dk=0;
            while (true) {


                try {
                    System.out.print("ting");
                    socket[port - 99] = serverSocket[port - 99].accept();
                    switch (port){
                        case 99:userLabel.setBackground(Color.YELLOW);break;
                        case 100:userLabe2.setBackground(Color.YELLOW);break;
                        case 101:userLabe3.setBackground(Color.YELLOW);break;
                        case 102:userLabe4.setBackground(Color.YELLOW);break;
                        case 103:userLabe5.setBackground(Color.YELLOW);break;
                        default:break;

                    }
                    System.out.println("链接" + port + "端口成功");
                    LogicalThread log = new LogicalThread(socket, port,socket1,DataBackText,DataBackText1,DataBackText2);
                    flag_dk++;
                    System.out.println("处理完成");
                } catch (Exception e) {
                }
                port++;
                if (port == 104) {
                    port = 99;
                }
                //if(flag_dk==3)break;
            }
          /*  for(port=99;port<=103;port++)
            {
                LogicalThread log = new LogicalThread(socket, port, socket1);
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭连接
                //  serverSocket[port-99].close();
                //System.out.println("********asd");
            } catch (Exception e) {
            }
        }
    }
}



