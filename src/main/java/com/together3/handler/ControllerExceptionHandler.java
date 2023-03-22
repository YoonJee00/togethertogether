package com.together3.handler;

import com.together3.web.dto.CMRespDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.together3.handler.ex.CustomApiException;
import com.together3.handler.ex.CustomException;
import com.together3.handler.ex.CustomValidationApiException;
import com.together3.handler.ex.CustomValidationException;
import com.together3.util.Script;

@RestController
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(CustomValidationException.class)
    public String validationException(CustomValidationException e) {

        //CMRespDto, Script 비교
        //1.클라이언트에게 응답할 때는 Script 좋음(브라우저)
        //2.Ajax통신 - CMRespDto(개발자)
        //3.Android통신 - CMRespDto(개발자)

        return Script.back(e.getErrorMap().toString());
//		return new CMRespDto(-1, e.getMessage(), e.getErrorMap());
    }

    @ExceptionHandler(CustomValidationApiException.class)
    public ResponseEntity<?> validationApiException(CustomValidationApiException e) {
        return new ResponseEntity<>(new CMRespDto<>(-1, e.getMessage(), e.getErrorMap()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomApiException.class)
    public ResponseEntity<?> apiException(CustomApiException e) {
        return new ResponseEntity<>(new CMRespDto<>(-1, e.getMessage(), null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomException.class)
    public String exception(CustomException e) {
        return Script.back(e.getMessage());
    }
}