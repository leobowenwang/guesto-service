package guesto.controller;

import guesto.user.dto.LoginDTO;
import guesto.user.dto.LoginResponseDTO;
import guesto.user.service.UserService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import jakarta.inject.Inject;

import java.util.Optional;

@Controller("/guesto")
public class GuestoController {

    @Inject
    private UserService userService;

    @Get(uri = "/", produces = "text/plain")
    public String index() {
        return "Example Response";
    }

    @Post(uri = "/login", consumes = "application/json", produces = "application/json")
    public HttpResponse<LoginResponseDTO> login(@Body LoginDTO loginData) {
        Optional<LoginResponseDTO> loginResponse = userService.login(loginData);
        return loginResponse
                .map(HttpResponse::ok)
                .orElse(HttpResponse.unauthorized());
    }
}