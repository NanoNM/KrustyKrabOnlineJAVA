package top.sleepnano.krustykrabonline.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SelectOrder {
    private String orderNo;
    private Integer orderQuantity;
    private Product product;

    @Override
    public String toString() {
        return "SelectOrder{" +
                "orderNo='" + orderNo + '\'' +
                ", orderQuantity=" + orderQuantity +
                ", product=" + product +
                '}';
    }
}
