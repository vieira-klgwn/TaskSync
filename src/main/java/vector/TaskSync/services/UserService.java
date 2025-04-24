package vector.TaskSync.services;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vector.TaskSync.models.ChangePasswordRequest;
import vector.TaskSync.models.User;
import vector.TaskSync.repositories.UserRepository;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    //functionality of chnaging the password
    public void changePassword(ChangePasswordRequest request, Principal connecteUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connecteUser).getPrincipal();

        if (passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }

        if(!request.getNewPassword().equals(request.getConfirmationPassword())){
            throw new IllegalStateException("Passwords do not match");
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);


    }

    //create
    public User createUser(User newUser) {
        return userRepository.save(newUser);
    }

    //find all
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    //find by id
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    //update
    public User updateUser(Long id, User updatedUser) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setId(updatedUser.getId());
            user.setFirstName(updatedUser.getFirstName());
            user.setLastName(updatedUser.getLastName());
            user.setPassword(updatedUser.getPassword());
            user.setEmail(updatedUser.getEmail());
            user.setRole(updatedUser.getRole());
            user.setTeams(updatedUser.getTeams());
            return userRepository.save(user);

        }
        throw new RuntimeException("User with id " + id + " not found");
    }

    //delete
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    //this was done by my own logic i don't know if it will work

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);

    }
}
