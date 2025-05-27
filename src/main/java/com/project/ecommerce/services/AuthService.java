package com.project.ecommerce.services;

import com.project.ecommerce.dto.AuthRequestDTO;
import com.project.ecommerce.dto.AuthResponseDTO;
import com.project.ecommerce.dto.UserDTO;
import com.project.ecommerce.dto.UserRegistrationDTO;
import com.project.ecommerce.exceptions.EmailAlreadyInUseException;
import com.project.ecommerce.exceptions.InvalidCredentialsException;
import com.project.ecommerce.exceptions.UserNotFoundException;
import com.project.ecommerce.models.User;
import com.project.ecommerce.repositories.UserRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AuthResponseDTO authenticate(AuthRequestDTO authRequestDTO) {
        if (StringUtils.isEmpty(authRequestDTO.email()) || StringUtils.isEmpty(authRequestDTO.password())) {
            throw new IllegalArgumentException("Missing login or password");
        }

        User user = userRepository.findByEmail(authRequestDTO.email())
                .orElseThrow(UserNotFoundException::new);

        if (!user.getPassword().equals(authRequestDTO.password())) {
            throw new InvalidCredentialsException();
        }

        return new AuthResponseDTO(user.getName(), user.getEmail());
    }

    public UserDTO registerUser(UserRegistrationDTO userRegistrationDTO) {
        if (StringUtils.isEmpty(userRegistrationDTO.name()) || StringUtils.isEmpty(userRegistrationDTO.email()) || StringUtils.isEmpty(userRegistrationDTO.password())) {
            throw new IllegalArgumentException("Missing name or email or password");
        }

        if(userRepository.findByEmail(userRegistrationDTO.email()).isPresent()){
            throw new EmailAlreadyInUseException();
        }

        User user = new User(userRegistrationDTO.name(),userRegistrationDTO.email(),userRegistrationDTO.password());
        userRepository.save(user);
        return new UserDTO(userRegistrationDTO.name(),userRegistrationDTO.email());
    }

}
