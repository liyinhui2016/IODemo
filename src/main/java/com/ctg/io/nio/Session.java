package com.ctg.io.nio;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liyh on 2016/5/18.
 */
public class Session implements ISession {
    /**
     * channel
     */
    private SocketChannel channel;

    /**
     * buffer 暂且写死，应该动态。
     */
    private ByteBuffer buffer = null;

    public Map<String ,Object> attrMap = new HashMap<>();


    public Session(SocketChannel channel){
        this.channel  = channel;
        //写死方便。
        this.buffer =  ByteBuffer.allocate(1024);
    }

    public void reset(){
        buffer.reset();
    }
    public SocketChannel getChannel() {
        return channel;
    }

    public void setChannel(SocketChannel channel) {
        this.channel = channel;
    }

    public ByteBuffer getBuffer() {
        return buffer;
    }

    @Override
    public void setAttr(String key, Object value) {
        this.attrMap.put(key,value);
    }

    @Override
    public Object getAttr(String key) {
        return attrMap.get(key);
    }

    @Override
    public int getBufferSize() {
        return 1024;
    }

    @Override
    public void write(Object msg) {
        if (msg instanceof ByteBuffer){
            TryCatchFinally.handle(()->{
                this.getChannel().write((ByteBuffer) msg);
            },(e)->e.printStackTrace(),()->{});
        }
    }

    public void setBuffer(ByteBuffer buffer) {
        this.buffer = buffer;
    }
}
