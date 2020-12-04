package com.example.admin.domain.mapper;

import com.example.admin.domain.dto.UserRegisterRequest;
import com.example.admin.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class UserEditMapper {

    @Mapping(target = "authorities", ignore = true)
    public abstract User requestToUser(UserRegisterRequest request);
}
