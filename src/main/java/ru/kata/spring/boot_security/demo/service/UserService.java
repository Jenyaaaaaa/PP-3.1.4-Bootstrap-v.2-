package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Collection;
import java.util.List;

@Service
public interface UserService {


    User findById(Long id);
    List<User> findAll();
    void saveUser(User user);
    void deleteById(Long id);
    User findByUsername(String username);
    UserDetails loadUserByUsername(String username);

    User get(Long id);
    public void updateUser(User user);
    void editUser(User user, List<Role> roles);

   public Collection<? extends GrantedAuthority> roles(Collection<Role> roles);

}
