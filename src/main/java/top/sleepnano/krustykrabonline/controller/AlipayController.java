package top.sleepnano.krustykrabonline.controller;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.sleepnano.krustykrabonline.dto.AliPayResource;
import top.sleepnano.krustykrabonline.dto.LoginUser;
import top.sleepnano.krustykrabonline.dto.OrderRedis;
import top.sleepnano.krustykrabonline.service.OrderService;
import top.sleepnano.krustykrabonline.util.JwtUtil;
import top.sleepnano.krustykrabonline.util.RedisUtil;
import top.sleepnano.krustykrabonline.util.VoBuilder;
import top.sleepnano.krustykrabonline.vo.Result;

import java.util.*;

/**
 * @author: HuGoldWater
 * @description:
 */
@Slf4j
@RestController
@RequestMapping("payment")
public class AlipayController {

    @Autowired
    private AliPayResource aliPayResource;
    @Autowired
    private OrderService orderService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RedisUtil redisUtil;

    /**
     * 前往支付宝进行支付
     */
    @GetMapping(value="/goAlipay")
    public Result goAlipay(String merchantOrderId,HttpServletRequest request) throws Exception{
        String token = request.getHeader("token");
        String userNo;
        try {
            userNo = JwtUtil.parseJWT(token).getSubject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        OrderRedis orderRedis = (OrderRedis) redisTemplate.opsForValue().get("order:" + userNo + ", orderID:" + merchantOrderId);
        LoginUser loginUser = (LoginUser) redisTemplate.opsForValue().get("login:" + userNo);

        if (Objects.isNull(orderRedis)){
            throw new RuntimeException("订单已失效或已被支付");
        }

        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(aliPayResource.getGatewayUrl(),
                aliPayResource.getAppId(),
                aliPayResource.getMerchantPrivateKey(),
                "json",
                aliPayResource.getCharset(),
                aliPayResource.getAlipayPublicKey(),
                aliPayResource.getSignType());

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(aliPayResource.getReturnUrl());
        alipayRequest.setNotifyUrl(aliPayResource.getNotifyUrl());

        // 商户订单号, 商户网站订单系统中唯一订单号, 必填
        String out_trade_no = merchantOrderId;
        // 付款金额, 必填 单位元
        String total_amount = orderRedis.getAllPrice().toString();
        // 订单名称, 必填
        String subject = "付款用户: "+loginUser.getUser().getUserName()+"[" + userNo + "]";
        // 商品描述, 可空, 目前先用订单名称
        String body = subject;

        // 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
        String timeout_express = "10m";
        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"timeout_express\":\""+ timeout_express +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        //请求
        String alipayForm = "";
        try {
            alipayForm = alipayClient.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        log.info("支付宝支付 - 前往支付页面, alipayForm: \n{}", alipayForm);
        return VoBuilder.ok("",alipayForm);
    }


    /**
     * 支付成功后的支付宝异步通知
     */
    @RequestMapping(value="/alipay")
    public String alipay(HttpServletRequest request, HttpServletResponse response,
                         @RequestParam("tmpno")String out_trade_no1,
                         @RequestParam("tmpst")String trade_status1
    ) throws Exception {
        // TODO:临时 order:*, orderID:*
        List<String> keys1 = redisUtil.getKey("order:*, orderID:*"+out_trade_no1);
        if (keys1.isEmpty()){
            throw new RuntimeException("缓存中找不到相关key 来自: " + out_trade_no1);
        }
        String key1 = keys1.get(0);
        orderService.paymentSucceeded(key1,"支付宝",trade_status1);
        return "success";




//        log.info("支付成功后的支付宝异步通知");
//        // 验证支付宝传过来的数据
//        Boolean aBoolean = genAliCheckMap(request);
//
//        if(aBoolean) {//验证成功
//
//            // 商户订单号
//            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
//            // 支付宝交易号
//            String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
//            // 交易状态
//            String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
//            // 付款金额
//            String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");
//
//            if (trade_status.equals("TRADE_SUCCESS")){
////                String merchantReturnUrl = paymentOrderService.updateOrderPaid(out_trade_no, CurrencyUtils.getYuan2Fen(total_amount));
////                notifyFoodieShop(out_trade_no,merchantReturnUrl);
//            }
//
//            log.info("************* 支付成功(支付宝异步通知) - 时间:  *************");
//            log.info("* 订单号: {}", out_trade_no);
//            log.info("* 支付宝交易号: {}", trade_no);
//            log.info("* 实付金额: {}", total_amount);
//            log.info("* 交易状态: {}", trade_status);
//            log.info("*********************************************************");
//
//            List<String> keys = redisUtil.getKey("*"+out_trade_no+"*");
//            if (keys.isEmpty()){
//                throw new RuntimeException("缓存中找不到相关key 来自: " + out_trade_no);
//            }
//            String key = keys.get(0);
//            orderService.paymentSucceeded(key,"支付宝",trade_status);
//            return "success";
//        }else {
//            //验证失败
//            log.info("验签失败, 时间: ");
//            return "fail";
//        }
    }

    private Boolean genAliCheckMap(HttpServletRequest request) throws AlipayApiException {
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
//       valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }
        // 返回验证表示
        return AlipaySignature.rsaCheckV1(params,
                aliPayResource.getAlipayPublicKey(),
                aliPayResource.getCharset(),
                aliPayResource.getSignType());
    }

    @RequestMapping(value="/alipayResult")
    public Result alipayResult(HttpServletRequest httpServletRequest) throws AlipayApiException {
        //获取支付宝过来反馈信息
        Boolean aBoolean = genAliCheckMap(httpServletRequest);
        if (!aBoolean){
            throw new RuntimeException("无法验证返回数据");
        }
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(aliPayResource.getGatewayUrl(),
                aliPayResource.getAppId(),
                aliPayResource.getMerchantPrivateKey(),
                "json",
                aliPayResource.getCharset(),
                aliPayResource.getAlipayPublicKey(),
                aliPayResource.getSignType());

        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizContent("{\"out_trade_no\":\""+ httpServletRequest.getParameter("out_trade_no") + "\"}");
        AlipayTradeQueryResponse response = alipayClient.execute(request);
        if (!response.isSuccess()) {
            throw new RuntimeException("调用订单状态查询失败");
        }
        JSONObject jsonObject=JSONObject.parseObject(response.getBody());
        JSONObject alipay_trade_query_response = jsonObject.getJSONObject("alipay_trade_query_response");
        if (!"Success".equals(alipay_trade_query_response.get("msg"))){
            throw new RuntimeException("查询订单状态失败");
        }
        if ("TRADE_SUCCESS".equals(alipay_trade_query_response.get("trade_status")) || "TRADE_FINISHED".equals(alipay_trade_query_response.get("trade_status"))){
            return VoBuilder.ok("交易已完成",null);
        }
        return VoBuilder.wrong("交易未完成或交易已关闭",null);
    }
}
