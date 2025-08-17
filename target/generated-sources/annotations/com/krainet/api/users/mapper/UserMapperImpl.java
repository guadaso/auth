package com.krainet.api.users.mapper;

import com.krainet.api.users.dto.UserDto;
import com.krainet.api.users.entity.Users;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-17T13:48:03+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Amazon.com Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toDto(Users user) {
        if ( user == null ) {
            return null;
        }

        UserDto.UserDtoBuilder userDto = UserDto.builder();

        userDto.id( user.getId() );
        userDto.userName( user.getUserName() );
        userDto.password( user.getPassword() );
        userDto.email( user.getEmail() );
        userDto.firstName( user.getFirstName() );
        userDto.lastName( user.getLastName() );
        userDto.role( user.getRole() );

        return userDto.build();
    }

    @Override
    public Users toEntity(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        Users.UsersBuilder<?, ?> users = Users.builder();

        users.id( userDto.getId() );
        users.userName( userDto.getUserName() );
        users.password( userDto.getPassword() );
        users.email( userDto.getEmail() );
        users.firstName( userDto.getFirstName() );
        users.lastName( userDto.getLastName() );
        users.role( userDto.getRole() );

        return users.build();
    }

    @Override
    public void updateEntity(UserDto userDto, Users user) {
        if ( userDto == null ) {
            return;
        }

        if ( userDto.getId() != null ) {
            user.setId( userDto.getId() );
        }
        if ( userDto.getUserName() != null ) {
            user.setUserName( userDto.getUserName() );
        }
        if ( userDto.getPassword() != null ) {
            user.setPassword( userDto.getPassword() );
        }
        if ( userDto.getEmail() != null ) {
            user.setEmail( userDto.getEmail() );
        }
        if ( userDto.getFirstName() != null ) {
            user.setFirstName( userDto.getFirstName() );
        }
        if ( userDto.getLastName() != null ) {
            user.setLastName( userDto.getLastName() );
        }
        if ( userDto.getRole() != null ) {
            user.setRole( userDto.getRole() );
        }
    }
}
