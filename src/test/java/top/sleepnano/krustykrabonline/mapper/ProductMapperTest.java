package top.sleepnano.krustykrabonline.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.sleepnano.krustykrabonline.dto.RestOrderProduct;
import top.sleepnano.krustykrabonline.entity.Product;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductMapperTest {
    @Autowired
    ProductMapper productMapper;

    @Test
    void selectProductByNos() {
        List<RestOrderProduct> strings = new ArrayList<>();
        strings.add(new RestOrderProduct("80e2a82a-c909-11ec-ab41-00e04c86af91",10));
        List<Product> products = productMapper.selectProductByNos(strings);
        for (Product product : products) {
            System.out.println("product = " + product);
        }
    }

    @Test
    void selectProductByNosString() {
        List<String> strings = new ArrayList<>();
        strings.add("80e2a82a-c909-11ec-ab41-00e04c86af91");
        strings.add("80e2abe6-c909-11ec-ab41-00e04c86af91");
        List<Product> products = productMapper.selectProductByNosString(strings);
        for (Product product : products) {
            System.out.println("product = " + product);
        }
    }
}