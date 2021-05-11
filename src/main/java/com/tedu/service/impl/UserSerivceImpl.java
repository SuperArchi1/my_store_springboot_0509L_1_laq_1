package com.tedu.service.impl;

import com.tedu.dao.UserDao;
import com.tedu.entity.User;
import com.tedu.exception.UserException;
import com.tedu.service.IUserService;
import com.tedu.util.CodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service("userService")
public class UserSerivceImpl implements IUserService {
    @Resource
    private UserDao userDao;

    @Override
    public Boolean checkUsername(String username) {
        int count = userDao.getByUsername(username);
        if(count > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void regist(User user) {
        String newPassword = CodeUtil.getMD5Encoding(user.getPassword());
        user.setPassword(newPassword);
        userDao.regist(user);
    }

    @Override
    public User login(String username, String password) {
        User user = userDao.login(username);
        if(user == null){
            throw new UserException("用户名不存在");
        }
        if(!CodeUtil.getMD5Encoding(password).equals(user.getPassword())) {
            throw new UserException("密码错误");
        }
        return user;
    }

    @Override
    public void changePassword(User user) {

        userDao.updatePasswordByUid(user);
    }

    @Override
    public void updateUserInfo(User user) {
        userDao.updateInfoByUid(user);

    }
    @Override
    public void changeAvatar(User user) throws Exception {
        // 非空验证 TODO
        // 基于用户名查询用户信息
        int exist = 0;
        try {
            exist = userDao.getByUsername(user.getUsername());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("修改头像异常：" + e.getMessage(), e);
        }
        // 验证用户信息是否有效
        if (exist == 0) {
            throw new Exception("修改头像异常：用户不存在或已删除");
        }
        // 更新用户信息

           userDao.updateAvatar(user);

    }

}
