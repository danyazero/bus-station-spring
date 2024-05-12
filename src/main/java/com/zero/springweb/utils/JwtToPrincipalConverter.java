package com.zero.springweb.utils;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.zero.springweb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtToPrincipalConverter {
    private final UserRepository userRepository;
    static final Logger logger = LoggerFactory.getLogger(JwtToPrincipalConverter.class);

    public UserPrincipal convert(DecodedJWT jwt) {
        System.out.println("JWT Subject: "+jwt.getSubject());
        var user = userRepository.findUserByIdEquals(Integer.parseInt(jwt.getSubject()));
        if (user.isEmpty()) return null;
        logger.info(user.get().getId() + " " + user.get().getPhone() + " " + user.get().getRole());
        var authorityList = Arrays.stream(user.get().getRole().split(", "))
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        for (SimpleGrantedAuthority grantedAuthority : authorityList) {
            System.out.println(grantedAuthority);
        }

        return new UserPrincipal()
                .setUserId( user.get().getId() )
                .setPhone( user.get().getPhone() )
                .setAuthorities( authorityList );
    }
}
