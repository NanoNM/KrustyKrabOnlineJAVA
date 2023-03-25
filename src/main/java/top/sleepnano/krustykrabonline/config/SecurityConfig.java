package top.sleepnano.krustykrabonline.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import top.sleepnano.krustykrabonline.dto.LoginUser;
import top.sleepnano.krustykrabonline.dto.MyAManager;
import top.sleepnano.krustykrabonline.filter.JwtAuthTokenFilter;
import top.sleepnano.krustykrabonline.service.impl.UserServiceImpl;
import top.sleepnano.krustykrabonline.util.JwtUtil;
import top.sleepnano.krustykrabonline.util.VoBuilder;
import top.sleepnano.krustykrabonline.vo.Result;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * SpringSecurity配置类
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig{
    @Autowired
    Result result;

    @Autowired
    UserServiceImpl userService;
    @Autowired
    JwtAuthTokenFilter jwtAuthTokenFilter;

    @Value("${KK.needlogin}")
    String needLogin;

    
    @Bean
    public SecurityFilterChain filerChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//                .exceptionHandling().authenticationEntryPoint().accessDeniedHandler().and()
                .authorizeHttpRequests()
                .requestMatchers("/lg/**","/alg/**").authenticated()
                .requestMatchers("/**").permitAll()
                .and().formLogin()
                .loginPage("/user/nlg")
                .loginProcessingUrl("/user/lg")
                .successHandler(new MyAuthenticationSuccessHandler())
                .failureHandler(new MyAuthenticationFailureHandler())
                .and()
                .logout()
                .logoutUrl("/user/lgout")
                .addLogoutHandler(new MyLogoutHandler())
                .logoutSuccessHandler(new MyLogoutSuccessHandler())
                .and().csrf().disable()
                .userDetailsService(userService)
                .addFilterBefore(jwtAuthTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }


    /**
     * 配置跨源访问(CORS)
     * @return
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }

    /**
     * 指定密码加密器
     * @return 密码加密器
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    /**
     * 自定义登出操作
     */
    class MyLogoutHandler implements LogoutHandler{

        @Override
        public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
            String token = request.getHeader("token");
            userService.logout(token);
        }
    }


    /**
     * 登出成功后的逻辑
     */
    class MyLogoutSuccessHandler implements LogoutSuccessHandler {
        @Override
        public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            result = VoBuilder.ok("登出成功",null);
            response.setContentType("application/json;charset=UTF-8");
            String s = new ObjectMapper().writeValueAsString(result);
            response.getWriter().println(s);
        }
    }

    /**
     * 登录成功后返回的数据
     */
    class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
        @Override
        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            result = userService.successLogin(authentication);
            response.setContentType("application/json;charset=UTF-8");
            String s = new ObjectMapper().writeValueAsString(result);
            response.getWriter().println(s);
        }
    }

    /**
     * 登陆失败后返回的数据
     */
    class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {
        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
            result = VoBuilder.wrong("登录失败", null);
            response.setContentType("application/json;charset=UTF-8");
            String s = new ObjectMapper().writeValueAsString(result);
            response.getWriter().println(s);
        }
    }



}
