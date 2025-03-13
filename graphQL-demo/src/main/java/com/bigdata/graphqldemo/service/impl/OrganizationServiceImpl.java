package com.bigdata.graphqldemo.service.impl;

import com.bigdata.graphqldemo.dto.OrganizationCreateInput;
import com.bigdata.graphqldemo.dto.OrganizationPage;
import com.bigdata.graphqldemo.dto.OrganizationUpdateInput;
import com.bigdata.graphqldemo.dto.PageInfo;
import com.bigdata.graphqldemo.exception.ResourceNotFoundException;
import com.bigdata.graphqldemo.model.Organization;
import com.bigdata.graphqldemo.repository.OrganizationRepository;
import com.bigdata.graphqldemo.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;

    @Override
    @Transactional(readOnly = true)
    public Organization getOrganizationById(Long id) {
        return organizationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Organization not found with id: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public OrganizationPage getAllOrganizations(Pageable pageable) {
        Page<Organization> organizationPage = organizationRepository.findAll(pageable);
        return buildOrganizationPage(organizationPage);
    }

    @Override
    @Transactional(readOnly = true)
    public OrganizationPage searchOrganizations(String keyword, Pageable pageable) {
        Page<Organization> organizationPage = organizationRepository.searchOrganizations(keyword, pageable);
        return buildOrganizationPage(organizationPage);
    }

    @Override
    @Transactional
    public Organization createOrganization(OrganizationCreateInput input) {
        Organization organization = Organization.builder()
                .name(input.getName())
                .description(input.getDescription())
                .address(input.getAddress())
                .phoneNumber(input.getPhoneNumber())
                .email(input.getEmail())
                .build();

        return organizationRepository.save(organization);
    }

    @Override
    @Transactional
    public Organization updateOrganization(Long id, OrganizationUpdateInput input) {
        Organization organization = getOrganizationById(id);

        if (input.getName() != null) {
            organization.setName(input.getName());
        }

        if (input.getDescription() != null) {
            organization.setDescription(input.getDescription());
        }

        if (input.getAddress() != null) {
            organization.setAddress(input.getAddress());
        }

        if (input.getPhoneNumber() != null) {
            organization.setPhoneNumber(input.getPhoneNumber());
        }

        if (input.getEmail() != null) {
            organization.setEmail(input.getEmail());
        }

        return organizationRepository.save(organization);
    }

    @Override
    @Transactional
    public boolean deleteOrganization(Long id) {
        if (!organizationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Organization not found with id: " + id);
        }

        organizationRepository.deleteById(id);
        return true;
    }

    // 构建分页结果
    private OrganizationPage buildOrganizationPage(Page<Organization> page) {
        PageInfo pageInfo = PageInfo.builder()
                .totalElements((int) page.getTotalElements())
                .totalPages(page.getTotalPages())
                .currentPage(page.getNumber())
                .pageSize(page.getSize())
                .build();

        return OrganizationPage.builder()
                .content(page.getContent())
                .pageInfo(pageInfo)
                .build();
    }
}
