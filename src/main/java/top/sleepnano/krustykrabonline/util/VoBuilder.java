package top.sleepnano.krustykrabonline.util;

import org.springframework.stereotype.Component;
import top.sleepnano.krustykrabonline.vo.Result;

@Component
public class VoBuilder {
    public static Result ok(String msg, Object data){
        return new Result("1",msg,data);
    }
    public static Result wrong(String msg, Object data){
        return new Result("0",msg,data);
    }
    public static Result error(String msg, Object data){
        return new Result("-1",msg,data);
    }

}
