package vector.TaskSync.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import vector.TaskSync.models.ChangePasswordRequest;
import vector.TaskSync.models.User;
import vector.TaskSync.models.UserDTO;
import vector.TaskSync.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> user = userService.getAllUsers();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        return userService.getUserById(id)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody User user){
        try{
           User updatedUser = userService.updateUser(id, user);
           return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest, Principal connectedUser){
        userService.changePassword(changePasswordRequest, connectedUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal() instanceof String) {
            logger.warn("Unauthorized access attempt");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String email = authentication.getName();
        logger.info("Fetching user with email: {}", email);
        return userService.getUserByEmail(email)
                .map(user -> {
                    logger.info("User found: {}", user.getEmail());
                    UserDTO userDTO = new UserDTO();
                    userDTO.setId(user.getId());
                    userDTO.setFirstName(user.getFirstName());
                    userDTO.setLastName(user.getLastName());
                    userDTO.setEmail(user.getEmail());
                    return new ResponseEntity<>(userDTO, HttpStatus.OK);
                })
                .orElseGet(() -> {
                    logger.warn("User not found for email: {}", email);
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                });
    }
}
