package ltjv.dacs.service;

import lombok.RequiredArgsConstructor;
import ltjv.dacs.Repository.IRoleRepository;
import ltjv.dacs.Repository.IUserRepository;
import ltjv.dacs.constants.Role;
import ltjv.dacs.entity.Perfume;
import ltjv.dacs.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Slf4j
@Transactional
public class UserService  {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IRoleRepository roleRepository;

    public void save(User user) {
        userRepository.save(user);
        Long userId = userRepository.getUserIdByUsername(user.getUsername());
        Long roleId = roleRepository.getRoleIdByName("USER");
        if (roleId != 0 && userId != 0) {
            userRepository.addRoleToUser(userId, roleId);
        }
    }

    @PreAuthorize("hasAnyAuthority('USER') or hasAnyAuthority('ADMIN')")
    public List<User> getAllUsers(Integer pageNo,
                                        Integer pageSize,
                                        String sortBy) {

        return userRepository.findAllUsers(pageNo, pageSize, sortBy);
    }

    @PreAuthorize("hasAnyAuthority('USER') or hasAnyAuthority('ADMIN')")
    public User getUserById(String id){
        Optional<User> optional = userRepository.findById(id);
        return optional.orElse(null);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public void addUser(User user) {
        userRepository.save(user);
    }
}
