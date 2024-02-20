package ru.gb.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.userservice.repo.UserRepository;
import ru.gb.userservice.user.Role;
import ru.gb.userservice.user.User;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Пользователь не найден"));
        return user;
    }

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Пользователь не найден"));
    }

    @Transactional(readOnly = true)
    public User findByName(String name) {
        return userRepository.findByName(name)
                .orElseThrow(() ->
                        new RuntimeException("Пользователь не найден"));
    }

    @Transactional
    public User create(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalStateException("Пользователь с таким email уже существует");
        }
        if (userRepository.findByName(user.getUsername()).isPresent()) {
            throw new IllegalStateException("Пользователь с таким именем уже существует");
        }
        if (!user.getPassword().equals(user.getConfirmedPassword())) {
            throw new IllegalStateException(
                    "Пароли не совпадают"
            );
        }
        user.setId(null);
        user.setRoles(Collections.singleton(new Role(1, "ROLE_USER")));
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    @Transactional
    public User update(User updateUser, String email) {
        if (!updateUser.getPassword().equals(updateUser.getConfirmedPassword())) {
            throw new IllegalStateException(
                    "Пароли не совпадают"
            );
        }
        var user = findById(updateUser.getId());
        user.setName(updateUser.getName());
        user.setPassword(passwordEncoder.encode(updateUser.getPassword()));
        user.setEmail(updateUser.getEmail());
        if (!user.getEmail().equals(email)) {
            throw new RuntimeException("Вы не можете изменить другого пользователя");
        }
        return userRepository.save(user);
    }

    @Transactional
    public void delete(Long id, String email) {
        var user = findById(id);
        if (user.getEmail().equals(email)) {
            userRepository.delete(user);
        } else {
            throw new RuntimeException("Вы не можете удалить другого пользователя");
        }
    }

    @Transactional
    public User addUserAdminRole(Long id) {
        var user = findById(id);
        user.setRoles(Collections.singleton(new Role(2, "ROLE_ADMIN")));
        return userRepository.save(user);
    }
}
