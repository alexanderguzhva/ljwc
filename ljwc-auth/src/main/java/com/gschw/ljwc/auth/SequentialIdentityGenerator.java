package com.gschw.ljwc.auth;

import java.util.UUID;

/**
 * Generates an {@link Identity}.
 */
public class SequentialIdentityGenerator implements IIdentityGenerator {

    /**
     * A locker object.
     */
    private final Object locker = new Object();

    /**
     * A counter.
     */
    private long lowLong = 0;

    /**
     * Upper part of the underlying UUID.
     */
    private long hiLong;

    public SequentialIdentityGenerator() {
        hiLong = 0;
    }

    public SequentialIdentityGenerator(long hiLong) {
        this.hiLong = hiLong;
    }

    /**
     * Generates an identity.
     *
     * @return Identity.
     */
    @Override
    public Identity generate() {
        long localLowLong;
        long localHiLong;

        synchronized (locker) {
            localLowLong = lowLong;
            localHiLong = hiLong;

            //// it's good for debugging only, btw
            lowLong += 1;
        }

        UUID uuid = new UUID(localHiLong, localLowLong);

        return new Identity(uuid.toString());
    }
}
