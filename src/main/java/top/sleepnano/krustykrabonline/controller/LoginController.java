package top.sleepnano.krustykrabonline.controller;

import jakarta.annotation.Nullable;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import top.sleepnano.krustykrabonline.dto.OrderBackList;
import top.sleepnano.krustykrabonline.dto.OrderRedis;
import top.sleepnano.krustykrabonline.dto.RestOrderProduct;
import top.sleepnano.krustykrabonline.service.OrderService;
import top.sleepnano.krustykrabonline.service.ProductService;
import top.sleepnano.krustykrabonline.service.SeckillService;
import top.sleepnano.krustykrabonline.util.JwtUtil;
import top.sleepnano.krustykrabonline.util.VoBuilder;
import top.sleepnano.krustykrabonline.vo.Result;

import java.util.*;

@RestController
@RequestMapping("/lg")
public class LoginController {

    @Autowired
    ProductService productService;
    @Autowired
    SeckillService seckillService;
    @Autowired
    OrderService orderService;


    @GetMapping("/hello")
    public String testHello(){
        return "this is hello";
    }

    @GetMapping("/hello2")
    public String testHello2(HttpServletRequest httpServletRequest){
        return "this is hello2";
    }

    @GetMapping("/addcart")
    @PreAuthorize("hasAuthority('sys:user:add:cart')")
    public Result addCart(@RequestParam("productno") String productNo,@RequestParam("num") Integer num,HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("token");
        try {
            String userNo = JwtUtil.parseJWT(token).getSubject();
            return productService.addToShoppingCart(productNo, num, userNo);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/addorder")
    @PreAuthorize("hasAuthority('sys:user:add:order')")
    public Result addOrder(@RequestBody List<RestOrderProduct> products, HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("token");
        try {
            String userNo = JwtUtil.parseJWT(token).getSubject();
            return orderService.addOrderToRedis(products,userNo);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/cancelorder")
    @PreAuthorize("hasAuthority('sys:user:cancel:order')")
    public Result cancelOrder(@RequestParam("orderNo")String orderNo, HttpServletRequest httpServletRequest){
        String token = httpServletRequest.getHeader("token");
        try {
            String userNo = JwtUtil.parseJWT(token).getSubject();
            return orderService.cancelOrder(orderNo,userNo);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/listorder/{status}")
    @PreAuthorize("hasAuthority('sys:user:query:order')")
    public Result queryOrder(@Nullable @PathVariable("status") String status,
                             HttpServletRequest httpServletRequest,
                             @Nullable @RequestParam("page") Integer page){
        String token = httpServletRequest.getHeader("token");
        try {
            String userNo = JwtUtil.parseJWT(token).getSubject();
            if ("cache".equals(status)){
                List<OrderBackList> cacheList = orderService.getCacheList(userNo);
                if (Objects.isNull(cacheList)){
                    return VoBuilder.ok("缓存订单为空",null);
                }
                return VoBuilder.ok("缓存订单查询成功",cacheList);
            } else if("dat".equals(status)){
                List<OrderBackList> datList = orderService.getDatList(userNo);
                if (Objects.isNull(datList)){
                    return VoBuilder.ok("数据库订单为空",null);
                }
                return VoBuilder.ok("数据库订单查询成功",datList);
            } else {
                List<OrderBackList> cacheList = orderService.getCacheList(userNo);
                List<OrderBackList> datList = orderService.getDatList(userNo);
                Map<String,List> orderMap = new HashMap<>();
                orderMap.put("cache",cacheList);
                orderMap.put("data",datList);

                return VoBuilder.ok("订单查询成功",orderMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }



    @GetMapping("/seckill")
    @PreAuthorize("hasAuthority('sys:user:order:seckill')")
    public Result orderSecKill(
            @RequestParam("secNo")String secNo,
            HttpServletRequest httpServletRequest
    ){
        String token = httpServletRequest.getHeader("token");
        try {
            String userNo = JwtUtil.parseJWT(token).getSubject();
            return seckillService.orderSeckill(secNo,userNo);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }}

}
