package guesto.user.service;

import guesto.user.dto.RegisterDTO;
import guesto.user.model.User;
import guesto.user.repository.UserRepository;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.token.jwt.generator.JwtTokenGenerator;
import jakarta.inject.Singleton;
import org.mindrot.jbcrypt.BCrypt;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Singleton
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenGenerator jwtGenerator;

    public UserService(UserRepository userRepository, JwtTokenGenerator jwtGenerator) {
        this.userRepository = userRepository;
        this.jwtGenerator = jwtGenerator;
    }

    public Optional<String> login(UsernamePasswordCredentials credentials) {
        return userRepository.findByUsername(credentials.getUsername()).filter(user -> BCrypt.checkpw(credentials.getPassword(), user.getPassword())).map(user -> {
            Map<String, Object> claims = new HashMap<>();
            claims.put("sub", user.getUsername());

            Optional<String> token = jwtGenerator.generateToken(claims);
            return token.orElse(null);
        });
    }

    public Optional<User> register(RegisterDTO registerDTO) {
        if (userRepository.findByUsername(registerDTO.getUsername()).isPresent()) {
            return Optional.empty();
        }

        String hashedPassword = BCrypt.hashpw(registerDTO.getPassword(), BCrypt.gensalt());
        User newUser = new User(registerDTO.getUsername(), hashedPassword);
        userRepository.save(newUser);

        return Optional.of(newUser);
    }

}
