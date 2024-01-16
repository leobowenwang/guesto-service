package guesto.user;

import guesto.user.dto.LoginDTO;
import guesto.user.dto.LoginResponseDTO;
import guesto.user.dto.RegisterDTO;
import guesto.user.dto.UserDTO;
import guesto.user.model.Role;
import guesto.user.service.UserService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.*;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.rules.SecurityRule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;

import java.util.List;

@Controller("/user")
@Secured(SecurityRule.IS_ANONYMOUS)
@Tag(name = "Users", description = "User authentication and management")
public class UserController {

    @Inject
    private UserService userService;

    @Get("/list")
    @Secured(Role.ADMIN)
    @Operation(summary = "List All Users", description = "Lists all registered users.")
    public HttpResponse<List<UserDTO>> listUsers() {
        List<UserDTO> users = userService.listAllUsers();
        return HttpResponse.ok(users);
    }

    @Post("/login")
    @Operation(summary = "User Login", description = "Authenticates a user with the provided credentials.")
    public MutableHttpResponse<LoginResponseDTO> login(@Body LoginDTO loginDTO) {
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(loginDTO.getUsername(), loginDTO.getPassword());
        return userService.login(credentials)
                .map(guestoToken -> {
                    Long userId = guestoToken.getUserId();
                    String token = guestoToken.getToken();
                    return HttpResponse.ok(new LoginResponseDTO(userId, loginDTO.getUsername(), token));
                })
                .orElseGet(HttpResponse::unauthorized);
    }

    @Post("/register")
    @Operation(summary = "User Registration", description = "Registers a new user with the provided details.")
    public HttpResponse<?> register(@Body RegisterDTO registerDTO) {
        return userService.register(registerDTO).map(HttpResponse::created).orElseGet(HttpResponse::badRequest);
    }

    @Put("/{userId}")
    @Secured(Role.ADMIN)
    @Operation(summary = "Update User", description = "Updates a user's details with the specified ID.")
    public HttpResponse<?> updateUser(@PathVariable Long userId, @Body UserDTO userDTO) {
        return userService.updateUser(userId, userDTO)
                .map(updatedUser -> HttpResponse.ok(updatedUser))
                .orElseGet(() -> HttpResponse.notFound());
    }

    @Delete("/{userId}")
    @Secured("ADMIN")
    @Operation(summary = "Delete User", description = "Deletes a user with the specified ID.")
    public HttpResponse<?> deleteUser(Long userId) {
        boolean success = userService.deleteUser(userId);
        if (success) {
            return HttpResponse.noContent();
        } else {
            return HttpResponse.notFound();
        }
    }
}
