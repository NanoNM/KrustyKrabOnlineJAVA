package top.sleepnano.krustykrabonline.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import top.sleepnano.krustykrabonline.entity.OrderProduct;
import top.sleepnano.krustykrabonline.entity.SelectOrder;
import top.sleepnano.krustykrabonline.entity.SysOrder;
import top.sleepnano.krustykrabonline.entity.UserOrder;

import java.util.List;

public interface OrderMapper {
    Integer insertPaidOrder(SysOrder sysOrder);

    Integer insertOrderUser(UserOrder userOrder);

    Integer insertOrderProduct(List<OrderProduct> orderProducts);

    List<SelectOrder> getDatOrderList(String orderNo);

    SysOrder selectSysOrderByOrderNo(String orderNo);

    List<String> getUserOrderList(String userNo);
}
