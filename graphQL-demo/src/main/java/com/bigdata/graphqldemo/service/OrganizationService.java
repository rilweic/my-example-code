package com.bigdata.graphqldemo.service;

import com.bigdata.graphqldemo.dto.OrganizationCreateInput;
import com.bigdata.graphqldemo.dto.OrganizationPage;
import com.bigdata.graphqldemo.dto.OrganizationUpdateInput;
import com.bigdata.graphqldemo.model.Organization;
import org.springframework.data.domain.Pageable;

public interface OrganizationService {

    Organization getOrganizationById(Long id);

    OrganizationPage getAllOrganizations(Pageable pageable);

    OrganizationPage searchOrganizations(String keyword, Pageable pageable);

    Organization createOrganization(OrganizationCreateInput input);

    Organization updateOrganization(Long id, OrganizationUpdateInput input);

    boolean deleteOrganization(Long id);
}