package pl.programujodpodstaw.springwebjpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("users")
    public ResponseEntity<Iterable<UserDTO>> getAllUsers() {
        Iterable<UserDTO> allUsers = userService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    @GetMapping("users/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Integer id) {
        UserDTO userDTO = userService.getUser(id);
        if (userDTO != null) {
            return ResponseEntity.ok(userDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("users")
    public ResponseEntity<UserDTO> addUser(@RequestBody User user) {
        UserDTO userDTO = userService.addUser(user);
        return ResponseEntity.ok(userDTO);
    }

    @PutMapping("users/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Integer id, @RequestBody User user) {
        UserDTO userDTO = userService.updateUser(id, user);
        if (userDTO != null) {
           return ResponseEntity.ok(userDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("users/{id}")
    public ResponseEntity<UserDTO> partiallyUpdateUser(@PathVariable Integer id, @RequestBody User user) {
        UserDTO userDTO = userService.partiallyUpdateUser(id, user);
        if (userDTO != null) {
            return ResponseEntity.ok(userDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        if (userService.deleteUser(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
