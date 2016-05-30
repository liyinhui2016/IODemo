package com.ctg.io.comp;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liyh on 2016/5/30.
 */
public abstract class AbsSession implements ISession {

    public Map<String ,Object> attrMap = new HashMap<>();

    @Override
    public void setAttr(String key, Object value) {
        this.attrMap.put(key,value);
    }

    @Override
    public Object getAttr(String key) {
        return attrMap.get(key);
    }

}
