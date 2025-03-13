package com.bigdata.graphqldemo.resolver;

import com.bigdata.graphqldemo.dto.UserCreateInput;
import com.bigdata.graphqldemo.dto.UserPage;
import com.bigdata.graphqldemo.dto.UserUpdateInput;
import com.bigdata.graphqldemo.model.Organization;
import com.bigdata.graphqldemo.model.User;
import com.bigdata.graphqldemo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class UserResolver {

    private final UserService userService;

    // 查询解析
    @QueryMapping
    public User getUserById(@Argument Long id) {
        return userService.getUserById(id);
    }

    @QueryMapping
    public UserPage getAllUsers(@Argument Integer page, @Argument Integer size) {
        return userService.getAllUsers(PageRequest.of(page, size));
    }

    @QueryMapping
    public UserPage searchUsers(@Argument String keyword, @Argument Integer page, @Argument Integer size) {
        return userService.searchUsers(keyword, PageRequest.of(page, size));
    }

    // 变更解析
    @MutationMapping
    public User createUser(@Argument UserCreateInput input) {
        return userService.createUser(input);
    }

    @MutationMapping
    public User updateUser(@Argument Long id, @Argument UserUpdateInput input) {
        return userService.updateUser(id, input);
    }

    @MutationMapping
    public Boolean deleteUser(@Argument Long id) {
        return userService.deleteUser(id);
    }

    @MutationMapping
    public User assignUserToOrganization(@Argument Long userId, @Argument Long organizationId) {
        return userService.assignUserToOrganization(userId, organizationId);
    }

    @MutationMapping
    public User removeUserFromOrganization(@Argument Long userId) {
        return userService.removeUserFromOrganization(userId);
    }

    // 字段解析
    @SchemaMapping(typeName = "User", field = "organization")
    public Organization getOrganization(User user) {
        return user.getOrganization();
    }

    // 日期格式化
    @SchemaMapping(typeName = "User", field = "createdAt")
    public String getCreatedAt(User user) {
        return user.getCreatedAt().toString();
    }

    @SchemaMapping(typeName = "User", field = "updatedAt")
    public String getUpdatedAt(User user) {
        if (user.getUpdatedAt() == null) {
            return null;
        }
        return user.getUpdatedAt().toString();
    }
}
