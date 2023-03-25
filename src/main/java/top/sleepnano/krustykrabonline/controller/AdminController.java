package top.sleepnano.krustykrabonline.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import top.sleepnano.krustykrabonline.dto.RestOrderProduct;
import top.sleepnano.krustykrabonline.dto.SeckillRedisOrder;
import top.sleepnano.krustykrabonline.entity.Product;
import top.sleepnano.krustykrabonline.mapper.ProductMapper;
import top.sleepnano.krustykrabonline.service.SeckillService;
import top.sleepnano.krustykrabonline.util.JwtUtil;
import top.sleepnano.krustykrabonline.util.VoBuilder;
import top.sleepnano.krustykrabonline.vo.Result;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/alg")
public class AdminController implements BaseController{

    @Autowired
    SeckillService seckillService;

    @PostMapping("/addseckill")
    @PreAuthorize("hasAuthority('sys:admin:add:seckill')")
    public Result addSeckill(@RequestBody List<RestOrderProduct> productNo,
                             @RequestParam("num")Integer num,
                             @RequestParam("st")Long startTime,
                             @RequestParam("et")Long endTime,
                             @RequestParam("prise")Long prise,
                             HttpServletRequest httpServletRequest){
        return seckillService.addSeckillToRedis(productNo,num,startTime,endTime, BigDecimal.valueOf(prise));
    }
}
