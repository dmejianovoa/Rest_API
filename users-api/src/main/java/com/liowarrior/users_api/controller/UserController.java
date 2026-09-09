package com.liowarrior.users_api.controller;


import com.liowarrior.users_api.model.User;
import com.liowarrior.users_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//@RestController = @Controller + @ResposeBody:
//Maneja peticiones HTTP y todo lo que se devuelve se convierte en JSON automaticamente
@RestController

//@RequestMapping define el prefijo de todo los endpoints de esta clase
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    //Inyeccion de dependencias: Spring entrega Userservice ya listo
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // GET /api/users -> lista de todos los usuarios
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users); //200 ok + Lista de JSON
    }

    //GET /api/users/{id} -> busca usuario por id
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        Optional<User> user = userService.getUserById(id);
        //Si existe, responde 200 con usuario - si no muestra error 404 not found
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // POST /api/users -> crear un nuevo usuario
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            User createdUser = userService.createUser(user);
            //201 Created es el codigo correcto al momento de la creación de nuevo usuario
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (IllegalArgumentException e) {
            // 400 Bad Request - Si la regla de negocio falla
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // PUT /api/users/{id} -> actualiza un usuario existente
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody User userDetails) {
        try {
            User updateUser = userService.updateUser(id, userDetails);
            return ResponseEntity.ok(updateUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //DELETE /api/users/{id} -> eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        try {
            userService.deleteID(id);
            //204 No Content: Se elimino - pero no hay resultado devuelta
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}



