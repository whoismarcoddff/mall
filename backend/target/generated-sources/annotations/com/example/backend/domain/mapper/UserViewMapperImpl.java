package com.example.backend.domain.mapper;

import com.example.backend.domain.dto.UserView;
import com.example.backend.domain.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-12-04T22:33:34+0800",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_221 (Oracle Corporation)"
)
@Component
public class UserViewMapperImpl extends UserViewMapper {

    @Override
    public UserView userToUserView(User user) {
        if ( user == null ) {
            return null;
        }

        UserView userView = new UserView();

        userView.setUserId( user.getId() );
        userView.setUsername( user.getUsername() );
        userView.setEmail( user.getEmail() );

        userView.setFullname( user.getFirstname() + new String(" ")+ user.getLastname() );

        return userView;
    }

    @Override
    public List<UserView> userToUserView(List<User> user) {
        if ( user == null ) {
            return null;
        }

        List<UserView> list = new ArrayList<UserView>( user.size() );
        for ( User user1 : user ) {
            list.add( userToUserView( user1 ) );
        }

        return list;
    }
}
