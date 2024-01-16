package guesto.user.service;

import guesto.user.dto.RegisterDTO;
import guesto.user.dto.UserDTO;
import guesto.user.exception.UsernameAlreadyExistsException;
import guesto.user.model.GuestoToken;
import guesto.user.model.User;
import guesto.user.repository.UserRepository;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.token.jwt.generator.JwtTokenGenerator;
import jakarta.inject.Singleton;
import org.mindrot.jbcrypt.BCrypt;

import java.util.*;
import java.util.stream.Collectors;

@Singleton
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenGenerator jwtTokenGenerator;

    public UserService(UserRepository userRepository, JwtTokenGenerator jwtTokenGenerator) {
        this.userRepository = userRepository;
        this.jwtTokenGenerator = jwtTokenGenerator;
    }

    public Optional<GuestoToken> login(UsernamePasswordCredentials credentials) {
        return userRepository.findByUsername(credentials.getUsername()).filter(user -> BCrypt.checkpw(credentials.getPassword(), user.getPassword())).flatMap(user -> {
            Map<String, Object> claims = new HashMap<>();
            claims.put("sub", user.getUsername());
            claims.put("userId", user.getId());
            claims.put("roles", Collections.singletonList(user.getRole()));

            return jwtTokenGenerator.generateToken(claims).map(token -> new GuestoToken(token, user.getId()));
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

    public Optional<UserDTO> updateUser(Long userId, UserDTO updatedUserDTO) {
        return userRepository.findById(userId).map(user -> {
            user.setUsername(updatedUserDTO.getUsername());
            user.setRole(updatedUserDTO.getRole());

            userRepository.update(user);
            return UserDTO.fromEntity(user);
        });
    }

    public boolean deleteUser(Long userId) {
        return userRepository.findById(userId).map(user -> {
            userRepository.delete(user);
            return true;
        }).orElse(false);
    }

    public List<UserDTO> listAllUsers() {
        return userRepository.findAll().stream().map(UserDTO::fromEntity).collect(Collectors.toList());
    }

}
