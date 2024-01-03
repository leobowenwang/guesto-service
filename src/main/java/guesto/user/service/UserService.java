package guesto.user.service;

import guesto.user.dto.RegisterDTO;
import guesto.user.exception.UsernameAlreadyExistsException;
import guesto.user.model.Role;
import guesto.user.model.User;
import guesto.user.repository.UserRepository;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.token.jwt.generator.JwtTokenGenerator;
import jakarta.inject.Singleton;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Singleton
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenGenerator jwtTokenGenerator;

    public UserService(UserRepository userRepository, JwtTokenGenerator jwtTokenGenerator) {
        this.userRepository = userRepository;
        this.jwtTokenGenerator = jwtTokenGenerator;
    }

    public Optional<String> login(UsernamePasswordCredentials credentials) {
        return userRepository.findByUsername(credentials.getUsername()).filter(user -> BCrypt.checkpw(credentials.getPassword(), user.getPassword())).map(user -> {
            Map<String, Object> claims = new HashMap<>();
            claims.put("sub", user.getUsername());
            claims.put("roles", Collections.singletonList(user.getRole().name()));

            Optional<String> token = jwtTokenGenerator.generateToken(claims);
            return token.orElse(null);
        });
    }

    public Optional<User> register(RegisterDTO registerDTO) {
        if (userRepository.findByUsername(registerDTO.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException(registerDTO.getUsername());
        }

        String hashedPassword = BCrypt.hashpw(registerDTO.getPassword(), BCrypt.gensalt());
        User newUser = new User(registerDTO.getUsername(), hashedPassword, registerDTO.getRole());
        userRepository.save(newUser);

        return Optional.of(newUser);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
