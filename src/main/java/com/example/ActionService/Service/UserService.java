package com.example.ActionService.Service;

import com.example.ActionService.Entity.User;
import com.example.ActionService.Exception.UserAlreadyExistsException;
import com.example.ActionService.Repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }



    public Map<String, Object> createUser(User user) {
        Optional<User> existingEmail = userRepository.findByEmail(user.getEmail());
        if (existingEmail.isPresent()) {
            throw new UserAlreadyExistsException("Email already exists! Try another.");
        }

        Optional<User> existingPhone = userRepository.findByPhone(user.getPhone());
        if (existingPhone.isPresent()) {
            throw new UserAlreadyExistsException("Phone number already exists! Try another.");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User savedUser = userRepository.save(user);

        return Map.of(
                "status", HttpStatus.CREATED.value(),
                "message", "User successfully created",
                "data", savedUser
        );
    }

    // Update User
    public Map<String, Object> updateUser(Long userId, User userDetails) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setName(userDetails.getName());
        existingUser.setEmail(userDetails.getEmail());
        existingUser.setPhone(userDetails.getPhone());
        existingUser.setRole(userDetails.getRole());
        existingUser.setAddress(userDetails.getAddress());

        User updatedUser = userRepository.save(existingUser);
        return Map.of(
                "status", 200,
                "message", "User successfully updated",
                "data", updatedUser
        );
    }

    // Delete User
    public Map<String, Object> deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userRepository.delete(user);
        return Map.of(
                "status", 200,
                "message", "User deleted successfully"
        );
    }
}
