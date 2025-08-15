package com.krainet.api.users.service;

import com.krainet.api.users.dto.UserDto;
import com.krainet.api.users.entity.Users;
import com.krainet.api.users.mapper.UserMapper;
import com.krainet.api.users.repository.UserRepository;
import com.krainet.common.model.entity.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Transactional
    public UserDto createUser(UserDto userDto) {
        Users user = userMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Users savedUser = userRepository.save(user);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            log.info("User {} created new user with id {}", auth.getName(), savedUser.getId());
        } else {
            log.info("New user registered with id {}", savedUser.getId());
        }

        return userMapper.toDto(savedUser);
    }

    @Transactional
    public UserDto getUserById(Long id) {
        return userMapper.toDto(userRepository.findById(id).orElseThrow());
    }

    @Transactional
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserDto updateUser(Long id, UserDto userDto) {
        Users existingUser = userRepository.findById(id).orElseThrow();
        userMapper.updateEntity(userDto, existingUser);
        Users updatedUser = userRepository.save(existingUser);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("User {} updated user with id {}", auth.getName(), id);

        return userMapper.toDto(updatedUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("User {} deleted user with id {}", auth.getName(), id);
        userRepository.deleteById(id);
    }
    public boolean isOwner(Long userId, Object principal) {
        if (principal instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) principal;
            return userDetails.getId().equals(userId);
        }
        return false;
    }
}