package com.bigdata.graphqldemo.service.impl;

import com.bigdata.graphqldemo.dto.PageInfo;
import com.bigdata.graphqldemo.dto.UserCreateInput;
import com.bigdata.graphqldemo.dto.UserPage;
import com.bigdata.graphqldemo.dto.UserUpdateInput;
import com.bigdata.graphqldemo.exception.ResourceNotFoundException;
import com.bigdata.graphqldemo.model.Organization;
import com.bigdata.graphqldemo.model.User;
import com.bigdata.graphqldemo.repository.OrganizationRepository;
import com.bigdata.graphqldemo.repository.UserRepository;
import com.bigdata.graphqldemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public UserPage getAllUsers(Pageable pageable) {
        Page<User> userPage = userRepository.findAll(pageable);
        return buildUserPage(userPage);
    }

    @Override
    @Transactional(readOnly = true)
    public UserPage searchUsers(String keyword, Pageable pageable) {
        Page<User> userPage = userRepository.searchUsers(keyword, pageable);
        return buildUserPage(userPage);
    }

    @Override
    @Transactional
    public User createUser(UserCreateInput input) {
        // 检查用户名和邮箱是否已存在
        if (userRepository.existsByUsername(input.getUsername())) {
            throw new IllegalArgumentException("Username already exists: " + input.getUsername());
        }

        if (userRepository.existsByEmail(input.getEmail())) {
            throw new IllegalArgumentException("Email already exists: " + input.getEmail());
        }

        User user = User.builder()
                .username(input.getUsername())
                .email(input.getEmail())
                .password(passwordEncoder.encode(input.getPassword()))
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .role(input.getRole())
                .build();

        // 如果提供了组织ID，设置组织
        if (input.getOrganizationId() != null) {
            Organization organization = organizationRepository.findById(input.getOrganizationId())
                    .orElseThrow(() -> new ResourceNotFoundException("Organization not found with id: " + input.getOrganizationId()));
            user.setOrganization(organization);
        }

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User updateUser(Long id, UserUpdateInput input) {
        User user = getUserById(id);

        // 更新用户名(如果提供且不同)
        if (input.getUsername() != null && !input.getUsername().equals(user.getUsername())) {
            if (userRepository.existsByUsername(input.getUsername())) {
                throw new IllegalArgumentException("Username already exists: " + input.getUsername());
            }
            user.setUsername(input.getUsername());
        }

        // 更新邮箱(如果提供且不同)
        if (input.getEmail() != null && !input.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(input.getEmail())) {
                throw new IllegalArgumentException("Email already exists: " + input.getEmail());
            }
            user.setEmail(input.getEmail());
        }

        // 更新其他字段
        if (input.getFirstName() != null) {
            user.setFirstName(input.getFirstName());
        }

        if (input.getLastName() != null) {
            user.setLastName(input.getLastName());
        }

        if (input.getRole() != null) {
            user.setRole(input.getRole());
        }

        // 更新组织关系
        if (input.getOrganizationId() != null) {
            Organization organization = organizationRepository.findById(input.getOrganizationId())
                    .orElseThrow(() -> new ResourceNotFoundException("Organization not found with id: " + input.getOrganizationId()));
            user.setOrganization(organization);
        }

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public boolean deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }

        userRepository.deleteById(id);
        return true;
    }

    @Override
    @Transactional
    public User assignUserToOrganization(Long userId, Long organizationId) {
        User user = getUserById(userId);
        Organization organization = organizationRepository.findById(organizationId)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found with id: " + organizationId));

        user.setOrganization(organization);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User removeUserFromOrganization(Long userId) {
        User user = getUserById(userId);
        user.setOrganization(null);
        return userRepository.save(user);
    }

    // 构建分页结果
    private UserPage buildUserPage(Page<User> page) {
        PageInfo pageInfo = PageInfo.builder()
                .totalElements((int) page.getTotalElements())
                .totalPages(page.getTotalPages())
                .currentPage(page.getNumber())
                .pageSize(page.getSize())
                .build();

        return UserPage.builder()
                .content(page.getContent())
                .pageInfo(pageInfo)
                .build();
    }
}
