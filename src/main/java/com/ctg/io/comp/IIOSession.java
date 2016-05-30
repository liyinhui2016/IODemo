package com.ctg.io.comp;

import java.net.Socket;

/**
 * Created by liyh on 2016/5/30.
 */
public interface IIOSession extends ISession {

    /**
     * 客户端socket
     * @return
     */
    Socket socket();
}
