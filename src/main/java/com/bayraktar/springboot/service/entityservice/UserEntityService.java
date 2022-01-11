package com.bayraktar.springboot.service.entityservice;

import com.bayraktar.springboot.dao.UserDao;
import com.bayraktar.springboot.entity.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserEntityService extends BaseEntityService<User, UserDao>{

    public UserEntityService(UserDao userDao) {
        super(userDao);
    }

    public Optional<User> findByName(String name) {
        return getDao().findByName(name);
    }
}
