package com.example.backend.domain.mapper;

import com.example.backend.domain.dto.UserRegisterRequest;
import com.example.backend.domain.entity.User;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-12-04T22:33:34+0800",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_221 (Oracle Corporation)"
)
@Component
public class UserEditMapperImpl extends UserEditMapper {

    @Override
    public User requestToUser(UserRegisterRequest request) {
        if ( request == null ) {
            return null;
        }

        User user = new User();

        user.setEmail( request.getEmail() );
        user.setUsername( request.getUsername() );
        user.setFirstname( request.getFirstname() );
        user.setLastname( request.getLastname() );
        user.setPassword( request.getPassword() );
        user.setRole( request.getRole() );

        return user;
    }
}
