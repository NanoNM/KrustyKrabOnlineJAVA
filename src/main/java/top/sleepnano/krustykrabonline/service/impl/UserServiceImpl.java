package top.sleepnano.krustykrabonline.service.impl;

import io.jsonwebtoken.Jwt;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import top.sleepnano.krustykrabonline.dto.LoginUser;
import top.sleepnano.krustykrabonline.entity.User;
import top.sleepnano.krustykrabonline.mapper.UserMapper;
import top.sleepnano.krustykrabonline.service.UserService;
import top.sleepnano.krustykrabonline.util.JwtUtil;
import top.sleepnano.krustykrabonline.util.VoBuilder;
import top.sleepnano.krustykrabonline.vo.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 用户操作相关 管理SpringSecurity登录以及注册相关
 */
@Service
public class UserServiceImpl implements UserDetailsService, UserService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 重写 UserDetailsService 方法实现数据库登录
     * @param username 前段传入的用户名
     * @return 返回一个UserDetails对象 详见LoginUser
     * @throws UsernameNotFoundException 用户名未找到
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.selectUserByUserName(username);
        if (Objects.isNull(user)){
            throw new RuntimeException("无效的用户名和密码");
        }

        List<String> permissions = userMapper.getUserPermission(user.getUserNo());
        return new LoginUser(user,permissions);
    }

    @Override
    public User selectUserByName(String username) {
        return userMapper.selectUserByUserName(username);
    }

    /**
     * 用户注册相关
     * @param username 用户名
     * @param passwd 密码
     * @param phone 手机号码
     * @return Vo类
     */
    @Override
    public Result userRegister(String username, String passwd, String phone) {
        // 密码加密
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encodePass = bCryptPasswordEncoder.encode(passwd);

        try{
            userMapper.insertUser(username, encodePass, phone);
            return VoBuilder.ok("注册成功",null);
        }catch (DuplicateKeyException e){
            return VoBuilder.wrong("用户名被使用",null);
        }
    }

    /**
     * 登录成功逻辑
     * @param authentication
     * @return Vo类
     */
    @Override
    public Result successLogin(Authentication authentication) {
        LoginUser loginUser = (LoginUser)authentication.getPrincipal();
        String userNo = loginUser.getUser().getUserNo();
        String jwt = JwtUtil.createJWT(userNo);
        Map<String,String> jwtMap = new HashMap<>();
        jwtMap.put("token",jwt);
        redisTemplate.opsForValue().set("login:"+userNo,loginUser);
        return VoBuilder.ok("登录成功", jwtMap);
    }

    @Override
    public Result successLogout(HttpServletRequest request) throws Exception {
        return null;
    }

    @Override
    public void logout(String token) {

        String userNo = null;
        try {
            userNo = JwtUtil.parseJWT(token).getSubject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Boolean delete = redisTemplate.delete("login:" + userNo);
        if (Boolean.TRUE.equals(delete)){
            throw new RuntimeException("redis未能删除登录的用户");
        }
    }
}
