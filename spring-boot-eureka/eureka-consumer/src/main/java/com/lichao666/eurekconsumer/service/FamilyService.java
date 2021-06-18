package com.lichao666.eurekconsumer.service;

import com.lichao666.eurekconsumer.entity.Family;

public interface FamilyService {
    Family getFamilyById(Integer id);

    Family getFamilyByLoadBalance();

    Family getByLoadBalanceAnnotation();
}
