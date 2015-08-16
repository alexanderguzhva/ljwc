package com.gschw.lj.common;

/**
 * Created by nop on 8/16/15.
 */
public interface IResetEvent {
    void set();
    void reset();
    void waitOne() throws InterruptedException;
    boolean waitOne(long timeoutMsec) throws InterruptedException;
}
