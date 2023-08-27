package net.guides.springboot.registrationlogindemo.service.impl;

import net.guides.springboot.registrationlogindemo.entity.Role;
import net.guides.springboot.registrationlogindemo.entity.UserEntity;
import net.guides.springboot.registrationlogindemo.repository.UserRepository;
import net.guides.springboot.registrationlogindemo.repository.RoleRepository;
import net.guides.springboot.registrationlogindemo.dto.UserDto;
import net.guides.springboot.registrationlogindemo.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
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
        UserEntity userEntity = new UserEntity();
        userEntity.setName(userDto.getFirstName() + " " + userDto.getLastName());
        userEntity.setEmail(userDto.getEmail());

        //encrypt the password once we integrate spring security
        //user.setPassword(userDto.getPassword());
        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        if(userDto.isRole())
        {
            Role role1 = roleRepository.findByName("AGENCY");
            if(role1 == null){
                role1 = checkRoleExistA();
            }
            userEntity.setRoles(Arrays.asList(role1));
            userRepository.save(userEntity);
        }
        else
        {
            Role role2 = roleRepository.findByName("USER");
            if(role2 == null){
                role2 = checkRoleExistU();
            }
            userEntity.setRoles(Arrays.asList(role2));
            userRepository.save(userEntity);
        }
    }
    @Override
    public void saveUser(UserEntity userEntity) {
       this.userRepository.save(userEntity);
    }


    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<UserEntity> userEntities = (List<UserEntity>) userRepository.findAll();
        return userEntities.stream().map((user) -> convertEntityToDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUserByEmail(String email) {
        UserEntity userEntity = findByEmail(email);
        this.userRepository.deleteById(userEntity.getId());
    }


    public UserDto convertEntityToDto(UserEntity userEntity){
        UserDto userDto = new UserDto();
        String[] name = userEntity.getName().split(" ");
        userDto.setFirstName(name[0]);
        userDto.setLastName(name[1]);
        userDto.setEmail(userEntity.getEmail());
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
