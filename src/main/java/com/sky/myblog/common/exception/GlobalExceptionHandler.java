package com.sky.myblog.common.exception;

import com.sky.myblog.common.lang.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.sql.ResultSet;

/**
 * Global exception handler
 * @author sky
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * catch shiro exception
     * @param e shiro exception
     * @return sealed result
     */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public Result handle401(ShiroException e) {
        return Result.fail(401, e.getMessage(), null);
    }

    /**
     * handle assert exception
     * @param e illegal argument exception
     * @return sealed result
     * @throws IOException io exception
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public Result handler(IllegalArgumentException e) throws IOException {
        log.error("Assert exception:---------------->{}", e.getMessage());
        return Result.fail(e.getMessage());
    }

    /**
     * validation exception handler
     * @param e validation exception
     * @return sealed result
     * @throws IOException io exception
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result handler(MethodArgumentNotValidException e) throws IOException {
        log.error("entity valid error:--------------->{}", e.getMessage());
        BindingResult bindingResult = e.getBindingResult();
        ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();
        return Result.fail(objectError.getDefaultMessage());
    }

    /**
     * run time error handler
     * @param e run time error
     * @return sealed result
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = RuntimeException.class)
    public Result handler(RuntimeException e) {
        log.error("runtime error:--------------->{}", e.getMessage());
        return Result.fail(e.getMessage());
    }
}
