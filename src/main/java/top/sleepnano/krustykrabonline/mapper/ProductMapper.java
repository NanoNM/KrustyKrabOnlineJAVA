package top.sleepnano.krustykrabonline.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import top.sleepnano.krustykrabonline.dto.RestOrderProduct;
import top.sleepnano.krustykrabonline.entity.Product;

import java.util.List;

public interface ProductMapper extends BaseMapper<Product> {
    @Select("Select " +
            "id," +
            "product_name," +
            "product_category," +
            "product_price," +
            "product_desc," +
            "product_no," +
            "img," +
            "product_hits," +
            "product_status," +
            "create_time," +
            "modify_time from product_list")
    List<Product> selectAllProduct();

    @Select("Select " +
            "id," +
            "product_name," +
            "product_category," +
            "product_price," +
            "product_desc," +
            "product_no," +
            "img," +
            "product_hits," +
            "product_status," +
            "create_time," +
            "modify_time from product_list limit #{page},#{pageSize}")
    List<Product> selectProductByPage(Integer page,Integer pageSize);

    @Select("Select " +
            "id," +
            "product_name," +
            "product_category," +
            "product_price," +
            "product_desc," +
            "product_no," +
            "img," +
            "product_hits," +
            "product_status," +
            "create_time," +
            "modify_time from product_list where product_no = #{productNo}")
    Product selectProductByNo(String productNo);

//    @Select("Select " +
//            "id," +
//            "product_name," +
//            "product_category," +
//            "product_price," +
//            "product_desc," +
//            "product_no," +
//            "img," +
//            "product_hits," +
//            "product_status," +
//            "create_time," +
//            "modify_time from product_list where product_no IN(#{nos})")
    List<Product> selectProductByNos(List<RestOrderProduct> nos);

    List<Product> selectProductByNosString(List<String> nos);



}
