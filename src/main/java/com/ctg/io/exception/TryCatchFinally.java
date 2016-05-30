package com.ctg.io.exception;

import com.ctg.io.exception.Catch;
import com.ctg.io.exception.Finally;
import com.ctg.io.exception.Try;

/**
 * Created by liyh on 2016/5/19.
 */
public class TryCatchFinally {
    public static void handle(Try t, Catch c, Finally f){
        try {
            t.handle();
        }catch (Throwable e){
            c.handle(e);
        }finally {
            f.handle();
        }
    }
}
