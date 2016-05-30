package com.ctg.io.comp;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.*;

/**
 * 个人感觉客户端nio的必要性不是很大，一般客户端不需要很多TCP链接，但这也要看应用场景，不过作为demo可以实现一个玩玩。
 * Created by liyh on 2016/5/18.
 */
public class NioClient {

    private String host;
    private int port;
    private SocketChannel channel;
    private Selector selector;

    private ByteBuffer buffer  =  ByteBuffer.allocate(1024);

    private Queue<String> msgQueue = new LinkedList<>();

    public NioClient (String host ,int port){
        TryCatchFinally.handle(()->{
            this.host = host;
            this.port = port;
            selector = Selector.open();
            channel = SocketChannel.open();
            channel.connect(new InetSocketAddress(this.host,this.port));
            channel.configureBlocking(false) ;
            //注册链接时间。
            channel.register( selector,SelectionKey.OP_CONNECT);
        },(e)->{e.printStackTrace();},()->{});
    }

    public void send(String msg){
        msgQueue.add(msg);
    }

    public void start(){

        while (true){
            TryCatchFinally.handle(()->{
                int s = selector.select();
                if(s > 0){
                    Set<SelectionKey > keys = selector.selectedKeys();
                    if(keys!= null && keys .size()> 0) {
                        Iterator<SelectionKey> iter = keys.iterator();
                        while (iter.hasNext()){
                            SelectionKey key = iter.next();
                            if (key.isConnectable() ){
                                SocketChannel channel = (SocketChannel) key.channel();
                                if(channel.finishConnect()){
                                    key.interestOps(key.interestOps()|SelectionKey.OP_READ);
                                }else{

                                }
                            }
                            if(key.isWritable()){

                            }
                            if(key.isReadable()){

                            }
                            iter.remove();
                        }
                    }
                }
            },(e)->{
                e.printStackTrace();
            },()->{});
        }


    }


    public static void main(String[] args) {
        InetSocketAddress address  = new InetSocketAddress("127.0.0.1",1024);
    }

}
