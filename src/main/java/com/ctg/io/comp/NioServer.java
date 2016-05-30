package com.ctg.io.comp;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by liyh on 2016/5/18.
 */
public class NioServer {

    /**
     * 端口
     */
    private int port;



    /**
     * server socket channel
     */
    private ServerSocketChannel serverChannel ;

    /**
     * event select ..
     */
    private Selector selector;

    /**
     * 存贮session
     */
    private Map<SocketChannel,INioSession> sessionMap = null;

    //handler map .
    public Map<String,Handler> handlerMap = new HashMap<>();
    {
        //处理accept事件
        handlerMap.put("accept",(SelectionKey obj)-> {
            TryCatchFinally.handle(()->{
                SocketChannel channel = ((ServerSocketChannel)(obj.channel())).accept();
                if(channel != null) {
                    channel.configureBlocking(false);
                    //当然可以使用其它的selector
                    channel.register(this.selector,SelectionKey.OP_READ);
                    sessionMap.put(channel,new Session(channel));
                }
            },(e)->{
                e.printStackTrace();
            },()->{});
        });
        //处理读事件
        handlerMap.put("read",(SelectionKey obj)->{
            TryCatchFinally.handle(()->{
                SocketChannel channel = (SocketChannel) obj.channel();
                if(channel != null && channel.isOpen()) {
                    INioSession session = NioServer.this.sessionMap.get(channel);
                    if(session != null){
                        // 让buffer可写
                        session.getBuffer().clear();
                        int size = channel.read(session.getBuffer());
                        //此处应该循环读取，为了简单就读一次，假定所有数据包长度不会超过1024
                        if(size > 0){
                            session.setAttr("recLength",size);
                            //注册写就绪
                            obj.interestOps(obj.interestOps()|SelectionKey.OP_WRITE);
                            session.getBuffer().flip();
                            selector.wakeup();
                        } else
                        if(size == -1){
                            session.getChannel().close();
                            obj.cancel();
                        }
                    }
                }
            },(e)->{e.printStackTrace();},()->{});
        });
        //处理写事件
        handlerMap.put("write",(SelectionKey obj)->{
            TryCatchFinally.handle(()->{
                SocketChannel channel = (SocketChannel) obj.channel();
                if(channel != null && channel.isOpen()) {
                    INioSession session = NioServer.this.sessionMap.get(channel);
                    if(session != null){
                        int len = (int) session.getAttr("recLength");
                        byte [] bs = new byte[len];
                        session.getBuffer().get(bs);
                        session.getBuffer().clear();
                        byte [] msg = (String.format("server rec : %s , current session is : %s ",new String(bs,"utf-8"),session)).getBytes("UTF-8");
                        System.out.println(new String(msg));
                        session.getBuffer().put(msg,0,msg.length);
                        //进入可读模式
                        session.getBuffer().flip();
                        session.write(session.getBuffer());
                    }
                }
            },(e)->{e.printStackTrace();},()->{
                //取消写就绪
                obj.interestOps(obj.interestOps()&~SelectionKey.OP_WRITE);
            });
        });
    }

    /**
     * 构造方法指定端口
     * @param port
     */

    public NioServer(int port){
        this.port = port;
    }

    /**
     * 初始化
     */
    public void init() {
        try {
            this.serverChannel = ServerSocketChannel.open();
            this.selector = Selector.open();
            ServerSocket socket = this.serverChannel.socket();
            socket.bind(new InetSocketAddress(this.port));
            //设置为异步。
            this.serverChannel.configureBlocking(false);
            this.serverChannel.register(this.selector, SelectionKey.OP_ACCEPT);
            this.sessionMap = new ConcurrentHashMap<>();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            // do  something ...
        }

    }
    public void start(){
        while (true){
            try {
                //此处会阻塞，
                //this.selector.select(1000L);
                this.selector.select();
            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
            Set<SelectionKey> keys  = selector.selectedKeys();
            if(keys == null || keys.size() == 0){
                continue;
            }
            Iterator<SelectionKey> iter = keys.iterator();
            while (iter.hasNext()) {
                SelectionKey k = iter.next();
                if (k.isAcceptable()){
                    this.handlerMap.get("accept").handle(k);
                } else
                if(k.isReadable()){
                    this.handlerMap.get("read").handle(k);
                } else
                if(k.isWritable()){
                    this.handlerMap.get("write").handle(k);
                }
                iter.remove();
            }
        }
    }



    public static void main(String[] args) {
        NioServer server = new NioServer(1024);
        server.init();
        server.start();
    }

}
