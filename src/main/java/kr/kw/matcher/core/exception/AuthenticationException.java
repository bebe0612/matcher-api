package kr.kw.matcher.core.exception;

public class AuthenticationException extends RuntimeException {
    public AuthenticationException(String msg, Throwable t) {
        super(msg,t);
    }
    public AuthenticationException(String msg) { super(msg); }
    public AuthenticationException() {
        super();
    }
}