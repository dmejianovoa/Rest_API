package com.liowarrior.users_api.service;

import com.liowarrior.users_api.model.User;
import com.liowarrior.users_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


//@Service marca esta clase como componente de logica de negocio
//Spring - Administración e inyeccion si es necesario
@Service
public class UserService {

    private final UserRepository userRepository;
    private final  PasswordEncoder passwordEncoder;
    //declaramos PasswordEncoder para contraseñas en forma hash
    //Inyecccion de dependencias por constructor: Spring nos entrega automaticamente
    //La instancia del UserRepository, no la creamos con "new"

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //Devuelve todos los usuarios
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    //Duelve usuario por ID
    public User getUserById(Integer id) {
        return userRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con el numero de id:" + id));

    }

    //Crear un usuario, validar que los datos no esten repetidos
    public User createUser(User user) {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            //Regla de negocio: Email duplicado no permitido
            throw new IllegalArgumentException("Ya existe un usuario con ese email");
        }

        //Hashear la contraseña antes de guardar en la base de datos
        String hashedPassword = passwordEncoder.encode(user.getPasswordHash());
        user.setPasswordHash(hashedPassword);

        return userRepository.save(user);
    }

    //Actualizar un usuario existente
    public User updateUser(Integer id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con el numero de id: " + id));

        //Actualizar solo datos editables
        if (userDetails.getUserName() != null) {
            user.setUserName(userDetails.getUserName());
        }
        if (userDetails.getLastName() != null) {
            user.setLastName(userDetails.getLastName());
        }
        if (userDetails.getEmail() != null) {
            user.setEmail(userDetails.getEmail());
        }
        if (userDetails.getPhoneNumber() != null) {
            user.setPhoneNumber(userDetails.getPhoneNumber());
        }
        if (userDetails.getUserRole() != null) {
            user.setUserRole(userDetails.getUserRole());
        }
        if (userDetails.getPasswordHash() != null) {
            //Solo si se realiza proceso de contraseña nueva / se hashea antes de guardar
            user.setPasswordHash(passwordEncoder.encode(userDetails.getPasswordHash()));
        }

        return userRepository.save(user);
    }

    //Eliminar un usuario por ID
    public void deleteID(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("Usuario no encontrado con numero id: " + id);
        }
        userRepository.deleteById(id);
    }
}
