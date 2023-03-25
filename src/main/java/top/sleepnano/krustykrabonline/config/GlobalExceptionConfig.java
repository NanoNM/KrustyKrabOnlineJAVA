package top.sleepnano.krustykrabonline.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.sleepnano.krustykrabonline.util.VoBuilder;
import top.sleepnano.krustykrabonline.vo.Result;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionConfig {
    /**
     * 处理其他异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value =Exception.class)
    public Result exceptionHandler(HttpServletRequest req, Exception e, HttpServletResponse response){
        log.error("Exception！原因是:",e);
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return VoBuilder.wrong(" 异常! 来自:" +req.getRequestURI()+ " 原因: "+e.getLocalizedMessage(),null);
    }

}
