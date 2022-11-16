package com.example.isa.dto;

import java.util.List;

public class UserSearchDto {
    public List<String> parameters;
    public UserSearchDto(List<String> parameters){
        this.parameters = parameters;
    }
}
