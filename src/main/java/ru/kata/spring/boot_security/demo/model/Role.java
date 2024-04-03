package ru.kata.spring.boot_security.demo.model;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Data
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "roles")
    //private Set<User> users;
    private Set<User> users=new HashSet<>();
    public Role(String name) {
        this.name = name;
    }

    public Role() {
    }

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Role(Long id, String name, Set<User> users) {
        this.id = id;
        this.name = name;
        this.users = users;
    }

    @Override
    public String getAuthority() {
          return name;
        //return getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id) && Objects.equals(name, role.name) && Objects.equals(users, role.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, users);
    }
}

//    @Column(name = "username")
//    private String username;
//    @Column(name = "password")
//    private String password;
//    @Column(name = "roles")
//    private String roles;

//    public Role (){
//
//    }
//
//    public Role(Long id, String name) {
//        this.id = id;
//        this.name = name;
//    }
//
//    public Role (Long id){
//this.id = id;
//    }

    //    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Long getId() {
//        return id;
//    }

