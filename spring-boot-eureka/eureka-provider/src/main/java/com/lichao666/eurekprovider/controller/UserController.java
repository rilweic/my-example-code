package com.lichao666.eurekprovider.controller;


import com.lichao666.eurekprovider.entity.User;
import com.lichao666.eurekprovider.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/list")
    public List<User> getUser() {
        List<User> list = userService.list();
        return list;
    }
}
