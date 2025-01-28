package com.artemsolovev.security;

import com.artemsolovev.model.User;
import com.artemsolovev.repository.UserRepository;
import com.artemsolovev.security.jwt.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByLogin(userName);

        user.orElseThrow(() -> new UsernameNotFoundException("User with username: " + userName + " not found"));

        log.info("IN loadUserByUsername - user with username: {} successfully loaded", userName);
        return user.map(UserDetailsImpl::new).get();
    }
}
