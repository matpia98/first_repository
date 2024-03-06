package pl.programujodpodstaw.springwebjpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Iterable<UserDTO> getAllUsers() {
        Iterable<User> users = userRepository.findAll();
        List<UserDTO> userDTOs = new ArrayList<>();

        for (User user : users) {
            UserDTO userDTO = mapperUserDTO(user, false);
            userDTOs.add(userDTO);
        }
        return userDTOs;
    }

    public UserDTO getUser(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser
                .map(user -> mapperUserDTO(user, false))
                .orElse(null);
    }

    public UserDTO addUser(User user) {
        User savedUser = userRepository.save(user);
        UserDTO userDTO = mapperUserDTO(savedUser, false);
        return userDTO;
    }

    public UserDTO updateUser(Integer id, User user) {
        return userRepository.findById(id).map(existingUser -> {
            existingUser.setLogin(user.getLogin());
            existingUser.setDisplayName(user.getDisplayName());
            existingUser.setYearOfBirth(user.getYearOfBirth());
            userRepository.save(existingUser);
            return mapperUserDTO(existingUser, false);
        }).orElse(null);
    }

    public UserDTO partiallyUpdateUser(Integer id, User user) {
        return userRepository.findById(id).map(existingUser -> {
            if (user.getLogin() != null) existingUser.setLogin(user.getLogin());
            if (user.getDisplayName() != null) existingUser.setDisplayName(user.getDisplayName());
            if (user.getYearOfBirth() != null) existingUser.setYearOfBirth(user.getYearOfBirth());
            userRepository.save(existingUser);
            UserDTO userDTO = mapperUserDTO(existingUser, false);
            return userDTO;
        }).orElse(null);
    }

    public boolean deleteUser(Integer id) {
        if (!userRepository.existsById(id)) {
            return false;
        }
        userRepository.deleteById(id);
        return true;
    }

    public static UserDTO mapperUserDTO(User user, boolean excludeDetails) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setLogin(user.getLogin());
        if (!excludeDetails) {
            userDTO.setDisplayName(user.getDisplayName());
            userDTO.setYearOfBirth(user.getYearOfBirth());
        }
        return userDTO;
    }
}

