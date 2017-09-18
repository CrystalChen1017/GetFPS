package com.crystal.getfps.model;


/**
 * Every small frame key time
 */
public class FrameTime {
    long t1, t2, t3;

    public FrameTime(String t1, String t2, String t3) {
        this.t1 = Long.parseLong(t1);
        this.t2 = Long.parseLong(t2);
        this.t3 = Long.parseLong(t3);
    }
}
