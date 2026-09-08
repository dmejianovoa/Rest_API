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


