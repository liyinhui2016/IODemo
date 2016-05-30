package com.ctg.io.io;

import com.ctg.io.comp.Arr;
import jdk.internal.util.xml.impl.Input;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by liyh on 2016/5/18.
 */
public class Client {

    public static void main(String[] args) throws IOException, InterruptedException {

        Socket socket = new Socket("localhost",1024);
        OutputStream out = socket.getOutputStream();
        InputStream in = socket.getInputStream();
        byte [] bs = new byte[ 1024 ];
        for (int i = 0 ;i < 100; i ++ ){
            out.write(String.format("hello server : %s ",i).getBytes());
            out.flush();
            int len  = in.read(bs);
            System.out.println("client rec :" + new String(Arr.sub(bs,0,len),"UTF-8"));
            Thread.sleep((long) ((Math.random()*1+1d)*1000));
        }

        out.close();
        in.close();

    }







}
