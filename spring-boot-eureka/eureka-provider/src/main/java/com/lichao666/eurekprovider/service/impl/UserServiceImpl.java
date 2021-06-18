package com.lichao666.eurekprovider.service.impl;

import com.lichao666.eurekprovider.entity.User;
import com.lichao666.eurekprovider.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public List<User> list() {
        return Arrays.asList(
                new User(1,"李四"),
                new User(1,"张三")
        );
    }
}
