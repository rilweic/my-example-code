package com.bigdata.graphqldemo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationCreateInput {
    private String name;
    private String description;
    private String address;
    private String phoneNumber;
    private String email;
}