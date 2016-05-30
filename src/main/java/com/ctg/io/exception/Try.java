package com.ctg.io.exception;

/**
 * Created by liyh on 2016/5/19.
 */
@FunctionalInterface
public interface Try {
    void handle() throws Throwable;
}
