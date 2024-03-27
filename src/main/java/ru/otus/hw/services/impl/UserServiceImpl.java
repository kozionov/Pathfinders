package ru.otus.hw.services.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.dto.UserCreateDto;
import ru.otus.hw.dto.UserDto;
import ru.otus.hw.dto.UserUpdateDto;
import ru.otus.hw.entity.Club;
import ru.otus.hw.entity.Log;
import ru.otus.hw.entity.User;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.repositories.ClubRepository;
import ru.otus.hw.repositories.LogRepository;
import ru.otus.hw.repositories.RoleRepository;
import ru.otus.hw.repositories.UserRepository;
import ru.otus.hw.security.UserPrincipal;
import ru.otus.hw.services.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final LogRepository logRepository;

    private final ClubRepository clubRepository;

    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    @Override
    public UserDto findById(long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("User with id %d not found".formatted(id));
        }
        return modelMapper.map(user.get(), UserDto.class);
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserDto> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream().map(b -> modelMapper.map(b, UserDto.class)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserDto> findAllByRole(long roleId) {
        var role = roleRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("Role with id %d not found".formatted(roleId)));
        List<User> users = userRepository.findAllByRole(role);
        return users.stream().map(b -> modelMapper.map(b, UserDto.class)).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    @Override
    public UserDto insert(UserCreateDto userCreateDto) {
        var user = save(0L, userCreateDto.name(), userCreateDto.surname(), userCreateDto.mobileNumber(),
                userCreateDto.email(), userCreateDto.login(), userCreateDto.password(), userCreateDto.roleId());
        return modelMapper.map(user, UserDto.class);
    }

    @Transactional
    @Override
    public UserDto update(UserUpdateDto userUpdateDto) {
        var user = save(userUpdateDto.id(), userUpdateDto.name(), userUpdateDto.surname(), userUpdateDto.mobileNumber(),
                userUpdateDto.email(), userUpdateDto.login(), userUpdateDto.password(), userUpdateDto.roleId());
        return modelMapper.map(user, UserDto.class);
    }

    private UserDto save(Long id, String name, String surname, String mobileNumber, String email, String login,
                         String password, long roleId) {
        var role = roleRepository.findById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("Author with id %d not found".formatted(roleId)));
        var user = new User(id, name, surname, mobileNumber, email, login, new BCryptPasswordEncoder().encode(password), role, null);
        user = userRepository.save(user);
        addUserToLog(user);
        return modelMapper.map(user, UserDto.class);
    }

    private void addUserToLog(User user) {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal.getRole().equals("DIRECTOR")) {
            User dir = userRepository.findById(principal.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Dir with id %d not found".formatted(principal.getId())));
            Club club = clubRepository.findByDirector(dir)
                    .orElseThrow(() -> new EntityNotFoundException("Club by director id %d not found".formatted(principal.getId())));
            Log log = club.getLog().stream()
                    .filter(l -> testDate(l.getDateFrom(), l.getDateTo())).findFirst().orElseThrow();
            log.getMembers().add(user);
            user.setClub(club);
            userRepository.save(user);
            logRepository.save(log);
        }
    }

    private boolean testDate(LocalDate from, LocalDate to) {
        return LocalDate.now().isAfter(from) && LocalDate.now().isBefore(to);
    }

    @Transactional(readOnly = true)
    @Override
    public long count() {
        return userRepository.count();
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserDto> findAllByLogId(long id) {
        var log = logRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Log with id %d not found".formatted(id)));
        return log.getMembers().stream().map(b -> modelMapper.map(b, UserDto.class)).collect(Collectors.toList());
    }
}
