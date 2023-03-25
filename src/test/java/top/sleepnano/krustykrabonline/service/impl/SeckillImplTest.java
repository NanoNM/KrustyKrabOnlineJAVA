package top.sleepnano.krustykrabonline.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.sleepnano.krustykrabonline.service.SeckillService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SeckillImplTest {
    @Autowired
    SeckillService seckillService;

    @Test
    void orderSeckill() {
        seckillService.orderSeckill("393b918b-acf6-41e5-8447-0d10753fa94e","c3f3ba9f-ca3f-11ed-b568-00e04c86af91");
    }
}