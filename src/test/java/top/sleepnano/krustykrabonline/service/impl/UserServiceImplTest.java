package top.sleepnano.krustykrabonline.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.sleepnano.krustykrabonline.service.UserService;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {
    @Autowired
    UserService userService;

    @Test
    void selectUserByName() {
        userService.selectUserByName("user01");
    }

    @Test
    void userRegister() {
        userService.userRegister("user02","1234","155560008090");
    }
}