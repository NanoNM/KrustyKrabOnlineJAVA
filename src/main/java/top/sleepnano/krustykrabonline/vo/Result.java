package top.sleepnano.krustykrabonline.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Result {
    private String code;
    private String msg;
    private Object data;
}
