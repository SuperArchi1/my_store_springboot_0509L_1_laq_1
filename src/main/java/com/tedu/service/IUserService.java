package com.tedu.service;

import com.tedu.entity.User;

import java.util.Date;

public interface IUserService {
    public Boolean checkUsername(String username);
    public void regist(User user);
    public User login(String username, String password);
    public void changePassword(User user);
    public void updateUserInfo( User user);
    public void changeAvatar(User user) throws Exception;



}
