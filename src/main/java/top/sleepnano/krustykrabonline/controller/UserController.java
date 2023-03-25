package top.sleepnano.krustykrabonline.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.sleepnano.krustykrabonline.service.UserService;
import top.sleepnano.krustykrabonline.util.VoBuilder;
import top.sleepnano.krustykrabonline.vo.Result;

/**
 * User相关
 */
@RestController
@RequestMapping("user")
public class UserController implements BaseController{

    @Autowired
    UserService userService;

    @GetMapping("nlg")
    public Result needLogin(){
        return VoBuilder.wrong("需要登录",null);
    }

    @PostMapping("register")
    public Result userRegister(@RequestParam String name,@RequestParam String pass,@RequestParam String phone){
        return userService.userRegister(name,pass,phone);
    }

    @GetMapping("logout")
    public Result userLogout(){
        return null;
    }
}
