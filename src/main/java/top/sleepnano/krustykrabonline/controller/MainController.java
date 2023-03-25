package top.sleepnano.krustykrabonline.controller;

import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.sleepnano.krustykrabonline.dto.SeckillRedisOrder;
import top.sleepnano.krustykrabonline.service.ProductService;
import top.sleepnano.krustykrabonline.service.SeckillService;
import top.sleepnano.krustykrabonline.util.VoBuilder;
import top.sleepnano.krustykrabonline.vo.Result;

import java.util.List;

@RestController
@RequestMapping("main")
public class MainController implements BaseController{

    @Autowired
    ProductService productService;
    @Autowired
    SeckillService seckillService;

    /**
     *
     * @param page 页码
     * @return VO数据
     */
    @GetMapping("/list")
    Result listProduct(@RequestParam("page") Integer page){
        return productService.getProduct(page,10);
    }

    @GetMapping("list/seckill")
    public Result listSecKill(){
        List<SeckillRedisOrder> seckillRedisOrders = seckillService.listCacheSeckill();
        return VoBuilder.ok("查询成功",seckillRedisOrders);
    }
    @GetMapping("/test")
    String  test(){
        return "Hello World";
    }
}
