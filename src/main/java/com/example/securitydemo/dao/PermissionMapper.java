package com.example.securitydemo.dao;

import com.example.securitydemo.domain.Permission;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PermissionMapper {
    @Select("SELECT * FROM `tb_permission`")
    List<Permission>  findAllPermission();
}
