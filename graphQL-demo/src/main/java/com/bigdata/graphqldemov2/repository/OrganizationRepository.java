package com.bigdata.graphqldemov2.repository;

import com.bigdata.graphqldemov2.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {
}

