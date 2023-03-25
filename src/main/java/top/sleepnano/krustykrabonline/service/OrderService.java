package top.sleepnano.krustykrabonline.service;

import org.springframework.stereotype.Service;
import top.sleepnano.krustykrabonline.dto.OrderBackList;
import top.sleepnano.krustykrabonline.dto.OrderRedis;
import top.sleepnano.krustykrabonline.dto.RestOrderProduct;
import top.sleepnano.krustykrabonline.vo.Result;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface OrderService extends BaseService{
    Result addOrderToRedis(List<RestOrderProduct> products, String userNo);

    Result addOrderToRedis(List<RestOrderProduct> products, String secNo, String userNo, BigDecimal prise);

    Result cancelOrder(String orderNo, String userNo);

    Result paymentSucceeded(String key,String method,String status);

    void orderCacheExpired(String toString);

    List<OrderBackList> getCacheList(String userNo);

    List<OrderBackList> getDatList(String userNo);
}
