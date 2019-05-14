import javax.swing.*;
import java.awt.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.io.*;
//import java.io.IOException;
/**
 * 支持多客户端的服务器端实现
 */
public class Serve_1 {
    static JTextField DataBackText = new JTextField(20);
    static JTextField DataBackText1 = new JTextField(20);
    static JTextArea DataBackText2 = new JTextArea(20,20);
    static JButton userLabel = new JButton("104");
    static JButton userLabe2 = new JButton("105");
    static JButton userLabe3 = new JButton("106");
    static JButton userLabe4 = new JButton("107");
    static JButton userLabe5 = new JButton("108");
    public static void MyFrame(){
        Graphics g = null;
        //super.paint(g);
        JFrame frame = new JFrame("二号交换机");
        // Setting the width and height of frame
        frame.setSize(500, 450);
        frame.setLocation(1100,320);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        /* 创建面板，这个类似于 HTML 的 div 标签
         * 我们可以创建多个面板并在 JFrame 中指定位置
         * 面板中我们可以添加文本字段，按钮及其他组件。
         */
        JPanel panel = new JPanel();
        // 添加面板
        frame.add(panel);
        placeComponents(panel);
        frame.setVisible(true);
    }
    private static void placeComponents(JPanel panel) {

        /* 布局部分我们这边不多做介绍
         * 这边设置布局为 null
         */

        panel.setLayout(null);


        userLabel.setBounds(420,100,60,25);
        //Component userLabe1;
        userLabel.setBackground(Color.RED);
        panel.add(userLabel);


        userLabe2.setBounds(420,150,60,25);
        userLabe2.setBackground(Color.RED);
        panel.add(userLabe2);


        userLabe3.setBounds(420,200,60,25);
        userLabe3.setBackground(Color.RED);
        panel.add(userLabe3);


        userLabe4.setBounds(420,250,60,25);
        panel.add(userLabe4);
        userLabe4.setBackground(Color.RED);


        userLabe5.setBounds(0,175,60,45);
        panel.add(userLabe5);
        userLabe5.setBackground(Color.RED);

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
            for(port=104;port<109;port++) {
                serverSocket[port - 104] = new ServerSocket(port);
                System.out.println("监听" + port + "端口服务器已启动");
            }
            // while (true) {
           // socket1 = new Socket(serverIP, 103);
            port=104;
            //获得连接

            int flag_host=0;
            String serverIP = "127.0.0.1";
            Socket socket1=null;
           /* System.out.print("输入开始");
            // BufferedReader br = new BufferedReader(new InputStreamReader(System.in)) ;
            Scanner sc=new Scanner(System.in);
            String data=sc.nextLine();*/
            try {
                socket1 = new Socket(serverIP, 103);
                flag_host=1;
            }catch (Exception e){

            }
           /* socket[4] = serverSocket[4].accept();
            System.out.print("交换机开启成功");*/
           port=108;
            while(true)
            {


                try {

                    socket[port - 104] = serverSocket[port - 104].accept();
                    switch (port){
                        case 104:userLabel.setBackground(Color.YELLOW);break;
                        case 105:userLabe2.setBackground(Color.YELLOW);break;
                        case 106:userLabe3.setBackground(Color.YELLOW);break;
                        case 107:userLabe4.setBackground(Color.YELLOW);break;
                        case 108:userLabe5.setBackground(Color.YELLOW);break;
                        default:break;

                    }
                    System.out.println("链接" + port + "端口成功");
                    Serve1_LogicalThread log = new Serve1_LogicalThread(socket, port,socket1,DataBackText,DataBackText1,DataBackText2);
                    System.out.println("处理完成");

                } catch (Exception e) {



                    //   }
                    //port=99;
                    // LogicalThread log = new LogicalThread(socket, 100);
                    //启动线程
                    // System.out.println("asd");

                }
                port--;
                if(port==103) {
                    port = 108;

                }
                //System.out.println(log.flag_finish);
                //if(log.flag_finish==1)break;
            }

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