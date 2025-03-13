package com.bigdata.graphqldemov2.service;

import com.bigdata.graphqldemov2.model.User;
import com.bigdata.graphqldemov2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public List<User> getUsersByOrganization(Long orgId) {
        return userRepository.findByOrganizationId(orgId);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }
}
