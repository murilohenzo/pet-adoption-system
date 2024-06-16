package br.com.murilohenzo.ms.user.domain.services;

import br.com.murilohenzo.ms.user.domain.entities.User;
import br.com.murilohenzo.ms.user.domain.exceptions.EntityAlreadyExistsException;
import br.com.murilohenzo.ms.user.domain.exceptions.EntityNotFoundException;
import br.com.murilohenzo.ms.user.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuario nao encontrado"));
    }

    @Transactional
    public User createNewUser(User user) {
        log.debug("[D50] - RegisterUser user received {} ", user);
        existsUserWithSameEmailUsername(user);
        existsUserWithSameEmail(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = this.userRepository.save(user);
        return user;
    }

    @Transactional
    public User updateUser(Long id, User user) {
        User foundUser = findById(id);
        log.info("[W49] - Exists user with email = {}", foundUser.getEmail());
        foundUser.setFirstName(user.getFirstName());
        foundUser.setLastName(user.getLastName());
        foundUser.setEmail(user.getEmail());
        return userRepository.save(foundUser);
    }

    @Transactional
    public User updatePassword(User user) {
        log.debug("[D54] - UpdatePassword user received {} ", user);
        var existsUser = findById(user.getId());
        existsUser.setPassword(user.getPassword());
        existsUser = this.userRepository.save(existsUser);
        return existsUser;
    }

    @Transactional
    public void deleteUser(User user) {
        this.userRepository.delete(user);
    }

    private void existsUserWithSameEmail(User user) {
        this.userRepository.findByEmail(user.getEmail())
                .ifPresent(p -> {
                    log.info("User with email {} already exists", user.getEmail());
                    throw new EntityAlreadyExistsException("Usuario com email ja cadastrado");
                });
    }

    private void existsUserWithSameEmailUsername(User user) {
        this.userRepository.findByUsername(user.getUsername())
                .ifPresent(p -> {
                    log.info("User with username {} already exists", user.getEmail());
                    throw new EntityAlreadyExistsException("Usuario com username ja cadastrado");
                });
    }

}
