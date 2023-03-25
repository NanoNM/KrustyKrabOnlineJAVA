package top.sleepnano.krustykrabonline.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.sleepnano.krustykrabonline.dto.CartItem;
import top.sleepnano.krustykrabonline.entity.Product;
import top.sleepnano.krustykrabonline.mapper.ProductMapper;
import top.sleepnano.krustykrabonline.service.ProductService;
import top.sleepnano.krustykrabonline.util.VoBuilder;
import top.sleepnano.krustykrabonline.vo.Result;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 商品实现类
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private Result result;
    @Value("${KK.redis.cart.limit}")
    Integer cartLimit;

    /**
     * 查询商品方法
     * @param num 页码
     * @param pageSize 每页数量
     * @return VO数据
     */
    public Result getProduct(Integer num,Integer pageSize){
        if (num<=0){
            return VoBuilder.error("无法从"+num+"页开始查询", null);
        }
        List<Product> products = null;
        products = productMapper.selectProductByPage((num - 1) * pageSize, pageSize);
        if (products.isEmpty()){
            result = VoBuilder.wrong("查询失败, 数据为空",products);
        }else {
            result = VoBuilder.ok("查询成功",products);
        }
        return result;
    }
    @Override
    @Deprecated
    public Result getAllProduct() {
        List<Product> products = null;
        products = productMapper.selectAllProduct();

//        ValueOperations ops = redisTemplate.opsForValue();
//        String productsJsonString = (String) ops.get("products");
//        if (productsJsonString == null){
//            products = productMapper.selectAllProduct();
//            ops.set("products", JSON.toJSONString(products));
//        }else {
//            products = JSONObject.parseArray(productsJsonString, Product.class);
//        }

//        if (products==null){
//            result = voBuilder.genVoModel("查询失败, 查询数据为NULL","0", null);
//        }
//        result = voBuilder.genVoModel("查询成功","0",products);

        return result;
    }

    /**
     * 添加购物车实现方法
     * @param productNo 商品No
     * @param num 商品数量
     * @param userNo 用户No
     * @return 返回Vo类
     */
    @Override
    public Result addToShoppingCart(String productNo, Integer num,String userNo) {
        // 判断是否是清除购物车
        if ("clearAll".equals(productNo) && num == 0){
            Boolean delete = redisTemplate.delete("cart:" + userNo);
            if (!delete){
                throw new RuntimeException("redis 无法清空购物车");
            }

            return VoBuilder.ok("购物车已清空",null);
        }
        // 从数据库取商品并判空
        Product product = productMapper.selectProductByNo(productNo);
        if (Objects.isNull(product)){
            throw new RuntimeException("商品未找到 无法加入购物车");
        }
        // 从缓存中查询是否存在缓存
        List<CartItem> cartItems = (List<CartItem>) redisTemplate.opsForValue().get("cart:" + userNo);
        // 之前存在购物车的可能
        if (Objects.nonNull(cartItems)){
            if(cartItems.size() >= cartLimit){
                return VoBuilder.wrong("无法加入购物车: 已达购物车上限制",cartItems);
            }
            // 创建要删除掉的购物车标记集合
            List<CartItem> delMarked = new ArrayList<>();
            // 创建是佛新建购物车标记
            AtomicReference<Boolean> addNewMark = new AtomicReference<>(true);
            // 去除的数据遍历修改
            cartItems.forEach(cartItem -> {
                if (productNo.equals(cartItem.getProduct().getProductNo())){
                    addNewMark.set(false);
                    if (cartItem.getNum()+num<=0){
                        delMarked.add(cartItem);
                    }else {
                        cartItem.setNum(cartItem.getNum()+num);
                    }
                }
            });
            cartItems.removeAll(delMarked);
            if (addNewMark.get()){
                if (num<=0){
                    throw new RuntimeException("无法添加"+num+"件商品到购物车");
                }
                cartItems.add(new CartItem(product,num));
            }
            // 重新写回缓存

            if (cartItems.isEmpty()){
                redisTemplate.delete("cart:" + userNo);
                return VoBuilder.ok("购物车已清空",null);
            }else {
                redisTemplate.opsForValue().set("cart:" + userNo,cartItems);
            }
            return VoBuilder.ok("购物车添加成功",cartItems);
        }else {
            if (num<=0){
                throw new RuntimeException("无法添加"+num+"件商品到购物车");
            }
            // 之前不存在购物车的可能
            List<CartItem> cartItemsRedis = new ArrayList<>();
            cartItemsRedis.add(new CartItem(product,num));
            redisTemplate.opsForValue().set("cart:" + userNo,cartItemsRedis);
            return VoBuilder.ok("购物车添加成功",cartItemsRedis);
        }
    }


}
