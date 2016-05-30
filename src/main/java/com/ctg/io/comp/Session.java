package com.ctg.io.comp;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liyh on 2016/5/18.
 */
public class Session extends AbsSession implements INioSession {
    /**
     * channel
     */
    private SocketChannel channel;

    /**
     * buffer 暂且写死，应该动态。
     */
    private ByteBuffer buffer = null;



    public Session(SocketChannel channel){
        this.channel  = channel;
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
