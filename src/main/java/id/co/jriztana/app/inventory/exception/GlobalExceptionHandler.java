package id.co.jriztana.app.inventory.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, String> dataExceptionHandler(DataNotFoundException ex) {
        HashMap<String, String> resultMap = new HashMap<>();
        resultMap.put("code", "404");
        resultMap.put("message", ex.getMessage());
        return resultMap;
    }

}
