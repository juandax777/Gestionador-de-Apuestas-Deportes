package co.ucentral.apuestasdeportivas.ApuestasDeportivas.servicios;

import co.ucentral.apuestasdeportivas.ApuestasDeportivas.persistencia.entidades.Transaccion;
import co.ucentral.apuestasdeportivas.ApuestasDeportivas.persistencia.repositorios.TransaccionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransaccionServicios {

    @Autowired
    private TransaccionRepositorio transaccionRepositorio;

    /**
     * Registra una transacción automática sin un usuario específico.
     * @param monto Monto de la transacción.
     * @param tipo Tipo de transacción (por ejemplo, "INGRESO" o "RETIRO").
     */
    public void registrarTransaccionAutomatica(double monto, String tipo) {
        Transaccion transaccion = new Transaccion();
        transaccion.setFecha(LocalDateTime.now());
        transaccion.setMonto(monto);
        transaccion.setTipo(tipo);

        // Guardar la transacción sin un usuario asociado
        transaccionRepositorio.save(transaccion);
    }
}
