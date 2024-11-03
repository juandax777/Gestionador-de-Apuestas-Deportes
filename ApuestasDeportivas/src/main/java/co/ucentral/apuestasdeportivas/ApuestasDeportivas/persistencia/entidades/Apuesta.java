package co.ucentral.apuestasdeportivas.ApuestasDeportivas.persistencia.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "apuestas")
public class Apuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idApuesta;
    private double monto;
    private String equipoSeleccionado;
    private String estado;
    private double cuota;

    // Getters y setters
    public Long getIdApuesta() {
        return idApuesta;
    }

    public void setIdApuesta(Long idApuesta) {
        this.idApuesta = idApuesta;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public String getEquipoSeleccionado() {
        return equipoSeleccionado;
    }

    public void setEquipoSeleccionado(String equipoSeleccionado) {
        this.equipoSeleccionado = equipoSeleccionado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getCuota() {
        return cuota;
    }

    public void setCuota(double cuota) {
        this.cuota = cuota;
    }
}