package com.gschw.lj.common;

/**
 * Interface for objects that mimic .NET's EventWaitHandle-based classes.
 */
public interface IResetEvent {
    void set();
    void reset();
    void waitOne() throws InterruptedException;
    boolean waitOne(long timeoutMsec) throws InterruptedException;
}
