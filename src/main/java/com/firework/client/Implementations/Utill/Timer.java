package com.firework.client.Implementations.Utill;

public final class Timer {
    private long time = System.currentTimeMillis();

    public boolean hasPassed(double ms) {
        return (double)(System.currentTimeMillis() - this.time) >= ms;
    }

    public void reset() {
        this.time = System.currentTimeMillis();
    }

    public long getTime() {
        return this.time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
