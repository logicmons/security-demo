package com.example.securitydemo.service;

import com.example.securitydemo.dao.UserMapper;
import com.example.securitydemo.domain.Permission;
import com.example.securitydemo.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userMapper.findUserByName(s);
        if (ObjectUtils.isEmpty(user)){
            throw new UsernameNotFoundException("用户不存在");
        }

        //查询用户权限
        List<GrantedAuthority> authorities = new ArrayList<>();
        List<Permission> perms = userMapper.findPermsByUsername(s);
        perms.forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.getPermTag())));

        //设置用户权限
        user.setAuthorities(authorities);
        return user;
    }
}
