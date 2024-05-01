package com.zero.springweb.service;

import com.zero.springweb.repository.UserRepository;
import com.zero.springweb.utils.UserPrincipal;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService  implements UserDetailsService {
    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = repository.getFirstByPhone(username).orElseThrow();


        List<SimpleGrantedAuthority> roles = Arrays.stream(user.getRole().split(",")).map(SimpleGrantedAuthority::new).toList();
        return new UserPrincipal()
                .setUserId(user.getId())
                .setPhone(user.getPhone())
                .setPassword(user.getPassword())
                .setAuthorities(roles);
    }
}
