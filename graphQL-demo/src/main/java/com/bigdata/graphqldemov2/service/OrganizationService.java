package com.bigdata.graphqldemov2.service;

import com.bigdata.graphqldemov2.model.Organization;
import com.bigdata.graphqldemov2.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrganizationService {
    private final OrganizationRepository organizationRepository;

    public List<Organization> getOrganizations() {
        return organizationRepository.findAll();
    }

    public Organization createOrganization(Organization org) {
        return organizationRepository.save(org);
    }

    public Organization getOrganizationById(Long id){
        return organizationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Organization not found"));
    }
}
