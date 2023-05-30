package kr.kw.matcher.core.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String msg, Throwable t) {
        super(msg,t);
    }
    public NotFoundException(String msg) {
        super(msg);
    }
    public NotFoundException() {
        super();
    }
}
