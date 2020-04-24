package com.example.securitydemo.utils;


import org.junit.jupiter.api.Test;

public class MD5UtilTest {

    @Test
    public void md5Encrypt32Lower() {
        String s = MD5Util.md5Encrypt32Lower("154255");
        System.out.println(s);
    }
}