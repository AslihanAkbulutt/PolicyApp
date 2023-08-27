package net.guides.springboot.registrationlogindemo.controller;

import net.guides.springboot.registrationlogindemo.entity.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.ui.Model;
import net.guides.springboot.registrationlogindemo.dto.UserDto;
import net.guides.springboot.registrationlogindemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {
    private UserService  userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity = userService.findByEmail(((User)authentication.getPrincipal()).getUsername());
        if (userEntity.getRoles().get(0).getName().equals("USER"))
        {
            return "index";
        }
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users",users);
        return "users";
    }

    @GetMapping("users/add")
    public String showNewUserForm(Model model) {
        UserEntity userEntity = new UserEntity();
        model.addAttribute("user", userEntity);
        return "create-user";
    }

    @PostMapping("users/save")
    public String saveUser(@ModelAttribute("user") UserEntity userEntity) {
        userService.saveUser(userEntity);
        return "redirect:/users";
    }

    @GetMapping("users/update/{email}")
    public String showFormForUpdate(@PathVariable( value = "email") String email, Model model) {

        UserEntity userEntity = userService.findByEmail(email);
        model.addAttribute("user", userEntity);
        return "update-user";
    }

    @GetMapping("users/delete/{email}")
    public String deleteUser(@PathVariable (value = "email") String email) {

        this.userService.deleteUserByEmail(email);
        return "redirect:/users";
    }


}
