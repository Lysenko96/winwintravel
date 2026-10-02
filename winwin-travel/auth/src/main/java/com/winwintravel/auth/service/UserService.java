package com.winwintravel.auth.service;

import com.winwintravel.auth.dto.UserDto;
import com.winwintravel.auth.model.User;
import com.winwintravel.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPasswordHash(),
                user.getAuthorities()
        );
    }

    public User findByUsername(String username) {
        return userRepository.findByEmail(username);
    }

    public void createNewUser(UserDto userDto) {
        User user = new User(userDto.getEmail(), userDto.getPassword(), userDto.getRoles());
        userRepository.save(user);
    }
}
