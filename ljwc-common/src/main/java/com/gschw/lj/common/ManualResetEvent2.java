package com.gschw.lj.common;

/**
 * Andy's version of .NET's ManualResetEvent class.
 */
public class ManualResetEvent2 implements IResetEvent {
    private final Object lockObj = new Object();
    private boolean isSet = false;

    public ManualResetEvent2(boolean isSet) {
        this.isSet = isSet;
    }

    public void waitOne() throws InterruptedException {
        synchronized (lockObj) {
            while (!isSet) lockObj.wait();
        }
    }

    public boolean waitOne(long millis) throws InterruptedException {
        synchronized (lockObj) {
            if (isSet) return true;
            lockObj.wait(millis);
            return isSet;
        }
    }

    public void set() {
        synchronized (lockObj) {
            isSet = true;
            lockObj.notifyAll();
        }
    }

    public void reset() {
        synchronized (lockObj) {
            isSet = false;
        }
    }
}

