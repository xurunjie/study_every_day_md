package com.kedacom.service;

import com.kedacom.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void insertUser(){
        userDao.insert();
        System.out.println("插入完成");
    }
}
