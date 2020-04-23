package com.example.securitydemo.config;

import com.example.securitydemo.dao.PermissionMapper;
import com.example.securitydemo.domain.Permission;
import com.example.securitydemo.service.UserService;
import com.example.securitydemo.utils.MD5Util;
import org.apache.tomcat.util.security.MD5Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
@Configuration
public class SpringSecurityConf extends WebSecurityConfigurerAdapter {
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private UserService userService;

    /*
      加密验证对比
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return MD5Util.md5Encrypt32Lower((String) rawPassword);
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                String encode = MD5Util.md5Encrypt32Lower((String) rawPassword);
                encodedPassword = encodedPassword.replace("\r\n","");
                boolean b = encodedPassword.equals(encode);
                return b;
            }
        });
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //获取所有权限，动态权限验证
        List<Permission> permissions = permissionMapper.findAllPermission();
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry authorizeRequests =
                http.authorizeRequests();
        permissions.forEach(permission -> {
            System.out.println("获取的权限为" + permission.getPermTag());
            authorizeRequests.antMatchers(permission.getUrl()).hasAnyAuthority(permission.getPermTag());
        });
        authorizeRequests.antMatchers("/login")
                .permitAll() //登录跳转url无需验证
                .antMatchers("/**")
                .fullyAuthenticated()
                .and()
                .formLogin() //表单认证
                .loginPage("/login")
                .and()
                .csrf()
                .disable();
    }
}
