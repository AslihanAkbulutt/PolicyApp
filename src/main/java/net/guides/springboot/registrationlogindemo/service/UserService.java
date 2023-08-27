package net.guides.springboot.registrationlogindemo.service;

import net.guides.springboot.registrationlogindemo.dto.UserDto;
import net.guides.springboot.registrationlogindemo.entity.UserEntity;

import java.util.List;
public interface UserService {
    void saveUser(UserDto userDto);
    void saveUser(UserEntity userEntity);

    UserEntity findByEmail(String email);

    public UserDto convertEntityToDto(UserEntity userEntity);
    List<UserDto> findAllUsers();


    void deleteUserByEmail(String email);
}
