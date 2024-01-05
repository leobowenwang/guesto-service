package guesto.user;

import guesto.user.dto.LoginDTO;
import guesto.user.dto.LoginResponseDTO;
import guesto.user.dto.RegisterDTO;
import guesto.user.service.UserService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.rules.SecurityRule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;

@Controller("/user")
@Secured(SecurityRule.IS_ANONYMOUS)
@Tag(name = "Users", description = "User authentication and management")
public class UserController {

    @Inject
    private UserService userService;

    @Get(uri = "/", produces = "text/plain")
    public String index() {
        return "Example Response";
    }

    @Post("/login")
    @Operation(summary = "User Login", description = "Authenticates a user with the provided credentials.")
    public HttpResponse<?> login(@Body LoginDTO loginDTO) {
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(loginDTO.getUsername(), loginDTO.getPassword());
        return userService.login(credentials).map(token -> HttpResponse.ok(new LoginResponseDTO(loginDTO.getUsername(), token))).orElseGet(HttpResponse::unauthorized);
    }

    @Post("/register")
    @Operation(summary = "User Registration", description = "Registers a new user with the provided details.")
    public HttpResponse<?> register(@Body RegisterDTO registerDTO) {
        return userService.register(registerDTO).map(HttpResponse::created).orElseGet(HttpResponse::badRequest);
    }

}
