package santander.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import santander.jwt.JwtUtils;
import santander.model.user.User;
import santander.model.user.UserDto;
import santander.repository.UserJpaRepository;
import santander.service.UserDetailsImpl;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api/auth")
@Tag(name = "auth-resources")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;


    private DozerBeanMapper mapper = new DozerBeanMapper();

    @Operation(summary = "init sesion with username and password user")
    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@Valid @RequestBody UserDto userDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(String.format("Roles: %s. JWT token: %s", roles, jwt));
    }

    @Operation(summary = "register a new user")
    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserDto userDto) {
        User user = mapper.map(userDto, User.class);
        user.setPassword(encoder.encode(userDto.getPassword()));
        userJpaRepository.save(user);

        return ResponseEntity.ok(String.format("User %s, registered successfully!", userDto.getFirstName()));
    }
}