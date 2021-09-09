package com.example.test8_11.util;


import com.example.test8_11.common.exception.MyException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;

public class BindingResultUtil {
    public static void check(BindingResult bindingResult) {
        HashMap<String, Object> map = new HashMap<>();
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                String field = fieldError.getField();
                String defaultMessage = fieldError.getDefaultMessage();
                map.put(field, defaultMessage);
            }
            map.put("code", "700");
            throw new MyException(map);
        }
    }
}
