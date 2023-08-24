package net.guides.springboot.registrationlogindemo.service;

import net.guides.springboot.registrationlogindemo.dto.UserDto;
import net.guides.springboot.registrationlogindemo.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;
public interface UserService {
    void saveUser(UserDto userDto);
    void saveUser(User user);

    User findByEmail(String email);

    public UserDto convertEntityToDto(User user);
    List<UserDto> findAllUsers();


    void deleteUserByEmail(String email);
}
