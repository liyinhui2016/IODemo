package com.ctg.io.nio;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by liyh on 2016/5/19.
 */
public interface ISession {

    /**
     * 获取通讯channel
     * @return
     */
    SocketChannel getChannel();

    /**
     * 数据buffer.
     * @return
     */
    ByteBuffer getBuffer();

    /**
     * 设置属性
     * @param key
     * @param value
     */
    void setAttr(String key,Object value);

    /**
     * 获取属性。
     * @param key
     * @return
     */
    Object getAttr(String key);

    /**
     * 获取缓冲区buffer size .
     * @return
     */
    int getBufferSize();

    /**
     * 写数据
     * @param msg
     */
    void write(Object msg);

}
