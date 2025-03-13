package com.bigdata.graphqldemov2.repository;

import com.bigdata.graphqldemov2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByOrganizationId(Long orgId);
}
