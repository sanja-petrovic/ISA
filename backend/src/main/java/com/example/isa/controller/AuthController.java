package com.example.isa.controller;

import com.example.isa.dto.CredentialsDto;
import com.example.isa.dto.BloodDonorDto;
import com.example.isa.dto.UserDto;
import com.example.isa.dto.UserTokenState;
import com.example.isa.model.*;
import com.example.isa.security.TokenHandler;
import com.example.isa.service.interfaces.RefreshTokenService;
import com.example.isa.service.interfaces.RoleService;
import com.example.isa.service.interfaces.UserService;
import org.springframework.http.HttpHeaders;
import com.example.isa.util.converters.BloodDonorConverter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {
    private final TokenHandler tokenHandler;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final RoleService roleService;
    private final BloodDonorConverter bloodDonorConverter;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(TokenHandler tokenHandler, AuthenticationManager authenticationManager, BloodDonorConverter bloodDonorConverter, UserService userService, RoleService roleService, PasswordEncoder passwordEncoder, RefreshTokenService refreshTokenService) {
        this.tokenHandler = tokenHandler;
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.roleService = roleService;
        this.bloodDonorConverter = bloodDonorConverter;
        this.passwordEncoder = passwordEncoder;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserTokenState> createAuthenticationToken(
            @RequestBody CredentialsDto authenticationRequest, HttpServletResponse response) {
        Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();
        String jwt = this.tokenHandler.generateToken(user.getUsername());
        ResponseCookie jwtCookie = tokenHandler.generateJwtCookie(user);
        List<String> roles = user.getRoles().stream()
                .map(GrantedAuthority::getAuthority).toList();
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());
        ResponseCookie jwtRefreshCookie = tokenHandler.generateRefreshJwtCookie(refreshToken.getToken());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
                .body(new UserTokenState(jwt, user.getUsername()));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!Objects.equals(principal.toString(), "anonymousUser")) {
            refreshTokenService.deleteByUserId(((User) principal).getId());
        }

        ResponseCookie jwtCookie = tokenHandler.getCleanJwtCookie();
        ResponseCookie jwtRefreshCookie = tokenHandler.getCleanJwtRefreshCookie();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE, jwtRefreshCookie.toString())
                .build();
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(HttpServletRequest request) {
        String refreshToken = tokenHandler.getJwtRefreshFromCookies(request);

        if ((refreshToken != null) && (refreshToken.length() > 0)) {
            Optional<RefreshToken> optionalRefreshToken = refreshTokenService.findByToken(refreshToken);
            if(optionalRefreshToken.isPresent()) {
                RefreshToken token = optionalRefreshToken.get();
                if(refreshTokenService.verifyExpiration(token) != null) {
                    User tokenUser = token.getUser();
                    ResponseCookie jwtCookie = tokenHandler.generateJwtCookie(tokenUser);
                    return ResponseEntity.ok()
                            .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                            .header(HttpHeaders.SET_COOKIE, refreshToken)
                            .build();
                }
            } else {
                return ResponseEntity.badRequest().build();
            }
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/current")
    public ResponseEntity<UserDto> createAuthenticationToken() {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(new UserDto(user));
    }

    @PostMapping("/register")
    public ResponseEntity<BloodDonorDto> registerBloodDonor(@RequestBody BloodDonorDto dto) {
        if (userService.findByUsername(dto.getEmail()) == null) {
            BloodDonor bloodDonor = bloodDonorConverter.dtoToEntity(dto);
            bloodDonor.setRoles(roleService.findByName("ROLE_BLOOD_DONOR"));
            userService.register(bloodDonor);
            return new ResponseEntity<>(bloodDonorConverter.entityToDto(bloodDonor), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/verify/{email}")
    public ResponseEntity<?> verifyAccount(@PathVariable String email) {
        User user = userService.findByUsername(email);
        if(user == null) {
            return ResponseEntity.status(404).body("Account with the given email does not exist.");
        } else if(user.isVerified()) {
            return ResponseEntity.status(403).body("Account already verified.");
        } else {
            userService.verifyAccount(user);
            return ResponseEntity.ok().build();
        }
    }
}