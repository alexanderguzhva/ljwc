package com.gschw.lj.common;

/**
 * Andy's version of .NET's AutoResetEvent class.
 */
public class AutoResetEvent2 implements IResetEvent {

    private final Object lockObj = new Object();
    private boolean isSet = false;

    public AutoResetEvent2(boolean isSet) {
        this.isSet = isSet;
    }

    public void waitOne() throws InterruptedException {
        synchronized (lockObj) {
            while (!isSet) lockObj.wait();
            isSet = false;
        }
    }

    public boolean waitOne(long millis) throws InterruptedException {
        synchronized (lockObj) {
            if (isSet) return !(isSet = false);
            lockObj.wait(millis);
            return isSet ? !(isSet = false) : isSet;
        }
    }

    public void set() {
        synchronized (lockObj) {
            isSet = true;
            lockObj.notify();
        }
    }

    public void reset() {
        synchronized (lockObj) {
            isSet = false;
        }
    }
}
