package com.bigdata.graphqldemov2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.stereotype.Controller;
import com.bigdata.graphqldemov2.model.*;
import com.bigdata.graphqldemov2.service.*;
@Controller
@RequiredArgsConstructor
public class MutationController {
    private final UserService userService;
    private final OrganizationService organizationService;

    @MutationMapping
    public User createUser(@Argument String name, @Argument String email, @Argument Long orgId) {
        Organization org = organizationService.getOrganizations()
                .stream()
                .filter(o -> o.getId().equals(orgId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Org not found"));

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setOrganization(org);

        return userService.createUser(user);
    }

    @MutationMapping
    public Organization createOrganization(@Argument String name, @Argument String description) {
        Organization org = new Organization();
        org.setName(name);
        org.setDescription(description);
        return organizationService.createOrganization(org);
    }
}