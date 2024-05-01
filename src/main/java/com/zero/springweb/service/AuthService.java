package com.zero.springweb.service;

import com.zero.springweb.entities.User;
import com.zero.springweb.model.Credentials;
import com.zero.springweb.model.HttpStatusCodes;
import com.zero.springweb.model.Registration;
import com.zero.springweb.plugins.errorModule.ErrorModule;
import com.zero.springweb.plugins.errorModule.model.Code;
import com.zero.springweb.plugins.jwtModule.JWTModule;
import com.zero.springweb.plugins.jwtModule.model.RefreshToken;
import com.zero.springweb.repository.UserRepository;
import com.zero.springweb.utils.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JWTModule jwtService;
    private final UserRepository repository;
    private final PasswordEncoder crypto;
    private final ErrorModule errorModule;
    private final AuthenticationManager authenticationManager;

    /**
     * Passes username and passwordCrypto to the authenticate method which checks credentials and if they are correct creates a session and refresh JWT token
     *
     * @param email
     * @param password
     * @return RefreshToken
     * @throws BadCredentialsException
     */
    public RefreshToken attemptLogin(String email, String password) throws BadCredentialsException {
        Authentication authentication = null;

        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
        } catch (AuthenticationException e) {
            errorModule.builder().addError("Не корректні дані для входу!", Code.REQUEST_VALIDATION_ERROR).throwErrorIfPresent(HttpStatusCodes.NOT_ACCEPTABLE.getValue());
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        var principal = (UserPrincipal) authentication.getPrincipal();


        return jwtService.issueRefresh(Credentials.builder()
                .userId(principal.getUserId())
                .phone(principal.getPhone())
                .roles(principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .build()
        );
    }

    public void attemptRegistration(Registration registration) {
        var user = repository.getFirstByPhone(registration.getPhone());


        if (user.isPresent()) {
            var errors = errorModule.builder().addError("Користувач з такою електронною поштою або номером телефону вже інсує!", Code.NICKNAME_BUSY);
            errors.throwErrorIfPresent(HttpStatusCodes.NOT_ACCEPTABLE.getValue());
        }

        var newUser = new User();
        newUser.setFullName(registration.getFullName());
        newUser.setPhone(registration.getPhone());
        newUser.setEmail(registration.getEmail());
        newUser.setDocumentNumber(registration.getDocumentNumber());
        newUser.setPassword(crypto.encode(registration.getPassword()));

        repository.save(newUser);
    }

    public boolean checkPhoneNumber(String phoneNumber) {
        var user = repository.getFirstByPhone(phoneNumber);
        System.out.println("Phone: " + phoneNumber + ", founded: " + user.isEmpty());

        return user.isEmpty();
    }

    public boolean checkUsernameAvailability(String username) {
        var user = repository.getFirstByPhone(username);
        System.out.println("Username: " + username + ", founded: " + user.isEmpty());

        return user.isEmpty();
    }
}
