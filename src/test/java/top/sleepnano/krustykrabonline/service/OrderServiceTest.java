package top.sleepnano.krustykrabonline.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.sleepnano.krustykrabonline.dto.OrderBackList;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceTest {
    @Autowired
    OrderService orderService;

    @Test
    void getDatList() {
        List<OrderBackList> datList = orderService.getDatList("985dcb16-b9fb-11ed-a7fa-00e04c86af91");

        System.out.println("datList = " + datList);
    }
}