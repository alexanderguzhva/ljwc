package com.gschw.lj.common;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Created by nop on 8/16/15.
 */
public class ManualResetEvent implements IResetEvent {
    private volatile int count;
    private final Object locker = new Object();
    private volatile CountDownLatch countDownLatch;

    public ManualResetEvent(boolean signaled) {
        count = -1;
        if (signaled) {
            countDownLatch = new CountDownLatch(0);
        } else {
            countDownLatch = new CountDownLatch(1);
        }
    }

    public void set() {
        countDownLatch.countDown();
    }

    public void reset() {
        synchronized (locker) {
            if (countDownLatch.getCount() == 0) {
                countDownLatch = new CountDownLatch(1);
            }
        }
    }

    public void waitOne() throws InterruptedException {
        countDownLatch.await();
    }

    public boolean waitOne(long timeoutMsec) throws InterruptedException {
        return countDownLatch.await(timeoutMsec, TimeUnit.MILLISECONDS);
    }
}
