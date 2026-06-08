package controlador.reportes;

import modelo.Asistencia;
import modelo.Evento;
import vista.reportes.evento.ReporteEvento;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import datos.DatosIniciales;

/*
  Actualmente todos los eventos se consideran dirigidos a la totalidad de los miembros registrados.
  Se pretende incorporar grupo objetivo específico por evento para calcular participación segmentada.
*/
public class ControladorReporteAsistenciaEventos {
  //----------------METODO PARA GENERAR EL REPORTE DE ASISTENCIA----------------//
  /* Valida los parámetros recibidos y genera un reporte para cada uno de los eventos seleccionados, 
    calculando sus indicadores de participación. */
  public List<ReporteEvento> generarReporte(List<Evento> eventosSeleccionados, int totalMiembros) {
    // Validación de la lista de eventos recibida.
    if (eventosSeleccionados == null) {
      throw new IllegalArgumentException("La lista de eventos no puede ser nula.");
    }

    // Si no existen eventos seleccionados no hay información para procesar.
    if (eventosSeleccionados.isEmpty()) {
      return Collections.emptyList();
    }

    if (totalMiembros < 0) {
      throw new IllegalArgumentException("La cantidad total de miembros no puede ser negativa.");
    }

    List<ReporteEvento> reporte = new ArrayList<>();

    // Recorre cada evento seleccionado y genera su reporte individual.
    for (Evento evento : eventosSeleccionados) {
      if (evento == null) {
        continue;
      }
      ReporteEvento reporteEvento = generarReporteEvento(evento, totalMiembros);
      reporte.add(reporteEvento);
    }

    return reporte;
  }

  //----------------METODO PARA GENERAR EL REPORTE DE UN EVENTO----------------//
  // Procesa las asistencias registradas en un evento y calcula los valores necesarios para construir el reporte.
  private ReporteEvento generarReporteEvento(Evento evento, int totalMiembros) {
    int presentes = 0;
    int ausentes = 0;

    // Recupera las asistencias registradas para el evento.
    List<Asistencia> asistencias = evento.getAsistencias();

    if (asistencias == null) {
      asistencias = Collections.emptyList();
    }

    // Cuenta presentes y ausentes registrados en el evento.
    for (Asistencia asistencia : asistencias) {
      if (asistencia == null) {
        continue;
      }
      if (asistencia.esPresente()) {
        presentes++;
      }
      if (asistencia.esAusente()) {
        ausentes++;
      }
    }

    int totalRegistros = asistencias.size();

    // Calcula la participación tomando como referencia el total de miembros
    double participacion = calcularParticipacion(presentes, totalMiembros);

    return new ReporteEvento(evento.getNombre(), totalRegistros, presentes, ausentes, participacion, evento);
  }

  //----------------METODO PARA CALCULAR LA PARTICIPACION----------------//
  // Calcula el porcentaje de participación de un evento en función de la cantidad de asistentes y el total de miembros.
  private double calcularParticipacion(int asistentes, int totalMiembros) {
    if (totalMiembros <= 0) {
      return 0;
    }
    return (asistentes * 100.0) / totalMiembros;
  }

  //----------------METODO PARA OBTENER LOS EVENTOS FINALIZADOS----------------//
  // Devuelve los eventos con estado finalizado que se van a utilizar para generar reportes.
  public List<Evento> obtenerEventosFinalizados() {
    return DatosIniciales.eventos.stream().filter(e ->
      "Finalizada".equalsIgnoreCase(e.getEstado())
    ).toList();
  }
}