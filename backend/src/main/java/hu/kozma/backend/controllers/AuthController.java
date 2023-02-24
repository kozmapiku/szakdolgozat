package hu.kozma.backend.controllers;

import hu.kozma.backend.dto.LoginDTO;
import hu.kozma.backend.dto.UserDTO;
import hu.kozma.backend.mappers.UserMapper;
import hu.kozma.backend.models.Role;
import hu.kozma.backend.models.User;
import hu.kozma.backend.repository.RoleRepository;
import hu.kozma.backend.repository.UserRepository;
import hu.kozma.backend.rest.JwtTokenUtil;
import hu.kozma.backend.rest.RestResponseHandler;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationFailureServiceExceptionEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private UserDetailsService userDetailsService;
    private JwtTokenUtil jwtTokenUtil;

    @RequestMapping("/user")
    public Principal user(Principal user) {
        System.out.println("ASD");
        System.out.println(user);
        return user;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO){

        if(userRepository.existsUserByEmail(userDTO.getEmail())){
            return new ResponseEntity<>("Ez az e-mail cím már foglalt!", HttpStatus.BAD_REQUEST);
        }

        User user = UserMapper.toUser(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        Role role = new Role();
        role.setName("admin");
        user.addRole(role);
        userRepository.save(user);
        return RestResponseHandler.generateResponse("A regisztráció sikeres volt!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateWithJwt(@RequestBody UserDTO userDTO){
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    userDTO.getEmail(), userDTO.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            String token = jwtTokenUtil.generateToken(userDetails);
            long expiresIn = System.currentTimeMillis() + JwtTokenUtil.JWT_TOKEN_VALIDITY*1000;

            Optional<User> user = userRepository.findUserByEmail(userDTO.getEmail());

            if(user.isEmpty()) {
                throw new UsernameNotFoundException("Nem található felhasználó!");
            }
            LoginDTO loginDTO = new LoginDTO(user.get(), token, expiresIn);

            return RestResponseHandler.generateResponse(loginDTO);
        }
        catch(Exception e) {
            throw new AuthenticationCredentialsNotFoundException("Hibás e-mail cím vagy felhasználónév!");
        }
    }
}
