package com.huitong.learn.service;

import com.huitong.learn.dao.UserDAO;
import com.huitong.learn.entity.User;
import com.huitong.learn.entity.UserType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserService {
    @Autowired
    private UserDAO userDAO;

    @RequestMapping(value = "/userservice/save", method = RequestMethod.GET)
    public void saveUser(User user) {
        if(isValid(user)) {
            userDAO.saveUser(user);
        } else {
            //for test only
            User defaultUser = new User();
            defaultUser.setId(2);
            defaultUser.setName("default user");
            defaultUser.setUserType(UserType.INTERNAL_USER);
//            userDAO.saveUser(defaultUser);
            List<User> userList = new ArrayList<>();
            userList.add(defaultUser);
            userDAO.saveUser(userList);
        }
    }

    private boolean isValid(User user) {
        if(user == null) {
          return false;
        }
        if(user.getId() <= 0) {
            return false;
        }
        if(StringUtils.isBlank(user.getName())) {
            return false;
        }
        if(user.getUserType() == null) {
            return false;
        }
        return true;
    }
    @RequestMapping(value = "/userservice/getUserByName", method = RequestMethod.GET)
    public List<User> getUserByName(String name) {
        if(StringUtils.isNotBlank(name)) {
            return userDAO.getUserByName(name);
        } else {
            System.out.println("Invalid query parameter");
            return new ArrayList<>();
        }
    }
}
