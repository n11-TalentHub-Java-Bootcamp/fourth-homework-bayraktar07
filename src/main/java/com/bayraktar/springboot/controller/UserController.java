package com.bayraktar.springboot.controller;

import com.bayraktar.springboot.dto.UserDTO;
import com.bayraktar.springboot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<UserDTO> getUserByName(@RequestParam String name) {
        return ResponseEntity.ok(userService.findByName(name));
    }

    @PostMapping
    public ResponseEntity<UserDTO> saveUser(@RequestBody @Valid UserDTO userDTO) {
        userDTO = userService.save(userDTO);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userDTO.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUserById(@RequestParam Long id) {
        userService.deleteById(id);
        return ResponseEntity.ok("User deleted");
    }

    @PutMapping
    public ResponseEntity<UserDTO> updateUser (@RequestBody @Valid UserDTO userDTO) {
        return ResponseEntity.ok(userService.update(userDTO));
    }
}
