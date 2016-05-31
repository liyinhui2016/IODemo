package com.ctg.io.io;

import com.ctg.io.comp.IIOSession;
import com.ctg.io.comp.INioSession;
import com.ctg.io.comp.IOSession;
import com.ctg.io.exception.TryCatchFinally;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.Map;

/**
 * Created by liyh on 2016/5/18.
 */
public class Server {


    int port  = 1024;


    /**
     * 存贮session
     */
    private Map<Socket,IIOSession> sessionMap = null;

    public  void start() throws IOException {
        //綁定端口
        ServerSocket serverSocket = new ServerSocket(1024);
        while (true){
            //listen
            Socket socket = serverSocket.accept();
            //一个客户端，一个线程
            new Thread(()->{
                byte [] bs = new byte[1024];
                TryCatchFinally.handle(()->{
                    IIOSession session = new IOSession(socket);
                    InputStream in = session.socket().getInputStream();
                    OutputStream out = session.socket().getOutputStream();
                    while (true){
                        //读取客户端内容
                        int len = in.read(bs);
                        byte [] msg = (String.format("server rec : %s , current session is : %s,Current thread id is : %s  ",new String(bs,"utf-8"),session,Thread.currentThread().getId())).getBytes("UTF-8");
                        System.out.println(new String(msg));
                        //写给客户端。
                        out.write(msg);
                        out.flush();
                    }

                },(e)->{e.printStackTrace();},()->{});
            }).start();
        }
    }


    public static void main(String[] args) throws IOException {

        new Server().start();


    }


}
