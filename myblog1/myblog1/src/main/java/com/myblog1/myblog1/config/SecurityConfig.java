package com.myblog1.myblog1.config;
//
//import com.myblog1.myblog1.security.CustomUserDetailsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class SecurityConfig{
//
//
//
////    @Override
////    protected void configure(HttpSecurity http) throws Exception {
////
////        // Enabling Basic Authentication.
////        http
////                .csrf().disable()
////                .authorizeRequests()
////                .anyRequest()
////                .authenticated()
////                .and()
////                .httpBasic();
////    }
//
//
//
//      //Password encoder(parent reference variable) interface has a non-static method called as encoder interface. BCryptPasswordEncoder(it's object)it's a class, object of passwordEncoder interface.
//     // 1st step
//    //BCryptPasswordEncoder this object is required , because it has method encode , which can encode my password, without encoding in spring security password are not entered.
//
//
//    @Autowired
//    private CustomUserDetailsService userDetailsService;
//
//
//   @Bean
//   PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//
//    //
//
//
//    @Bean
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//
//
//    //2nd step
//    // antMatchers():- help us to specifie, which url who can access.
//    // permitAll():- any user can access this url.
//
////    @Override
////    @Bean
////    protected void configure(HttpSecurity http) throws Exception {
////
////        http
////                .csrf().disable()
////                .authorizeHttpRequests()
////                .antMatchers(HttpMethod.GET, "/api/**").permitAll()
////                .anyRequest()
////                .authenticated()
////                .and()
////                .httpBasic();
////
////    }
//
//
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        // IN-MEMORY-Authentication
//        //hcdr4ah
//        http
//                .csrf().disable()
//                .authorizeHttpRequests()
//                .antMatchers(HttpMethod.GET, "/api/**").permitAll()
//                .antMatchers("/api/auth/**").permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .httpBasic();
//        return http.build();
//    }
//
//
//
//    // 3rd step:- Right click-> generate-> override method-> userDetailsService()-> Ok
//    //im doing  IN-MEMORY-Authentication, which is temporally store my username and password along with the roles
//    //In Order to store your User name and password in InMemory u have to write this much code.
//
////    @Bean
////    protected UserDetailsService userDetailsService() {
////
////       // both user can access GET , but not POST. via antMatchers()
////        UserDetails userWasim = User.builder().username("wasim").password(passwordEncoder().encode("password")).roles("USER").build();
////        UserDetails userAdmin = User.builder().username("admin").password(passwordEncoder().encode("admin")).roles("ADMIN").build();
////        return new InMemoryUserDetailsManager(userWasim,userAdmin);
////    }
//
//    // 4 th step should have controller layer to add @PreAuthorize(), so go to for controller layer.
//
//
//    //
//
//
//
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService)
//                .passwordEncoder(passwordEncoder());
//    }
//
//}


import com.myblog1.myblog1.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/api/**").permitAll()
                .antMatchers("/api/auth/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
}
