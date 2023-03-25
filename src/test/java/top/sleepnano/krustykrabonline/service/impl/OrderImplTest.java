package top.sleepnano.krustykrabonline.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.sleepnano.krustykrabonline.dto.RestOrderProduct;
import top.sleepnano.krustykrabonline.service.OrderService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class OrderImplTest {

    @Autowired
    OrderService orderService;
    @Test
    void addOrderToRedis() {
        List<RestOrderProduct> restOrderProducts = new ArrayList<>();
        restOrderProducts.add(new RestOrderProduct("123",10));
        orderService.addOrderToRedis(restOrderProducts,"123");
    }

    @Test
    void paymentSucceeded() {
        orderService.paymentSucceeded("123","支付宝","SUCCESS");
    }
}