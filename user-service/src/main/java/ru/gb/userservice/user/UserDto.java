package ru.gb.userservice.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import ru.gb.userservice.user.validation.OnCreate;
import ru.gb.userservice.user.validation.OnUpdate;


@Getter
@Setter
@Schema(description = "Модель пользователя")
public class UserDto {
    @Schema(description = "ID пользователя", example = "1")
    @NotNull(message = "ID не может быть пустым", groups = OnUpdate.class)
    private Long id;
    @Schema(description = "Имя пользователя", example = "Ivan Ivanov")
    @Size(min = 2, max = 255, message = "Длина имени пользователя не может быть меньше 2," +
            " и больше 255 символов", groups = {OnUpdate.class, OnCreate.class})
    private String username;
    @Schema(description = "Email пользователя", example = "ivan@ya.ru")
    @Pattern(regexp = "\\w*@\\w*\\.\\w*", message = "Email не соответствует формату: ivan@ya.ru",
            groups = {OnUpdate.class, OnCreate.class})
    @Size(min = 5, max = 255, message = "Длина email не может быть меньше 5, и больше 255 символов",
            groups = {OnUpdate.class, OnCreate.class})
    private String email;
    @Schema(description = "Пароль пользователя", example = "123456")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 6, max = 255, message = "Длина пароля не может быть меньше 6, и больше 255 символов",
            groups = {OnUpdate.class, OnCreate.class})
    private String password;
    @Schema(description = "Подтвердить пароль", example = "123456")
    @Size(min = 6, max = 255, message = "Длина пароля не может быть меньше 6, и больше 255 символов",
            groups = {OnCreate.class})
    private String confirmedPassword;
}
