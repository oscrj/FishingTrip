package ecutb.fishingtrip.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ApplicationControllerAdvice {


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(AppResourceNotFoundException.class)
    public String handelAppResourceNotFoundException(Model model, AppResourceNotFoundException e){
        Map<Object, Object> map = new HashMap<>();
        map.put("statusname", HttpStatus.NOT_FOUND.getReasonPhrase());  // Get reason phrase.
        map.put("statuscode", HttpStatus.NOT_FOUND.value());  // Get status code.
        map.put("message", e.getMessage());  // Get message from created exceptions.

        model.addAttribute("modelMap", map);
        return "exception-view";
    }
}
