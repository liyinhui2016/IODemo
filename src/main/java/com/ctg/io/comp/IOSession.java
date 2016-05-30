package com.ctg.io.comp;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by liyh on 2016/5/30.
 */
public class IOSession extends AbsSession implements  IIOSession {

    private  Socket socket;

    public IOSession(){}

    public  IOSession(Socket socket){
        this.socket = socket;
    }

    @Override
    public Socket socket() {
        return socket;
    }

    @Override
    public void write(Object msg) {
        try {
            socket.getOutputStream().write(msg.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
