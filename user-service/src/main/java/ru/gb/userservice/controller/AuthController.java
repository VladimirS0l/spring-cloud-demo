package ru.gb.userservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.gb.userservice.service.AuthServiceImpl;
import ru.gb.userservice.service.UserServiceImpl;
import ru.gb.userservice.user.UserDto;
import ru.gb.userservice.user.UserMapper;
import ru.gb.userservice.user.auth.JwtRequest;
import ru.gb.userservice.user.auth.JwtResponse;
import ru.gb.userservice.user.validation.OnCreate;

import java.security.Principal;

@RestController
@RequestMapping("v1/auth")
@RequiredArgsConstructor
@Validated
@Tag(name = "Авторизация", description = "Методы для управления авторизацией пользователя")
public class AuthController {
    private final AuthServiceImpl authService;
    private final UserServiceImpl userService;
    private final UserMapper userMapper;

    @PostMapping("login")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Авторизация пользователя")
    public JwtResponse login(@Validated
                             @RequestBody final JwtRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Регистрация пользователя")
    public UserDto register(@Validated(OnCreate.class)
                            @RequestBody final UserDto userDto) {
        return userMapper.toDto(userService.create(
                userMapper.toEntity(userDto)));
    }

    @GetMapping("info")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Информация об авторизации пользователя")
    public String getInfo(Principal principal) {
        if (principal != null) return "Вход выполнен: " + principal.getName();
        return "Пользователь не авторизовался";
    }

}
