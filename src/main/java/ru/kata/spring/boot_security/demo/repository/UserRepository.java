package ru.kata.spring.boot_security.demo.repository;


import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

//    @Query("SELECT u FROM User u WHERE u.username = :username")
    @Query("select u from User u left join fetch u.roles where u.username=:username")
    UserDetails findByUsername(String username);
    // List<User> findByLastName(String lastName);
//   @Query(value = "SELECT nextval(pg_get_serial_sequence('t_user', 'id'))", nativeQuery = true)
//   Long getNextId();
//   User findUserByUserName(String userName);

    

}
