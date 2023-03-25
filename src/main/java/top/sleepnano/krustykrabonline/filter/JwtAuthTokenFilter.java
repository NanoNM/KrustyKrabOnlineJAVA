package top.sleepnano.krustykrabonline.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.MalformedJwtException;
import io.netty.util.internal.StringUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import top.sleepnano.krustykrabonline.dto.LoginUser;
import top.sleepnano.krustykrabonline.util.JwtUtil;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class JwtAuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    RedisTemplate redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 截取token
        String token = request.getHeader("token");
        if (StringUtil.isNullOrEmpty(token)){
            filterChain.doFilter(request, response);
            return;
        }
        // 解析token
        String userNo = null;
        try {
            userNo = JwtUtil.parseJWT(token).getSubject();
            // TODO 需要判断userNo为null的情况 但是现在不需要
            LoginUser loginUser = (LoginUser) redisTemplate.opsForValue().get("login:"+userNo);

            if (Objects.nonNull(loginUser)){
                List<SimpleGrantedAuthority> simpleGrantedAuthorities =
                        loginUser.getPermissions().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =

                        new UsernamePasswordAuthenticationToken(loginUser,null,simpleGrantedAuthorities);
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        } catch (Exception e) {
            // 异常捕获，发送到error controller
            request.setAttribute("filter.error", e);
            //将异常分发到/error/exthrow控制器
            request.getRequestDispatcher("/error/exthrow").forward(request, response);

        }
        filterChain.doFilter(request,response);
    }
}
