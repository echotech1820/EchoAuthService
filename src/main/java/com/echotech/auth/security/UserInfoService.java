package com.echotech.auth.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.echotech.auth.model.AppUser;
import com.echotech.auth.repository.AppUserRepository;

@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private AppUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String mobile) {

        AppUser user = repository.findByUserPhoneNumber(mobile)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        return new User(
                user.getUserPhoneNumber(),
                user.getUserPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}
