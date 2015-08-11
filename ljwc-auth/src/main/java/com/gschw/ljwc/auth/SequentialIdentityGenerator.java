package com.gschw.ljwc.auth;

import java.util.UUID;

/**
 * Created by nop on 8/10/15.
 */
public class SequentialIdentityGenerator implements IIdentityGenerator {

    private final Object locker = new Object();

    private long lowLong = 0;
    private long hiLong = 0;

    @Override
    public Identity generate() {
        synchronized (locker) {
            UUID uuid = new UUID(hiLong, lowLong);

            //// it's good for debugging only, btw
            lowLong += 1;

            return new Identity(uuid.toString());
        }
    }
}
