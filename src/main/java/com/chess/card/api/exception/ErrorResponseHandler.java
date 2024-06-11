package com.chess.ws.api.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RestControllerAdvice
public class ErrorResponseHandler extends ResponseEntityExceptionHandler implements AccessDeniedHandler {

    @Autowired
    private ObjectMapper mapper;

    @Override
    @ExceptionHandler(AccessDeniedException.class)
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException,
            ServletException {

        log.error("error={}",accessDeniedException.getMessage(),accessDeniedException);
    }

    @ExceptionHandler(Exception.class)
    public void handle(Exception exception, HttpServletResponse response) {
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        try {
            log.error("error={}",exception.getMessage(),exception);
            mapper.writeValue(response.getWriter(), ErrorResponse.of(exception.getMessage(),
                    ErrorCode.GENERAL, HttpStatus.INTERNAL_SERVER_ERROR));
        } catch (IOException e) {
            log.error("IOException",e);
            throw new RuntimeException(e);
        }
    }


}
