package top.sleepnano.krustykrabonline.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserMapperTest {
    @Autowired
    UserMapper userMapper;

    @Test
    void getUserPermission() {
        List<String> userPermission = userMapper.getUserPermission("985dcb16-b9fb-11ed-a7fa-00e04c86af91");
        for (String s : userPermission) {
            System.out.println("s = " + s);
        }
    }
}