package top.sleepnano.krustykrabonline;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import top.sleepnano.krustykrabonline.entity.Product;
import top.sleepnano.krustykrabonline.entity.User;
import top.sleepnano.krustykrabonline.service.ProductService;
import top.sleepnano.krustykrabonline.service.UserService;
import top.sleepnano.krustykrabonline.vo.Result;

@SpringBootTest
class KrustyKrabOnlineApplicationTests {

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	ProductService productService;

	@Autowired
	UserService userService;

	@Test
	void contextLoads() {
	}
	@Test
	void testRedis(){

		ValueOperations ops = redisTemplate.opsForValue();
		Product[] products = (Product[]) ops.get("product");
		System.out.println("products = " + products);
	}

	@Test
	void testUserService(){
		Result product = productService.getProduct(1, 10);
		System.out.println("product = " + product);
		User user01 = userService.selectUserByName("user01");
		System.out.println("user01 = " + user01);
	}
}
