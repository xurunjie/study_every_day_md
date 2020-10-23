package com.kedacom.service;

import com.kedacom.dao.UserDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    public void insertUser(){
        userDao.insert();
        System.out.println("插入完成");
        int i = 10 / 0;
    }
}
