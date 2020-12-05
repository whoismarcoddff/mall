package com.example.backend.domain.mapper;

import com.example.backend.domain.dto.UserRegisterRequest;
import com.example.backend.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class UserEditMapper {

    @Mapping(target = "authorities", ignore = true)
    public abstract User requestToUser(UserRegisterRequest request);
}
