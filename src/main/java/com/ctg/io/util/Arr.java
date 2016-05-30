package com.ctg.io.util;

/**
 * Created by liyh on 2016/5/30.
 */
public class Arr {

    public static  byte[] sub(byte [] bs ,int start ,int len ){
        if(bs == null)
            return  null;
        if(start + len > bs.length)
            throw new ArrayIndexOutOfBoundsException();
        byte [] bss  = new byte[len];
        for (int i = 0 ; i<len ; ++ i)
            bss[i] = bs[start+i];
        return  bss;
    }
}
