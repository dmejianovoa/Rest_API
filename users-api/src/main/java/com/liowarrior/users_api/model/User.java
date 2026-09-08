package com.liowarrior.users_api.model;

import jakarta.persistence.*;
import org.hibernate.annotations.AnyDiscriminatorImplicitValues;
import org.springframework.boot.autoconfigure.web.WebProperties;

//@Entity - Habla con spring y le dice que va a mapear una BD
@Entity
//@Table - Especifica el nombre de la tabla
@Table(name="Users")
public class User {

    //@Id - Marca la llave primaria
    @id

    //@GenerateValue - Valor incremental de la DB (AUTO_INCREMENT)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Integer idUser;

    //@Column - Mapear el atributo Java al nombre real de DB
    @Column(name = "user_name", nullable = false, length = 50)
    private String userName;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "password_hash", nullable = false, length = 255)
    private String passwordHash;

    //@Enumerated - Dice a JPA que esto es un ENUM y que lo guarde como texto
    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private userRole userRole;


}

