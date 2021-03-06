package com.zz.yuan_user.exception;


import com.zz.yuan_user.result.CodeMsg;
import com.zz.yuan_user.result.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@ControllerAdvice
@ResponseBody
public class GlobleExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public Result<String> exceptionHandler(HttpServletRequest request, Exception e){
        e.printStackTrace();
        if(e instanceof GlobalException){
          GlobalException ge = (GlobalException) e;
          return Result.error(ge.getCm());
        } else if (e instanceof BindException){
            BindException be = (BindException) e;
            List<ObjectError> errorList = be.getAllErrors();
            String msg = errorList.get(0).getDefaultMessage();
            return Result.error(CodeMsg.BIND_ERROR.fillArgs(msg));
        }else {
            String message = e.getCause().getMessage();
            return Result.errorMessage(message);
        }
    }
}
