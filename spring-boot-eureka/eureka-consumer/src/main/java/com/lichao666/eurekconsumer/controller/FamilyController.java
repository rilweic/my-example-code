package com.lichao666.eurekconsumer.controller;

import com.lichao666.eurekconsumer.entity.Family;
import com.lichao666.eurekconsumer.service.FamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FamilyController {
    @Autowired
    private FamilyService familyService;

    @GetMapping("/family")
    public String getFamily() {

        return familyService.getFamilyById(1).toString();
    }

    @GetMapping("/family/loadbalance")
    public Family getFamilyByLoadBalance() {

        return familyService.getFamilyByLoadBalance();
    }

    @GetMapping("/family/loadbalanceannotation")
    public Family getFamilyByLoadBalanceAnnotation() {

        return familyService.getByLoadBalanceAnnotation();
    }
}
