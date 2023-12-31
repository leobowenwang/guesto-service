package guesto.user.controller;

import guesto.user.dto.LoginDTO;
import guesto.user.dto.RegisterDTO;
import guesto.user.service.UserService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.authentication.AuthenticationResponse;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.rules.SecurityRule;
import jakarta.inject.Inject;

import java.util.Collections;

@Controller("/user")
public class UserController {

    @Inject
    private UserService userService;

    @Secured(SecurityRule.IS_ANONYMOUS)
    @Get(uri = "/", produces = "text/plain")
    public String index() {
        return "Example Response";
    }

    @Post("/login")
    public HttpResponse<?> login(@Body LoginDTO loginDTO) {
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(loginDTO.getUsername(), loginDTO.getPassword());
        return userService.login(credentials).map(token -> HttpResponse.ok(AuthenticationResponse.success(loginDTO.getUsername(), Collections.singleton(token)))).orElseGet(HttpResponse::unauthorized);
    }
    @Secured(SecurityRule.IS_ANONYMOUS)
    @Post("/register")
    public HttpResponse<?> register(@Body RegisterDTO registerDTO) {
        return userService.register(registerDTO).map(HttpResponse::created).orElseGet(HttpResponse::badRequest);
    }

}
