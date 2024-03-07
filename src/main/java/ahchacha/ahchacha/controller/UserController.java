package ahchacha.ahchacha.controller;

import ahchacha.ahchacha.domain.User;
import ahchacha.ahchacha.dto.UserDto;
import ahchacha.ahchacha.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public User login(@RequestBody UserDto.LoginRequestDto loginRequestDto, HttpSession session) {
        try {
            return userService.login(loginRequestDto, session);
        } catch (IOException e) {
            throw new RuntimeException("Login failed", e);
        }
    }
}
