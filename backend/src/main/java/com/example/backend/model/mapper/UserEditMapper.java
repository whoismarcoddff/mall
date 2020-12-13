package com.example.backend.model.mapper;

import com.example.backend.model.dto.UserRegisterRequest;
import com.example.backend.model.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class UserEditMapper {

    public abstract User requestToUser(UserRegisterRequest request);
}
