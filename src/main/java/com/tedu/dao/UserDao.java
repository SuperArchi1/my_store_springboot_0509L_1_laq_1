package com.tedu.dao;

import com.tedu.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;
import java.util.Date;

public interface UserDao {
    //判断用户名是否重复
    public int getByUsername(String username);
    //用户注册
    public void regist(User user);
    //用户登录
    public User login(String username);
    //修改密码
    public void updatePasswordByUid(User user);
    //修改密码
    public void updateInfoByUid(User user);
    //頭像
    public void updateAvatar(User user);
}
