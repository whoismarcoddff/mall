package com.example.admin.domain.mapper;

import com.example.admin.domain.dto.UserView;
import com.example.admin.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;



@Mapper(componentModel = "spring")
public abstract class UserViewMapper {

    @Mapping(target = "userId", source = "id")
    @Mapping(target = "fullname", expression = "java(user.getFirstname() + new String(\" \")+ user.getLastname())")
    public abstract UserView userToUserView(User user);
}
