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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "equipos")

public class Equipo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String liga;
    private double cuota; // Nueva columna para cuota


    // Constructor adicional para cuando no se proporciona cuota (puedes usarlo con 'null')
    public Equipo(Long id, String nombre, String liga) {
        this.id = id;
        this.nombre = nombre;
        this.liga = liga;
        this.cuota = 0.0; // Valor predeterminado
    }
}
