package ru.kata.spring.boot_security.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import org.apache.catalina.User;
//import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;
import ru.kata.spring.boot_security.demo.model.User;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.RowSet;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Controller
public class UserController {

    private final UserService userService;
    private final RoleService roleService;
    private Object Collection;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

//    private final AuthenticationManager authenticationManager;
//    @PostMapping("/login")
//    public ResponseEntity<Void> login(@RequestBody LoginRequest loginRequest) {
//        Object UsernamePasswordAuthenticationToken = null;
//        Authentication authenticationRequest =
//                UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.username(), loginRequest.password());
//        Authentication authenticationResponse =
//                this.authenticationManager.authenticate(authenticationRequest);
//        // ...
//    }

    public record LoginRequest(String username, String password) {
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login"; // возвращаем имя представления для страницы входа
    }


    @GetMapping("/user")
    public String getHome(Model model, Principal principal) {
        model.addAttribute("user", userService.findByUsername(principal.getName()));
        return "user-home";
    }


    // Logout с любой страницы

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/login?logout";
    }

    @GetMapping("/admin")
    public String findAll(Model model, Principal principal) {
        model.addAttribute("user", userService.findByUsername(principal.getName()));
        List<User> users = userService.findAll();
        List<Role> roles = roleService.getAllRoles();
        model.addAttribute("listRoles", roles); // Передаем список ролей в модель
        model.addAttribute("users", users);
        return "user-list";
    }

    @GetMapping("/admin/user-create")
    public String createUserForm(Model model) {
        List<Role> listRoles = roleService.getAllRoles();
        model.addAttribute("listRoles", listRoles);
        return "user-create";
    }

    @PostMapping("/admin")
    public String createUser(@RequestParam(name = "listRoles") List<Long> listRoles, User user) {
        List<Role> roles = roleService.findRolesByIds(listRoles);
        user.setRoles(roles);
        userService.saveUser(user);
        return "redirect:/admin";
    }
//    @PatchMapping("/admin/edit/{id}")
//    public String editUser(@ModelAttribute("user") User user, @RequestParam(name = "listRoles") List<Long> listRoles) {
//        List<Role> roles = roleService.findRolesByIds(listRoles);
//        userService.editUser(user, roles);
//        return "redirect:/admin";
//    }



    @GetMapping("admin/user-update/{id}")
    public String updateUserForm(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "user-update";
    }

    @PostMapping("/admin/edit/{id}")
    public String updateUser(@PathVariable Long id, @RequestParam("listRoles") List<Long> listRoleIds, User user) {
        User existingUser = userService.findById(id);

        if (existingUser != null) {
            // Обновляем поля пользователя, кроме ролей
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setUsername(user.getUsername());
            // Обновляем роли пользователя
            List<Role> roles = roleService.findRolesByIds(listRoleIds);
            existingUser.setRoles(roles);

            // Обновляем пользователя в базе данных
            userService.saveUser(existingUser);

            return "redirect:/admin"; // Перенаправление на страницу деталей пользователя
        } else {
            return "redirect:/admin"; // Перенаправление на страницу списка пользователей
        }
    }
    @DeleteMapping("/admin/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return "redirect:/admin";
    }
}


//    @PostMapping("/admin/edit/{id}")
//    public String updateUser(@PathVariable Long id, @ModelAttribute User user, @RequestParam("listRoles") List<Role> listRoles) {
//        User existingUser = userService.findById(id);
//
//        if (existingUser != null) {
//            existingUser.setFirstName(user.getFirstName());
//            existingUser.setLastName(user.getLastName());
//            existingUser.setUsername(user.getUsername());
//            existingUser.setRoles(listRoles);
//
//            // Обновляем пользователя в базе данных
//            userService.saveUser(existingUser);
//
//            return "redirect:/admin"; // Перенаправление на страницу деталей пользователя
//        } else {
//            return "redirect:/admin"; // Перенаправление на страницу списка пользователей
//        }
//    }
//
//}
//    @PostMapping("/admin/user-update/{id}")
//    public String updateUser(User user) {
//        userService.saveUser(user);
//        return "redirect:/admin";
//    }

//    @PostMapping("/admin/edit/{id}")
//    public String updateUser(@PathVariable Long id, @ModelAttribute User updatedUser) {
//        User existingUser = userService.findById(id);
//        if (existingUser != null) {
//            existingUser.setFirstName(updatedUser.getFirstName());
//            existingUser.setLastName(updatedUser.getLastName());
//            existingUser.setUsername(updatedUser.getUsername());
//            existingUser.setRoles(updatedUser.getRoles());
//            // Обновление других полей пользователя при необходимости
//            userService.saveUser(existingUser);
//            return "redirect:/admin"; // Перенаправление на страницу деталей пользователя
//        } else {
//            return "redirect:/admin"; // Перенаправление на страницу списка пользователей
//        }
//
//    }
//}

//    @GetMapping("/login")
//    public String loginPage() {
//        return "login"; // возвращаем имя представления для страницы входа
//    }


//@GetMapping(value = "/user")
//public String getHome(@AuthenticationPrincipal User activeUser, Model model) {
//    model.addAttribute("roles", activeUser.getRoles());
//    return "user-home";
//}

//        @GetMapping("users")
//        public String finlAll(Model model){
//            List<User> users = userService.findAll();
//            model.addAttribute("users", users);
//            return "user-list";
//        }
//        @GetMapping("/user-create")
//        public String creatUserForm(User user){
//            return "user-create";
//        }
//        @PostMapping("/user-create")
//        public String createUser(User user){
//            userService.saveUser(user);
//            return "redirect:/users";
//        }
//        @GetMapping("user-delete/{id}")
//        public String deleteUser(@PathVariable("id") Long id){
//            userService.deleteById(id);
//            return "redirect:/users";
//        }
//        @GetMapping("/user-update/{id}")
//        public String updateUserForm(@PathVariable("id") Long id, Model model){
//            User user = userService.findById(id);
//            model.addAttribute("user", user);
//            return "user-update";
//        }
//
//        @PostMapping("/user-update")
//        public String updateUser(User user) {
//            userService.saveUser(user);
//            return "redirect:/users";
//        }

