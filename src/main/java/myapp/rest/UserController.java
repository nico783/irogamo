package myapp.rest;

import myapp.dto.UserDto;
import myapp.entity.IrogamoUser;
import myapp.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final BCryptPasswordEncoder encoder;

    public UserController(UserService userService, BCryptPasswordEncoder encoder) {
        this.userService = userService;
        this.encoder = encoder;
    }

    @GetMapping("/users")
    public List<IrogamoUser> getUsers() {
        logger.info("get users list");
        return userService.getUsers();
    }

    @PostMapping("/users")
    public void createUser(@RequestBody UserDto userDto) {
        logger.info("create user : " + userDto);
        userService.saveUser(userDto);
    }

    @PreAuthorize("hasRole({'ADMIN'})")
    @PutMapping("/users-set-admin")
    public void setAdminRole(@RequestParam String username) {
        logger.info("Give admin role for user : " + username);
        userService.setAdmin(username);
    }

    @PutMapping("/users-change-password")
    public void setPassword(String password) {
        userService.changePassword(encoder.encode(password));
    }

}
