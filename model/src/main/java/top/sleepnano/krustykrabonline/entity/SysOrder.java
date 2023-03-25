package top.sleepnano.krustykrabonline.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author author
 * @since 2023-03-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_order")
@ApiModel(value="SysOrder对象", description="")
public class SysOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "订单No")
    private String orderNo;

    @ApiModelProperty(value = "付款标记")
    private Integer isPaid;

    @ApiModelProperty(value = "支付方式")
    private String paymentMethod;

    private String status;

    private LocalDate createTime;

    private LocalDate modifyTime;

    private LocalDate sentTime;

    private LocalDate completionTime;

    private LocalDate genTime;


}
