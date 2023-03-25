package top.sleepnano.krustykrabonline.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.sleepnano.krustykrabonline.entity.OrderProduct;
import top.sleepnano.krustykrabonline.entity.SelectOrder;
import top.sleepnano.krustykrabonline.entity.UserOrder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderMapperTest {

    @Autowired
    OrderMapper orderMapper;
    @Test
    void insertOrderProduct() {
        List<OrderProduct> orderProducts = new ArrayList<>();
//        orderProducts.add(new OrderProduct("1111111","12312312312"));
//        orderProducts.add(new OrderProduct("1111111","23312312313"));
        orderMapper.insertOrderProduct(orderProducts);
    }

    @Test
    void getDatOrderList() {
        List<SelectOrder> datOrderList = orderMapper.getDatOrderList("d6e0b255-676e-4e50-b065-c5b741dfd395");
        for (SelectOrder selectOrder : datOrderList) {
            System.out.println("selectOrder = " + selectOrder);
        }
    }

    @Test
    void getUserOrderList() {
        List<String> userOrderList = orderMapper.getUserOrderList("985dcb16-b9fb-11ed-a7fa-00e04c86af91");
        for (String s : userOrderList) {
            System.out.println("s = " + s);
        }
    }
}