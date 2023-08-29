package com.dongguk.cse.naemansan.security;

import com.dongguk.cse.naemansan.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserRepository.UserLoginForm user = userRepository
                .findUserForAuthentication(Long.valueOf(username))
                .orElseThrow(() -> new UsernameNotFoundException("ACCESS_DENIED_ERROR"));

        return CustomUserDetail.create(user);
    }
}
