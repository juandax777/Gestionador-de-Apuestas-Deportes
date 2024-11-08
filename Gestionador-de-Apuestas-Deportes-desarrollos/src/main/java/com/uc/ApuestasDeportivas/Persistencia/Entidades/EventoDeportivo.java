package com.uc.ApuestasDeportivas.Persistencia.Entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;
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

    private String nombre;
    private Date fecha;
    private String equipoParticipante;
    private String resultado;
}
