package com.ctg.io.comp;

/**
 * Created by liyh on 2016/5/30.
 */
public interface ISession {

    /**
     * 写数据
     * @param msg
     */
    void write(Object msg);

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

}
