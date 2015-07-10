package com.gschw.ljwc.auth;

import java.util.UUID;

/**
 * Created by nop on 7/10/15.
 */
public class IdentityRandomGenerator {
    public static Identity generate() {
        return new Identity(UUID.randomUUID().toString());
    }
}
