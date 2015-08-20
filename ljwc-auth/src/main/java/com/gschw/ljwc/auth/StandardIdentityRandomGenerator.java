package com.gschw.ljwc.auth;

import java.util.UUID;

/**
 * Generates a random Identity.
 */
public class StandardIdentityRandomGenerator implements IIdentityGenerator {
    /**
     * Generates an identity using random number generator.
     *
     * @return An identity.
     */
    @Override
    public Identity generate() {
        return new Identity(UUID.randomUUID().toString());
    }
}
