package com.example.securitydemo.dao;


import com.example.securitydemo.domain.Permission;
import com.example.securitydemo.domain.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper {
    @Select("SELECT * FROM `tb_user` WHERE `username` = #{username}")
    User findUserByName(String username);

    @Select("SELECT perm.* FROM  " +
            "tb_permission perm inner join `tb_role_permission` trp " +
            "on perm.id = trp.perm_id JOIN `tb_user_role` tur " +
            "ON trp.role_id = tur.role_id " +
            " JOIN `tb_user` on tur.user_id = `tb_user`.id " +
            "WHERE `tb_user`.username = #{username}")
    List<Permission> findPermsByUsername(String username);
}
