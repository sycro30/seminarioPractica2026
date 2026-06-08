package controlador.eventos;

import java.time.LocalDate;

import datos.DatosIniciales;
import modelo.Asistencia;
import modelo.Evento;
import modelo.Miembro;

//----------------CONTROLADOR DE REGISTRO DE ASISTENCIA A EVENTOS----------------//
// Gestiona el registro de asistencias de miembros a eventos, evitando duplicados y validando los datos ingresados.
public class ControladorRegistroAsistenciaEvento {

  //----------------METODO PARA REGISTRAR UNA ASISTENCIA----------------//
  // Valida los datos, verifica que el miembro no tenga una asistencia registrada previamente para el evento y crea el nuevo registro.
  public boolean registrarAsistencia(
    Evento evento, Miembro miembro, String estado, LocalDate fechaAsistencia, String observaciones
  ) {
    validarDatos(evento, miembro, estado, fechaAsistencia);
    if (yaExisteAsistencia(evento, miembro)) {
      return false;
    }
    int nuevoId = generarId(evento);
    Asistencia asistencia = new Asistencia(
      nuevoId,
      miembro,
      evento,
      LocalDate.now(),      // Fecha de registro del sistema
      fechaAsistencia,      // Fecha real de asistencia al evento
      estado,
      observaciones
    );

    evento.agregarAsistencia(asistencia);
    DatosIniciales.asistencias.add(asistencia);
    return true;
  }

  //----------------METODO PARA VALIDAR LOS DATOS----------------//
  // Verifica que todos los datos necesarios para registrar una asistencia sean válidos.
  private void validarDatos(Evento evento, Miembro miembro, String estado, LocalDate fechaAsistencia) {
    // Validar evento seleccionado
    if (evento == null) {
      throw new IllegalArgumentException("Debe seleccionar un evento.");
    }

    // Validar miembro seleccionado
    if (miembro == null) {
      throw new IllegalArgumentException("Debe seleccionar un miembro.");
    }

    // Validar estado de asistencia
    if (estado == null || 
      (
        !estado.equalsIgnoreCase("Presente")
        && !estado.equalsIgnoreCase("Ausente")
      )
    ) {
      throw new IllegalArgumentException("Estado de asistencia inválido.");
    }

    // Validar fecha
    if (fechaAsistencia == null) {
      throw new IllegalArgumentException("Debe seleccionar una fecha.");
    }

    // La fecha de asistencia debe coincidir con la fecha del evento
    if (!fechaAsistencia.equals(evento.getFechaActividad())) {
      throw new IllegalArgumentException("La fecha de asistencia debe coincidir con la fecha del evento.");
    }
  }

  //----------------METODO PARA VERIFICAR ASISTENCIA DUPLICADA----------------//
  // Comprueba si el miembro ya tiene una asistencia registrada para el evento seleccionado.
  private boolean yaExisteAsistencia(Evento evento, Miembro miembro) {
    return evento.getAsistencias().stream().anyMatch(
      asistencia -> asistencia.getMiembro().getId() == miembro.getId()
    );
  }

  //----------------METODO PARA GENERAR EL ID DE LA ASISTENCIA----------------//
  // Genera un identificador único tomando el mayor ID existente dentro de las asistencias del evento.
  private int generarId(Evento evento) {
    if (evento.getAsistencias().isEmpty()) {
      return 1;
    }
    return evento.getAsistencias().stream()
      .mapToInt(Asistencia::getId)
      .max()
      .orElse(0) + 1;
  }
}