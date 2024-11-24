package com.uc.ApuestasDeportivas.Persistencia.Entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "apuestas")
public class Apuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idApuesta;

    private double montoApostado;

    @Column(name = "tipo_cuota")
    private String tipoCuota; // Local, Empate, Visitante

    private double cuotaSeleccionada;

    private String equipoLocal;
    private String equipoVisitante;

    private String estado; // Pendiente, Ganada, Perdida

    // Añadir esta relación
    @ManyToOne
    @JoinColumn(name = "usu_usuario", nullable = false)
    private Usuario usuario;

}

