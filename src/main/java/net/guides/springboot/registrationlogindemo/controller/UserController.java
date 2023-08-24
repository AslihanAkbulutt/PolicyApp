package net.guides.springboot.registrationlogindemo.controller;

import jakarta.validation.Valid;
import net.guides.springboot.registrationlogindemo.entity.User;
import org.springframework.ui.Model;
import net.guides.springboot.registrationlogindemo.dto.UserDto;
import net.guides.springboot.registrationlogindemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users",users);
        return "users";
    }

    @GetMapping("users/add")
    public String showNewUserForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "create-user";
    }

    @PostMapping("users/save")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/users";
    }

    @GetMapping("users/update/{email}")
    public String showFormForUpdate(@PathVariable( value = "email") String email, Model model) {

        User user = userService.findByEmail(email);
        model.addAttribute("user", user);
        return "update-user";
    }

    @GetMapping("users/delete/{email}")
    public String deleteUser(@PathVariable (value = "email") String email) {

        this.userService.deleteUserByEmail(email);
        return "redirect:/users";
    }


}
