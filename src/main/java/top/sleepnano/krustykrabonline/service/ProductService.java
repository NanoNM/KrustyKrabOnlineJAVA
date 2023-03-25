package top.sleepnano.krustykrabonline.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import top.sleepnano.krustykrabonline.vo.Result;

@Service
public interface ProductService extends BaseService {

    Result getProduct(Integer num,Integer pageSize);
    Result getAllProduct();
    Result addToShoppingCart(String no,Integer num,String userNo);

}
