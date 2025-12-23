package ru.otus.hw.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.models.User;
import ru.otus.hw.repositories.UserRepository;

import java.util.Collection;
import java.util.Collections;

/**
 * Реализация UserDetailsService для Spring Security.
 * Загружает информацию о пользователе из базы данных.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Loading user by username: {}", username);
        
        User user = userRepository.findByLogin(username)
            .orElseThrow(() -> {
                log.warn("User not found: {}", username);
                return new UsernameNotFoundException("Пользователь не найден: " + username);
            });

        log.debug("User found: {}, role: {}", username, user.getRole().getName());
        
        return new org.springframework.security.core.userdetails.User(
            user.getLogin(),
            user.getPassword(),
            true, // enabled
            true, // accountNonExpired
            true, // credentialsNonExpired
            true, // accountNonLocked
            getAuthorities(user)
        );
    }

    /**
     * Получение ролей пользователя.
     */
    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        String roleName = "ROLE_" + user.getRole().getName();
        log.debug("Granting authority: {}", roleName);
        return Collections.singletonList(new SimpleGrantedAuthority(roleName));
    }
}
