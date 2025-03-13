package com.bigdata.graphqldemo.dto;

import com.bigdata.graphqldemo.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPage {
    private List<User> content;
    private PageInfo pageInfo;
}