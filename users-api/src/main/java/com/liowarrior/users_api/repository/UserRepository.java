package com.liowarrior.users_api.repository;

import com.liowarrior.users_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//@Repository marca esta interfaz como un componente de acceso a datos
//Spring administra automaticamente (inyecciones de dependencias)

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    // Metodo personalizado: Spring Data JPA lee el NOMBRE del metodo
    // y genera el SQL automáticamente.
    Optional<User> findByEmail(String email);
}