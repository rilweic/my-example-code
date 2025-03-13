package com.bigdata.graphqldemo.dto;

import com.bigdata.graphqldemo.model.Organization;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationPage {
    private List<Organization> content;
    private PageInfo pageInfo;
}