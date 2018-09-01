package com.example.demo.exception;

import com.example.demo.enums.ResponseStatusEnum;
import com.example.demo.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Order(value = Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class BaseExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(BaseExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result<String> baseErrorHandler(Exception e) {
        logger.error("error", e);

        Result<String> result = new Result<>();
        if (MethodArgumentNotValidException.class.isAssignableFrom(e.getClass())) {
            MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) e;
            result.setCode(ResponseStatusEnum.INVALID.getCode());
            result.setMsg(methodArgumentNotValidException.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        } else {
            result.setCode(ResponseStatusEnum.ERROR.getCode());
            result.setMsg(e.getMessage());
        }
        return result;
    }
}
