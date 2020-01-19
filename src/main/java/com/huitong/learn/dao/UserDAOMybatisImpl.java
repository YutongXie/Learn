package com.huitong.learn.dao;

import com.huitong.learn.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository(value="userDAOMybatis")
public class UserDAOMybatisImpl implements UserDAO{
    @Override
    public boolean isUserExist(User user) {
        return false;
    }

    @Override
    public void saveUser(List<User> user) {

    }

    @Override
    public void saveUser(User user) {

    }

    @Override
    public List<User> getUserByName(String name) {
        return null;
    }
}
