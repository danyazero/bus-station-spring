package com.zero.springweb.controllers;

import com.zero.springweb.model.HttpStatusCodes;
import com.zero.springweb.model.LoginRequest;
import com.zero.springweb.model.Registration;
import com.zero.springweb.plugins.errorModule.ErrorModule;
import com.zero.springweb.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "${CORS.FRONT_URL}", allowCredentials = "true")
@AllArgsConstructor
public class LoginController {
    private final AuthService authService;
    private final ErrorModule errorService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request, HttpServletResponse response) {
        System.out.println("IP-Address: " + request.getRemoteAddr());
        System.out.println(loginRequest.getPhone() + " " + loginRequest.getPassword());
        errorService.builder()
                .checkRegex(loginRequest.getPhone(), Pattern.compile("^\\d{12}$"), "Некорректний номер телефону.")
                .checkSize(loginRequest.getPassword(), 8, 32, "Некорректна довжина паролю.")
                .throwErrorIfPresent(HttpStatusCodes.NOT_ACCEPTABLE.getValue());

        var tokens = authService.attemptLogin(loginRequest.getPhone(), loginRequest.getPassword());

        var authCookie = new Cookie("AuthToken", tokens.getSessionToken());
        authCookie.setPath("/api");
        authCookie.setHttpOnly(true);
        authCookie.setMaxAge(60 * 60 * 24 * 20);

        response.addCookie(authCookie);
        if (loginRequest.getRemember()) {
            var refreshCookie = new Cookie("RefreshToken", tokens.getRefreshToken());
            refreshCookie.setPath("/api");
            refreshCookie.setHttpOnly(true);
            refreshCookie.setMaxAge(60 * 60 * 24 * 20);//20 Days
            response.addCookie(refreshCookie);
        }
        response.setStatus(200);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<?> registration(@RequestBody Registration registrationDto) {
        var errors = errorService.builder()
                .validatePasswords(registrationDto.getPassword(), registrationDto.getPasswordConfirm());
        errors.throwErrorIfPresent(HttpStatusCodes.NOT_ACCEPTABLE.getValue());

        authService.attemptRegistration(registrationDto);


        return ResponseEntity.ok().body(null);
    }
}
