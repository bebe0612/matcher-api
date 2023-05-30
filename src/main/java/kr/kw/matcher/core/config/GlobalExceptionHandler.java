package kr.kw.matcher.core.config;

import kr.kw.matcher.core.exception.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

    private void _logging(HttpServletRequest request, Exception exception) {
        log.warn("[API_EXCEPTION : {}({})] " +
                "[PATH : {}] " +
                "[IP : {}]",
                exception.getClass().getSimpleName(), exception.getMessage(),
                request.getRequestURI(),
                request.getRemoteAddr());
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionResponse authenticationException(HttpServletRequest request, AuthenticationException e) {
        _logging(request, e);
        return ExceptionResponse.of(e);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse ddd(HttpServletRequest request, ConstraintViolationException e) {
        _logging(request, e);
        return ExceptionResponse.of(e);
    }

    // TODO
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse notFoundException(HttpServletRequest request, NotFoundException e) {
        _logging(request, e);
        return ExceptionResponse.of(e);
    }

    @ExceptionHandler(ForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse forbiddenException(HttpServletRequest request, ForbiddenException e) {
        _logging(request, e);
        return ExceptionResponse.of(e);
    }

    @ExceptionHandler(ConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ExceptionResponse conflictException(HttpServletRequest request, ConflictException e) {
        _logging(request, e);
        return ExceptionResponse.of(e);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionResponse badRequestException(HttpServletRequest request, BadRequestException e) {
        _logging(request, e);
        return ExceptionResponse.of(e);
    }

    @ExceptionHandler(TooManyRequestException.class)
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public ExceptionResponse tooManyRequestException(HttpServletRequest request, TooManyRequestException e) {
        _logging(request, e);
        return ExceptionResponse.of(e);
    }
}
