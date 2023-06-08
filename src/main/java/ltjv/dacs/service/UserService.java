package ltjv.dacs.service;

import ltjv.dacs.Repository.IUserRepository;
import ltjv.dacs.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@Slf4j
@Transactional
public class UserService implements UserDetailsService {
    @Autowired
    private IUserRepository userRepository;

    public void save(@NotNull User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) throws
            UsernameNotFoundException {
        return Optional.ofNullable(userRepository.findByUsername(username));
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws
            UsernameNotFoundException {
        return userRepository.findByUsername(username);
    } }
