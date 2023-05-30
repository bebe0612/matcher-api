package kr.kw.matcher.core.exception;

public class ConflictException extends RuntimeException {
    public ConflictException(String msg, Throwable t) {
        super(msg,t);
    }
    public ConflictException(String msg) {
        super(msg);
    }
    public ConflictException() {
        super();
    }
}
