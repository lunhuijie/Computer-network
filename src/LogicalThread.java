import javax.swing.*;
import java.io.*;
//import java.io.FileNotFoundException;
import java.text.*;
import java.util.Date;
import java.net.Socket;
/**
 * 服务器端逻辑线程;
 */
class Biao{
    String MAC;
    int Duankou;
    Date Time;
}//交换表
class Data{
    String YU_MAC;
    String MD_MAC;
    String data;
    int Duankou;
}//传入数据分解
public class LogicalThread extends Thread {
    int flag_finish;
    Socket socket_read;
    Socket socket_write;
    int Write_back_port;
    int port;
    int hang;//记录将要写入文件的行数
    String[] Wenjian;
    Socket[] socket;
    Socket socket1 = null;
    InputStream is = null;
    OutputStream os1 = null;
    static String serverIP = "127.0.0.1";
    JTextArea Databack;
    JTextArea Databack1;
    JTextArea Databack2;
    public LogicalThread(Socket[] socket, int port, Socket socket1, JTextArea Databcak, JTextArea Databcak1, JTextArea Databcak2) {
        this.Databack=Databcak;
        this.Databack1=Databcak1;
        this.Databack2=Databcak2;

        this.socket = socket;
        this.socket1=socket1;

        //this.socket_write = socket[port-99];
        this.port = port;
        this.hang = 0;
        this.flag_finish = 0;

        start(); //启动线程
    }

    public void run() {


        //初始化流
        try {
            this.socket_read = socket[port - 99];
            InputStream is = null;
            is = socket_read.getInputStream();


            //os = socket_write.getOutputStream();
            while (true) {
                //读取数据

                byte[] b = new byte[1024];
                this.Wenjian = new String[100];
                this.hang = 0;
                this.flag_finish = 0;
                this.Write_back_port = 0;


                //System.out.println("等待连接");
                try {

                    //System.out.println("进入try");

                    //System.out.println("数组已经开好，等待建立连接");

                    //System.out.println("链接建立完毕");
                    int n = is.read(b);
                    String strc = new String(b);
                    System.out.println(strc);
                    //逻辑处理
                    Databack.setText(strc);
                    System.out.println("读取到" + port + "号端口有信息接入");

                    byte[] response = logic(b, 0, n);


                    //反馈数据
                    //if();//如果需要广播则flag
                    //创建新的连接并返回数据
                    System.out.println("回写" + Write_back_port);
                    if (Write_back_port != 0) {
                        //直接传播
                        Databack1.setText("单播"+Write_back_port);
                        W_rite(Write_back_port, response);
                        //is.close();
                    }
                    System.out.println("连接结束");

                    //close();
                   /* try {
                        stop();
                        start();
                    }catch (Exception e){

                    }*/
                    /*os.write(response);*/
                    //}
                    flag_finish = 1;
                    //break;
                    //break;
                } catch (Exception e) {
                    //System.out.println("i am hera");
                }
                //break;

            }
        } catch (IOException e) {
            System.out.println("jianlasda");
            e.printStackTrace();

        }

        System.out.println("运行结束");
    }

    public void W_rite(int port_1, byte[] response) throws IOException //直接写回
    {

        this.socket_write = socket[port_1 - 99];
        //Databack1.setText("单播"+port_1);
        System.out.println("广播的端口号是：" + port_1);
        OutputStream os;
        os = socket_write.getOutputStream();
        os.write(response);
        //os.close();
        //服务器端IP地址
        // os.write();
       /* String serverIP = "127.0.0.1";
        socket = new Socket(serverIP,port_1);
        //发送数据
        os = socket.getOutputStream();
        os.write(response);*/
        //接收数据
    }

    public int Op_biao(Biao biao, Data data)//广播还是直接发送的查表//flag_last:最后一个的标志  ==0,则表明已经是表的最后一行了
    {
        System.out.println("表的MAC:" + biao.MAC + "数据的目的MAC:" + data.MD_MAC);
        if (data.MD_MAC.equals(biao.MAC))//找到了
        {
            if (biao.Duankou == data.Duankou) {
                System.out.println("找到了要发送的端口但是需要过滤" + biao.Duankou);
                Databack1.setText("自播");
                //来的端口号和数据要去的端口号相等，过滤
            } else {
                //不等，发送该数据
                System.out.println("找到了要发送的端口" + biao.Duankou);
                this.Write_back_port = biao.Duankou;//发送表中对应的端口
            }
            return 1;
        }
        System.out.println("没有找到要发送的端口" + biao.Duankou);
        return 0;
    }

    public long Sub_Time(Date d1) {
        try {
            DateFormat df = new SimpleDateFormat("yy/MM/dd-HH:mm:ss");
            //Date d1 = df.parse("2004-03-26 13:31:40");
            Date d2 = new Date();
            long diff = Math.abs(d2.getTime() - d1.getTime());//这样得到的差值是毫秒级别
            //long days = diff / (1000 * 60 * 60 * 24);

            long hours = (diff  / (1000 * 60 * 60));
            long minutes = (diff  - hours * (1000 * 60 * 60)) / (1000 * 60);
            System.out.print("差值时间是："+hours);
            return hours;
            //System.out.println("" + hours + "小时" + minutes + "分");
        }catch (Exception e)
        {

        }
        return 0;
    }

    public int Op_biao1(Biao biao, Data data)//自学习的查表
    {
        SimpleDateFormat df = new SimpleDateFormat("yy/MM/dd-HH:mm:ss");//设置日期格式
        //System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
        String Str = String.valueOf(data.Duankou);
        // out.write(data.YU_MAC+" "+Str+" "+df.format(new Date())+"\r\n");

        //if (data.Duankou != 103) {
            if (biao.Duankou == data.Duankou) {
                //Tod
                //更新时间
                //return 1;
                String biao_data = new String(data.YU_MAC + " " + biao.Duankou + " " + df.format(new Date()));
                Wenjian[hang] = biao_data;
                hang++;
                return 1;
                //来的端口号和数据要去的端口号相等，过滤
            } else {
                if (Sub_Time(biao.Time)<=2 ){
                    String biao_data = new String(biao.MAC + " " + biao.Duankou + " " + df.format(biao.Time));
                    Wenjian[hang] = biao_data;
                    hang++;

                }
                return 0;
            }

       // }
       // return 0;
    }

    public int Before_Op_biao_study(Data Get_Sth, String path) throws IOException {
        File file = new File(path);
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(file));
            System.out.println("自学习打开文件");
        } catch (Exception e) {
        }
        byte[] bytes = new byte[2048];
        //接受读取的内容(n就代表的相关数据，只不过是数字的形式)
        String str = null;
        //循环取出数据
        int flag_read = 0;
        while ((str = in.readLine()) != null) {//每次读取一行，创建一个去比较，如果符合条件则进行相关操作
            //转换成字符串
            //String str = new String(bytes,0,n,"GBK"); //#这里可以实现字节到字符串的转换，比较实用
            System.out.println("自学习文件读取str=" + str);
            Biao biao1 = new Biao();
            String[] sourceStrArray = str.split(" ");
            biao1.MAC = sourceStrArray[0];
            System.out.println("sourceStrArray[1]=" + sourceStrArray[1]);
            biao1.Duankou = Integer.valueOf(sourceStrArray[1]);
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd-HH:mm:ss");
                //   System.out.println(sourceStrArray[2]);
                biao1.Time = sdf.parse(sourceStrArray[2]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            int re_biao = Op_biao1(biao1, Get_Sth);//去进行对比，如果成功了就跳出循环；找到地址就返回1，没有需要自学习返回0
            if (re_biao == 1) {
                flag_read = 1;//设置标识符，不需要自学习，找到了
                // break;//跳出循环读，找到了就可以传了
            }
        }
        in.close();
        return flag_read;//返回1，代表查到了，不用追加
    }

    public int Op_biao_study(Data data, BufferedWriter out) throws IOException {

        //把数组的内容写到文件里去
        String wenben=new String();
        for (int T = 0; T < hang; T++) {
            System.out.println("写入文件：" + Wenjian[T]);
            wenben=wenben+"\r\n"+Wenjian[T];
            out.write(Wenjian[T] + "\r\n");
        }
        Databack2.setText(wenben);
        return 0;
    }

    public void S_Swrite(byte[] response) {//交换机
        System.out.println("写到另一个交换机0");
        if(this.port!=103)
        try {
            os1 = socket1.getOutputStream();
            System.out.println("写到另一个交换机1");
            os1.write(response);
    } catch(Exception e)
    {
        e.printStackTrace(); //打印异常信息
    }

    }


    public void Send_data(byte[] response) throws IOException {//广播
        this.Write_back_port=0;
        S_Swrite(response);
        for(int T=99;T<103;T++)
        {
            W_rite(T,response);
        }
        System.out.println("广播完成");
        //实现广播，把每一个端口的连接都发送一遍
    }
    /**
     * 关闭流和连接
     */
    private void close(){
        try{
            //关闭流和连接
           // os.close();
            //is.close();
            socket_read.close();
            socket_write.close();
        }catch(Exception e){}
    }

    /**
     * 逻辑处理方法,实现echo逻辑
     * @param b 客户端发送数据缓冲区
     * @param off 起始下标
     * @param len 有效数据长度
     * @return
     */
    private byte[] logic(byte[] b,int off,int len) throws IOException, ParseException {//创建链接
        System.out.println("逻辑处理该端口");
            byte[] response = new byte[len+1];
        //将有效数据拷贝到数组response中
        System.arraycopy(b, 0, response, 0, len+1);
        String ty=new String(response);
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

        Get_Sth.Duankou=this.port;

        String path="C:\\表1.txt";//读取的文件名
         File file = new File(path);
        int re1_biao=Before_Op_biao_study(Get_Sth,path);//进行查表，实现自学习

            if (re1_biao == 0&&Get_Sth.Duankou!=103)//没有查到
            {
                SimpleDateFormat df = new SimpleDateFormat("yy/MM/dd-HH:mm:ss");//设置日期格式
                //System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
                // String Str=String.valueOf(data.Duankou);
                // out.write(data.YU_MAC+" "+Str+" "+df.format(new Date())+"\r\n");
                Wenjian[hang] = new String(Get_Sth.YU_MAC + " " + Get_Sth.Duankou + " " + df.format(new Date()));
                hang++;
                System.out.println("加入数组：" + Wenjian[hang - 1]);
                FileWriter fw = new FileWriter(file.getAbsoluteFile());//追加文件
                BufferedWriter out = new BufferedWriter(fw);
                Op_biao_study(Get_Sth, out);
                out.close();
            } else {
                FileWriter fw = new FileWriter(file.getAbsoluteFile());//追加文件
                BufferedWriter out = new BufferedWriter(fw);
                Op_biao_study(Get_Sth, out);
                out.close();

            }

        //后边是查表
        //实现存储，对表格进行查询修改，发送数据。

        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(file));
            //System.out.println("wanlea");
        } catch (Exception e) {
        }
        byte[] bytes = new byte[2048];
        //接受读取的内容(n就代表的相关数据，只不过是数字的形式)

        String str = null;

        //循环取出数据
        int flag_read = 0;
        while ((str = in.readLine()) != null) {//每次读取一行，创建一个去比较，如果符合条件则进行相关操作
            //转换成字符串
            //String str = new String(bytes,0,n,"GBK"); //#这里可以实现字节到字符串的转换，比较实用
            // System.out.println("str="+str);
            Biao biao1 = new Biao();
            String[] sourceStrArray = str.split(" ");
            biao1.MAC = sourceStrArray[0];
            //System.out.println("sourceStrArray[1]="+sourceStrArray[1]);
            biao1.Duankou = Integer.valueOf(sourceStrArray[1]);
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd-HH:mm:ss");
                //   System.out.println(sourceStrArray[2]);
                biao1.Time = sdf.parse(sourceStrArray[2]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            int re_biao = Op_biao(biao1, Get_Sth);//去进行对比，如果成功了就跳出循环；找到地址就返回1，没有需要自学习返回0
            if (re_biao == 1) {
                flag_read = 1;//设置标识符，不需要广播，已经在子函数中调用回传了
                break;//跳出循环读，找到了就可以传了
            }
        }
        in.close();
        if(flag_read==0) {
            Databack1.setText("广播");
            Send_data(response);//广播
           /* FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);//追加文件
            BufferedWriter out = new BufferedWriter(fw);
            //没有找到
            Op_biao_study(Get_Sth,out);
            System.out.println("自学习完成");
            out.close();*/
        }
        else {
            System.out.println("已经找到数据表中内容");
        }
        //关闭流


        //查询文件表
      //  String ty=new String(response);
        //System.out.println("ty="+ty);
        return response;
    }
}