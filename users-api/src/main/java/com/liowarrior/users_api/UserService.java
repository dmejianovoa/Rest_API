package com.liowarrior.users_api;

import com.liowarrior.users_api.model.User;
import com.liowarrior.users_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//@Service marca esta clase como componente de logica de negocio
//Spring - Administración e inyeccion si es necesario
@Service
public class UserService {

    private final UserRepository userRepository;

    //Inyecccion de dependencias por constructor: Spring nos entrega automaticamente
    //La instancia del UserRepository, no la creamos con "new"

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //Devuelve todos los usuarios
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    //Duelve usuario por ID
    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    //Crear un usuario, validar que los datos no esten repetidos
    public User createUser(User user) {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            //Regla de negocio: Email duplicado no permitido
            throw new IllegalArgumentException("Ya existe un usuario con ese email");
        }
        return userRepository.save(user);
    }

    //Actualizar un usuario existente
    public User updateUser(Integer id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con el numero de id: " + id));

        //Actualizar solo datos editables
        user.setUserName(userDetails.getUserName());
        user.setEmail(userDetails.getEmail());
        user.setUserRole(userDetails.getUserRole());

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
