package top.sleepnano.krustykrabonline.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import top.sleepnano.krustykrabonline.entity.User;
import top.sleepnano.krustykrabonline.vo.Result;

@Service
public interface UserService extends BaseService{

    User selectUserByName(String username);

    Result userRegister(String username, String passwd, String phone);

    Result successLogin(Authentication authentication);

    Result successLogout(HttpServletRequest request) throws Exception;

    void logout(String token);
}
