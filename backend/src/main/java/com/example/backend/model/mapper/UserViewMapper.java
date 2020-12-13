package com.example.backend.model.mapper;

import com.example.backend.model.entity.User;
import com.example.backend.model.view.UserView;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class UserViewMapper {

    @Mapping(target = "roles", source = "user", qualifiedByName = "rolesCustomize")
    @Mapping(target = "userId", source = "id")
    public abstract UserView userToUserView(User user);

    public abstract List<UserView> userToUserView(List<User> user);

    @Named("rolesCustomize")
    public List<String> rolesCustomize (User user) {
        return user.getRoles().stream().map(SimpleGrantedAuthority::getAuthority).collect(Collectors.toList());
    }
}
