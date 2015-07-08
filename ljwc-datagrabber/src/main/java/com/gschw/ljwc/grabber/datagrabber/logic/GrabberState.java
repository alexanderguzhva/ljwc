package com.gschw.ljwc.grabber.datagrabber.logic;

/**
 * Created by nop on 7/6/15.
 */
public enum GrabberState {
    UNINITIALIZED,
    SLEEPING,
    SHOULD_PULL_A_REQUEST,
    SHOULD_PUSH_A_REPLY,
    SHOULD_GRAB_AN_URL,
    NEED_TO_TERMINATE
}
