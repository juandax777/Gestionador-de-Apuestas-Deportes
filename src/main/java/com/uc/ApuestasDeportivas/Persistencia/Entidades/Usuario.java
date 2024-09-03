package com.uc.ApuestasDeportivas.Persistencia.Entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @Column(name = "usu_usuario")
    private String usuario;

    @Column(name = "usu_contrasena", nullable = false)
    private String contrasena;

    @Column(name = "saldo", nullable = false)
    private double saldo = 0.0; // Saldo inicial predeterminado
}
