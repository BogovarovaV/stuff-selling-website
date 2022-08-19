package ru.skypro.homework.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.RegisterReq;
import ru.skypro.homework.dto.Role;
import ru.skypro.homework.model.Users;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserDetailsManager manager;

    private final PasswordEncoder encoder;

    private final UserRepository userRepository;

    public AuthServiceImpl(UserDetailsManager manager, UserRepository userRepository) {
        this.manager = manager;
        this.userRepository = userRepository;
        this.encoder = new BCryptPasswordEncoder();
    }

    @Override
    public boolean login(String userName, String password) {
        if (userRepository.findByUsername(userName) == null) {
            return false;
        }
        Users user = userRepository.findByUsername(userName);
        String pw_hash = user.getPassword();
//        UserDetails userDetails = manager.loadUserByUsername(userName);
//        String encryptedPassword = userDetails.getPassword();
//        String encryptedPasswordWithoutEncryptionType = encryptedPassword.substring(8);
        return BCrypt.checkpw(password, pw_hash);
    }

    @Override
    public boolean register(RegisterReq registerReq, Role role) {
        if (userRepository.findByUsername(registerReq.getUsername()) != null) {
            return false;
        }
        Users user = new Users();
        user.setFirstName(registerReq.getFirstName());
        user.setLastName(registerReq.getLastName());
        user.setUsername(registerReq.getUsername());
        String pw_hash = BCrypt.hashpw(registerReq.getPassword(), BCrypt.gensalt());
        user.setPassword(pw_hash);
        user.setPhone(registerReq.getPhone());
        user.setRole(Users.Role.USER);
//      user.setRole(role); надо так, но не получается
        userRepository.save(user);
        return true;
    }
}
