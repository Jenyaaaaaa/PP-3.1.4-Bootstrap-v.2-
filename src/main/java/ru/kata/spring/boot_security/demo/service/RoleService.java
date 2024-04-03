package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;

import java.util.List;
@Service
public interface RoleService {

//    public  void deleteUserRolesByUserId(Long id);
//    }

//    public List<Role> listRoles();

    public List<Role> getAllRoles();

    public Role getRoleById(Long id);

    public Role createRole(Role role);

    public Role updateRole(Long id, Role roleDetails);

    public List<Role> findRolesByIds(List<Long> roleIds);
    public void deleteRole(Long id);
}
