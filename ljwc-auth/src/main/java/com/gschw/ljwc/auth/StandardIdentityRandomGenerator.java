package com.gschw.ljwc.auth;

import java.util.UUID;

/**
 * Created by nop on 8/10/15.
 *
 * Generates a random Identity.
 */
public class StandardIdentityRandomGenerator implements IIdentityGenerator {
    @Override
    public Identity generate() {
        return new Identity(UUID.randomUUID().toString());
    }
}
