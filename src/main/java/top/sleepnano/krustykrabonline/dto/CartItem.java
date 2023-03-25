package top.sleepnano.krustykrabonline.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.sleepnano.krustykrabonline.entity.Product;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    Product product;
    Integer num = 0;
}
