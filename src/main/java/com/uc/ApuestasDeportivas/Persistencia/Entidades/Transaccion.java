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

    private LocalDateTime fecha; // Fecha y hora de la transacción

    private double monto; // Monto de la transacción

    private String tipo; // Puede ser "Ingreso", "Apuesta", "Cancelación", etc.

    private String banco; // Banco desde donde se realiza la transacción

    private double saldo; // Saldo después de la transacción

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario; // Relación con el usuario
}
