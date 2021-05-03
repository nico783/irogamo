package myapp.service;

import myapp.dto.UserDto;
import myapp.entity.IrogamoUser;
import myapp.entity.Role;
import myapp.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder encoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Transactional
    public IrogamoUser saveUser(UserDto userDto) {
        IrogamoUser irogamoUser = new IrogamoUser();
        Role roleUser = new Role();
        roleUser.setAuthority("USER");
        irogamoUser.setAuthority(roleUser);
        irogamoUser.setCreationDate(LocalDate.now());
        irogamoUser.setUsername(userDto.getUsername());
        irogamoUser.setPassword(encoder.encode(userDto.getPassword()));
        return userRepository.save(irogamoUser);
    }

    @Transactional
    public IrogamoUser setAdmin(String username) {
        Optional<IrogamoUser> irogamoUser = userRepository.findByUsername(username);
        Role admin = new Role();
        admin.setAuthority("ADMIN");
        if (irogamoUser.isPresent()) {
            irogamoUser.get().setAuthority(admin);
            return irogamoUser.get();
        }
        throw new IllegalArgumentException("Username" + username + " not find.");
    }

    @Transactional
    public IrogamoUser changePassword(String password) {
        IrogamoUser user = (IrogamoUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<IrogamoUser> irogamoUser = userRepository.findByUsername(user.getUsername());
        if (irogamoUser.isPresent()) {
            irogamoUser.get().setPassword(password);
            return irogamoUser.get();
        }
        throw new IllegalStateException("Username" + user.getUsername() + " not find.");
    }

    @Transactional(readOnly = true)
    public List<IrogamoUser> getUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public Optional<IrogamoUser> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
