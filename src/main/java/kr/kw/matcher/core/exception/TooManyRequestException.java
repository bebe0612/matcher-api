package kr.kw.matcher.core.exception;

public class TooManyRequestException extends RuntimeException {
    public TooManyRequestException(String msg, Throwable t) {
        super(msg,t);
    }
    public TooManyRequestException(String msg) {
        super(msg);
    }
    public TooManyRequestException() {
        super();
    }
}
