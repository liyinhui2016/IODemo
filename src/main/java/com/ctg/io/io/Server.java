package com.ctg.io.io;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by liyh on 2016/5/18.
 */
public class Server {


    int port  = 1024;


    public  void start() throws IOException {
        //綁定端口
        ServerSocket serverSocket = new ServerSocket(1024);
        while (true){
            Socket socket = serverSocket.accept();



        }
    }


    public static void main(String[] args) throws IOException {




    }


}
