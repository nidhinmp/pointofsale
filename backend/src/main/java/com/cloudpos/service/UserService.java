package com.cloudpos.service;

import com.cloudpos.dto.*;
import com.cloudpos.entity.User;
import com.cloudpos.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return mapToDTO(user);
    }

    public UserDTO createUser(UserDTO dto) {
        User user = User.builder()
                .email(dto.getEmail())
                .passwordHash(dto.getPassword() != null ? dto.getPassword() : "default")
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .role(User.UserRole.valueOf(dto.getRole()))
                .status(User.UserStatus.valueOf(dto.getStatus()))
                .build();
        
        user = userRepository.save(user);
        return mapToDTO(user);
    }

    public UserDTO updateUser(Long id, UserDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        if (dto.getFirstName() != null) user.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null) user.setLastName(dto.getLastName());
        if (dto.getRole() != null) user.setRole(User.UserRole.valueOf(dto.getRole()));
        if (dto.getStatus() != null) user.setStatus(User.UserStatus.valueOf(dto.getStatus()));
        
        user = userRepository.save(user);
        return mapToDTO(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private UserDTO mapToDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole().name())
                .status(user.getStatus().name())
                .createdAt(user.getCreatedAt() != null ? user.getCreatedAt().toString() : null)
                .build();
    }
}