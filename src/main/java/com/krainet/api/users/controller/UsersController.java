package com.krainet.api.users.controller;

import com.krainet.api.users.dto.UserDto;
import com.krainet.api.users.entity.Users;
import com.krainet.api.users.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UsersController {

    private final UserService userService;

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('USER') and @userService.isOwner(#id, principal))")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("User {} requested user with id {}", auth.getName(), id);
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("Admin {} requested all users list", auth.getName());
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PreAuthorize("hasRole('USER') and #id == principal.id")
    @PutMapping("/user/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("User {} is updating user with id {}", auth.getName(), id);
        return ResponseEntity.ok(userService.updateUser(id, userDto));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("User {} is deleting user with id {}", auth.getName(), id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}