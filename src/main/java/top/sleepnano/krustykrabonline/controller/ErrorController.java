package top.sleepnano.krustykrabonline.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("error")
@Slf4j
public class ErrorController {
    @RequestMapping("exthrow")
    public void filterTokenError(HttpServletRequest request){
        Exception exception = (Exception) request.getAttribute("filter.error");
        throw new RuntimeException(exception.getLocalizedMessage());
    }
}
