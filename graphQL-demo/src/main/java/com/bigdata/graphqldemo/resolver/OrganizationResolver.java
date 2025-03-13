package com.bigdata.graphqldemo.resolver;

import com.bigdata.graphqldemo.dto.OrganizationCreateInput;
import com.bigdata.graphqldemo.dto.OrganizationPage;
import com.bigdata.graphqldemo.dto.OrganizationUpdateInput;
import com.bigdata.graphqldemo.model.Organization;
import com.bigdata.graphqldemo.model.User;
import com.bigdata.graphqldemo.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrganizationResolver {

    private final OrganizationService organizationService;

    // 查询解析
    @QueryMapping
    public Organization getOrganizationById(@Argument Long id) {
        return organizationService.getOrganizationById(id);
    }

    @QueryMapping
    public OrganizationPage getAllOrganizations(@Argument Integer page, @Argument Integer size) {
        return organizationService.getAllOrganizations(PageRequest.of(page, size));
    }

    @QueryMapping
    public OrganizationPage searchOrganizations(@Argument String keyword, @Argument Integer page, @Argument Integer size) {
        return organizationService.searchOrganizations(keyword, PageRequest.of(page, size));
    }

    // 变更解析
    @MutationMapping
    public Organization createOrganization(@Argument OrganizationCreateInput input) {
        return organizationService.createOrganization(input);
    }

    @MutationMapping
    public Organization updateOrganization(@Argument Long id, @Argument OrganizationUpdateInput input) {
        return organizationService.updateOrganization(id, input);
    }

    @MutationMapping
    public Boolean deleteOrganization(@Argument Long id) {
        return organizationService.deleteOrganization(id);
    }

    // 字段解析
    @SchemaMapping(typeName = "Organization", field = "users")
    public List<User> getUsers(Organization organization) {
        return organization.getUsers();
    }

    // 日期格式化
    @SchemaMapping(typeName = "Organization", field = "createdAt")
    public String getCreatedAt(Organization organization) {
        return organization.getCreatedAt().toString();
    }

    @SchemaMapping(typeName = "Organization", field = "updatedAt")
    public String getUpdatedAt(Organization organization) {
        if (organization.getUpdatedAt() == null) {
            return null;
        }
        return organization.getUpdatedAt().toString();
    }
}
