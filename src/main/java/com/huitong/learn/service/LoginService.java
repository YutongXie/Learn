package com.huitong.learn.service;

import com.huitong.learn.dao.UserDAO;
import com.huitong.learn.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginService {
    @Autowired
    private UserDAO userDAO;

    @RequestMapping(value="/login", method = RequestMethod.POST)
    public void login(User user) {

        if(userDAO.isUserExist(user)) {
            System.out.println("user exist, login success");
        } else {
            System.out.println("user not exist, login failed");
        }
    }
    @RequestMapping(value = "/loginById", method = RequestMethod.GET)
    public void login(@RequestParam(value="id", required = true) int id, @RequestParam(value="password", required = true) String password) {

    }
    @RequestMapping(value = "/loginByPathVariable/{id}")
    public void login(@PathVariable(value = "id") int id) {
        System.out.println("login with @PathVariable");
    }
}
