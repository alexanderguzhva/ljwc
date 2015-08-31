package com.gschw.ljwc.grabber.datagrabber.core;

import com.gschw.ljwc.auth.IIdentityGenerator;
import com.gschw.ljwc.auth.Identity;
import com.gschw.ljwc.auth.StandardIdentityRandomGenerator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Manages {@link Grabber}-s.
 */
public class GrabbersKeeper {
    private final Object locker = new Object();

    private Map<Identity, Grabber> sessions;

    private GrabberParameters grabberParameters;

    private GrabbersKeeperParameters keeperParameters;

    private IIdentityGenerator identityGenerator;

    public GrabbersKeeper(GrabbersKeeperParameters keeperParameters, GrabberParameters grabberParameters) {
        this.keeperParameters = keeperParameters;
        this.grabberParameters = grabberParameters;
        this.sessions = new HashMap<>();

        this.identityGenerator = new StandardIdentityRandomGenerator();
    }

    public Identity createSession() {
        synchronized (locker) {
            //// no more?
            if (sessions.size() >= keeperParameters.getMaximumNumberOfSessions())
                return null;

            ////
            Identity identity;
            do {
                 identity = identityGenerator.generate();
            } while (sessions.containsKey(identity));

            //GrabberSession grabberSession = GrabberSession.createSession(grabberParameters);
            Grabber grabber = new Grabber(grabberParameters);

            sessions.put(identity, grabber);
            return identity;
        }
    }

    public Grabber getBySession(Identity identity) {
        synchronized (locker) {
            return sessions.get(identity);
        }
    }

    public boolean deleteSession(Identity identity) {
        Grabber grabber;
        synchronized (locker) {
            grabber = sessions.remove(identity);
        }

        if (grabber != null)
            grabber.close();

        return (grabber != null);
    }

    public void clearSessions() {
        synchronized (locker) {
            sessions.clear();
        }
    }
}
