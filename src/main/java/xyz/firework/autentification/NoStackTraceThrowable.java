package xyz.firework.autentification;

public class NoStackTraceThrowable
extends RuntimeException {
    public NoStackTraceThrowable(String msg) {
        super(msg);
        this.setStackTrace(new StackTraceElement[0]);
    }

    @Override
    public String toString() {
        return "0.1";
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
