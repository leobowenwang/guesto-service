package guesto.user.service;

import guesto.user.dto.LoginDTO;
import guesto.user.dto.LoginResponseDTO;
import jakarta.inject.Singleton;

import java.util.Optional;

@Singleton
public class UserService {

    public Optional<LoginResponseDTO> login(LoginDTO loginData) {
        if ("validUser".equals(loginData.getUsername()) && "password123".equals(loginData.getPassword())) {
            // Erfolgreiche Authentifizierung: Erstellen und Rückgabe eines Authentifizierungstokens
            // Der Token wäre normalerweise ein JWT oder ein ähnliches Token
            String token = "dummyToken12345";
            return Optional.of(new LoginResponseDTO(token));
        } else {
            return Optional.empty();
        }
    }
}
