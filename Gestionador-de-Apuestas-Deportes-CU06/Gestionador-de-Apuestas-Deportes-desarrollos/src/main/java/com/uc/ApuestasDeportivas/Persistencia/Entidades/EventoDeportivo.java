package com.uc.ApuestasDeportivas.Persistencia.Entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "evento_deportivo")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventoDeportivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEvento;

    private String liga;
    private String equipo1;
    private String equipo2;
    private String fecha;
    private String resultado;
}
