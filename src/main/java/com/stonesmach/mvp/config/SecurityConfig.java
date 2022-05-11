package com.stonesmach.mvp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/auth/**").authenticated()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/readAnything").hasAuthority("READ_ANYTHING")
//                .antMatchers("/profile/**").hasAuthority("Profiler")
                .and()
                .formLogin()
                .and()
                .logout().logoutSuccessUrl("/hello");

    }
//    @Bean
//    public UserDetailsService users(){
//        UserDetails user = User.builder()
//                .username("user")
//                .password("{bcrypt}$2a$12$54IPdYIcsPz8sxBCvS2u3.lDD/ZLsTtid9Av3xhUVxfr7XzpCORci")
//                .roles("USER")
//                .build();
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password("{bcrypt}$2a$12$54IPdYIcsPz8sxBCvS2u3.lDD/ZLsTtid9Av3xhUVxfr7XzpCORci")
//                .roles("USER","ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(user,admin);
//    }
    @Bean
    public JdbcUserDetailsManager users(DataSource dataSource){
        UserDetails user = User.builder()
                .username("user")
                .password("{bcrypt}$2a$12$54IPdYIcsPz8sxBCvS2u3.lDD/ZLsTtid9Av3xhUVxfr7XzpCORci")
                .roles("USER")
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password("{bcrypt}$2a$12$54IPdYIcsPz8sxBCvS2u3.lDD/ZLsTtid9Av3xhUVxfr7XzpCORci")
                .authorities("READ_ANYTHING")
                .roles("USER","ADMIN")
                .build();
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        if(jdbcUserDetailsManager.userExists(user.getUsername())){
            jdbcUserDetailsManager.deleteUser(user.getUsername());
        }
        jdbcUserDetailsManager.createUser(user);
        if(jdbcUserDetailsManager.userExists(admin.getUsername())){
            jdbcUserDetailsManager.deleteUser(admin.getUsername());
        }
        jdbcUserDetailsManager.createUser(admin);
        return jdbcUserDetailsManager;
    }
}
