package net.guides.springboot.registrationlogindemo.service.impl;

import net.guides.springboot.registrationlogindemo.entity.Role;
import net.guides.springboot.registrationlogindemo.entity.User;
import net.guides.springboot.registrationlogindemo.repository.UserRepository;
import net.guides.springboot.registrationlogindemo.repository.RoleRepository;
import net.guides.springboot.registrationlogindemo.dto.UserDto;
import net.guides.springboot.registrationlogindemo.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getFirstName() + " " + userDto.getLastName());
        user.setEmail(userDto.getEmail());

        //encrypt the password once we integrate spring security
        //user.setPassword(userDto.getPassword());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        if(userDto.isRole())
        {
            Role role1 = roleRepository.findByName("AGENCY");
            if(role1 == null){
                role1 = checkRoleExistA();
            }
            user.setRoles(Arrays.asList(role1));
            userRepository.save(user);
        }
        else
        {
            Role role2 = roleRepository.findByName("USER");
            if(role2 == null){
                role2 = checkRoleExistU();
            }
            user.setRoles(Arrays.asList(role2));
            userRepository.save(user);
        }
    }
    @Override
    public void saveUser(User user) {
       this.userRepository.save(user);
    }


    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = (List<User>) userRepository.findAll();
        return users.stream().map((user) -> convertEntityToDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUserByEmail(String email) {
        User user = findByEmail(email);
        this.userRepository.deleteById(user.getId());
    }


    public UserDto convertEntityToDto(User user){
        UserDto userDto = new UserDto();
        String[] name = user.getName().split(" ");
        userDto.setFirstName(name[0]);
        userDto.setLastName(name[1]);
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    private Role checkRoleExistA() {
        Role role = new Role();
        role.setName("AGENCY");
        return roleRepository.save(role);
    }
    private Role checkRoleExistU() {
        Role role = new Role();
        role.setName("USER");
        return roleRepository.save(role);
    }
}
