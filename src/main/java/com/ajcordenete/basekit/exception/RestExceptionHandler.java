package com.ajcordenete.basekit.exception;

import com.ajcordenete.basekit.data.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(InsufficientAuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public BaseResponse handleAuthException(
            InsufficientAuthenticationException exception
    ) {
        return new BaseResponse(false, HttpStatus.UNAUTHORIZED.value(), exception.getLocalizedMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public BaseResponse handleUsernameNotFoundException(
            UsernameNotFoundException exception
    ) {
        return new BaseResponse(false, HttpStatus.NOT_FOUND.value(), exception.getLocalizedMessage());
    }
}
