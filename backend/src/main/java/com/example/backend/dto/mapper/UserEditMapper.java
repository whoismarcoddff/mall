package com.example.backend.dto.mapper;

import com.example.backend.dto.request.UserRegisterRequest;
import com.example.backend.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class UserEditMapper {

    public abstract User requestToUser(UserRegisterRequest request);
}
