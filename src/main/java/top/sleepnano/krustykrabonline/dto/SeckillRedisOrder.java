package top.sleepnano.krustykrabonline.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.sleepnano.krustykrabonline.entity.Product;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeckillRedisOrder {
    String seckillOrderNo;
    List<OrderItem> products;

    Integer num;
    Date startTime;
    Date endTime;
    BigDecimal prise;
}
