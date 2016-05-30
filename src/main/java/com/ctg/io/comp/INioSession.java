package com.ctg.io.comp;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by liyh on 2016/5/19.
 */
public interface INioSession extends ISession  {

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
     * 获取缓冲区buffer size .
     * @return
     */
    int getBufferSize();


}
