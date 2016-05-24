package com.ctg.io.nio;

import java.nio.channels.SelectionKey;

/**
 * Created by liyh on 2016/5/18.
 */

@FunctionalInterface
public interface Handler {
    void handle(SelectionKey key);
}
