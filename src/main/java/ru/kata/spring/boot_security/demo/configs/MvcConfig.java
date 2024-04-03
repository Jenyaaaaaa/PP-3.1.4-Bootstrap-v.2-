package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        //registry.addViewController("/login").setViewName("login");
        // Маршруты для страниц UserController
        registry.addViewController("/user").setViewName("user-home");
       // registry.addViewController("/logout").setViewName("logout");
        registry.addViewController("/admin").setViewName("user-list");
        //registry.addViewController("/admin/users").setViewName("user-list");
        //registry.addViewController("/admin/user-create").setViewName("user-create");
        //registry.addViewController("/admin/user-update/{id}").setViewName("user-update");

        // Маршруты для страниц RoleController
     //   registry.addViewController("/api/roles").setViewName("api/roles");
       // registry.addViewController("/api/roles/{id}").setViewName("api/role-details");
    }
}

