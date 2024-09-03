package com.uc.ApuestasDeportivas.Persistencia.Entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transaccion")
public class Transaccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTransaccion;

    private LocalDateTime fecha;

    private double monto;

    private String tipo;

    private String banco;

    private double saldo;

    @ManyToOne
    @JoinColumn(name = "usu_usuario", nullable = false)
    private Usuario usuario;
}

