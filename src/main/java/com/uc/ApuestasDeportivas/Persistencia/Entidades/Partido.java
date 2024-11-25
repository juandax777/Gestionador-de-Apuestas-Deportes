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
@Table(name = "partidos")
public class Partido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "equipo_local_id", nullable = false)
    private Equipo equipoLocal;

    @ManyToOne
    @JoinColumn(name = "equipo_visitante_id", nullable = false)
    private Equipo equipoVisitante;

    private String fecha; // Fecha del partido
    private String resultado; // Formato: "golesLocal-golesVisitante"

    private Double cuotaEmpate; // Cuota para empate

    private Integer golesLocal; // Goles del equipo local
    private Integer golesVisitante; // Goles del equipo visitante
}
