package top.sleepnano.krustykrabonline.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
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
 * @since 2023-03-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("product_list")
@ApiModel(value="ProductList对象", description="")
public class ProductList implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String productName;

    private Integer productCatagory;

    private BigDecimal productPrice;

    private String productDesc;

    private String productNo;

    private String img;

    private String productHits;

    private Integer productStatus;

    private LocalDateTime createTime;

    private LocalDateTime modifyTime;


}
