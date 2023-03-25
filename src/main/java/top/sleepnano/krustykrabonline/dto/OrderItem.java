package top.sleepnano.krustykrabonline.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.sleepnano.krustykrabonline.entity.Product;

import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
    Product product;
    Integer num;
    BigDecimal allPrice;
}

