package com.bigdata.graphqldemo.service;

import com.bigdata.graphqldemo.dto.UserCreateInput;
import com.bigdata.graphqldemo.dto.UserPage;
import com.bigdata.graphqldemo.dto.UserUpdateInput;
import com.bigdata.graphqldemo.model.User;
import org.springframework.data.domain.Pageable;

public interface UserService {

    User getUserById(Long id);

    UserPage getAllUsers(Pageable pageable);

    UserPage searchUsers(String keyword, Pageable pageable);

    User createUser(UserCreateInput input);

    User updateUser(Long id, UserUpdateInput input);

    boolean deleteUser(Long id);

    User assignUserToOrganization(Long userId, Long organizationId);

    User removeUserFromOrganization(Long userId);
}