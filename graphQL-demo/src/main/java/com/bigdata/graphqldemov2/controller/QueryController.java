package com.bigdata.graphqldemov2.controller;

import com.bigdata.graphqldemov2.model.*;
import com.bigdata.graphqldemov2.service.OrganizationService;
import com.bigdata.graphqldemov2.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class QueryController {
    private final UserService userService;
    private final OrganizationService organizationService;

    @QueryMapping
    public List<User> users() {
        return userService.getUsers();
    }

    @QueryMapping
    public List<Organization> organizations() {
        return organizationService.getOrganizations();
    }

    @QueryMapping
    public Organization organization(@Argument Long id) {
        return organizationService.getOrganizationById(id);
    }

    @QueryMapping
    public List<User> organizationUsers(@Argument Long orgId) {
        return userService.getUsersByOrganization(orgId);
    }
}