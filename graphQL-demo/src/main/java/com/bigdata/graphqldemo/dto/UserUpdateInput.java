package com.bigdata.graphqldemo.dto;


import com.bigdata.graphqldemo.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateInput {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private User.Role role;
    private Long organizationId;
}