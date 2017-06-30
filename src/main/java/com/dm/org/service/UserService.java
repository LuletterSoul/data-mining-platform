package com.dm.org.service;

import com.dm.org.dao.UserDao;
import com.dm.org.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * @author ÁõÏéµÂ qq313700046@icloud.com .
 * @date created in  23:00 2017/6/28.
 * @description
 * @modified by:
 */
@Service
@Transactional
public class UserService
{

    private UserDao userDao;

    @Autowired
    public void setUserDao(UserDao userDao)
    {
        this.userDao = userDao;
    }

    public UserDao getUserDao()
    {
        return userDao;
    }

    public User save()
    {
        String randomId=UUID.randomUUID().toString().substring(0,8);
        String randomName=UUID.randomUUID().toString().substring(0,8);
        User user = new User(randomId, randomName, "ÄÐ", 100);
        userDao.saveUser(user);
        return user;
    }

    public List<User> findAllUsers()
    {
        return userDao.getUserList();
    }
}
