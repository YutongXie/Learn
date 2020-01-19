package com.huitong.learn.dao;

import com.huitong.learn.entity.User;

import java.util.List;

public interface UserDAO {
    public boolean isUserExist(User user);
    public void saveUser(List<User> user);
    public void saveUser(User user);
    public List<User> getUserByName(String name);

}
