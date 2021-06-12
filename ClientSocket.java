package com.atguigu.ioupload.u2;

import org.hamcrest.core.Is;

import java.io.*;
import java.net.Socket;

/**
 * @author 里
 * @Data:2021/06/10/16:45
 * @Description:
 */
public class ClientSocket {
    public static void main(String[] args) {
        Socket socket = null;
        BufferedOutputStream bos = null;
        BufferedInputStream bis = null;
        try {
            //创建socket对象指定要连接的IP 以及端口
            socket = new Socket("127.0.0.1",9090);
            //创建输出流
            OutputStream os = socket.getOutputStream();
            //包装为高效输出流
            bos = new BufferedOutputStream(os);
            //创建高效读入流
            //创建一个数据流，获取文件 的名字
            DataOutputStream dos = new DataOutputStream(os);
            File file = new File("D:/1.jpg");
            String fileName = file.getName();
            //输出文件名
            dos.writeUTF(fileName);
            bis = new BufferedInputStream(new FileInputStream(file));
            //循环读取信息
            byte[] bs = new byte[1024];
            int len ;
            while ((len = bis.read(bs)) != -1){
                //写出数据
                bos.write(bs,0,len);
                bos.flush();
            }
            socket.shutdownOutput();
            //接收服务端反馈信息
            InputStream is = socket.getInputStream();
            len = is.read(bs);
            System.out.println(new String(bs,0,len));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null){
                //关流
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (socket!=null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
