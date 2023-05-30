package kr.kw.matcher.core.exception;

public class ForbiddenException extends RuntimeException{
    public ForbiddenException(String msg, Throwable t) {
        super(msg,t);
    }
    public ForbiddenException(String msg) {
        super(msg);
    }
    public ForbiddenException() {
        super();
    }
}
