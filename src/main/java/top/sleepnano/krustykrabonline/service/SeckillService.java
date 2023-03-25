package top.sleepnano.krustykrabonline.service;

import org.springframework.stereotype.Service;
import top.sleepnano.krustykrabonline.dto.RestOrderProduct;
import top.sleepnano.krustykrabonline.dto.SeckillRedisOrder;
import top.sleepnano.krustykrabonline.vo.Result;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface SeckillService extends BaseService{

//    void addSeckillToRedis(String productNo,Integer num,Long startTime,Long endTime);

    Result addSeckillToRedis(List<RestOrderProduct> productNo, Integer num, Long startTime, Long endTime, BigDecimal prise);

    List<SeckillRedisOrder> listCacheSeckill();

    Result orderSeckill(String secNo,String userNo);
}
