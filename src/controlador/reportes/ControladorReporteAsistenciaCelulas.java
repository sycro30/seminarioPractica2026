package controlador.reportes;

import vista.reportes.celula.ReporteCelula;
import modelo.Asistencia;
import modelo.Celula;
import modelo.ReunionCelula;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ControladorReporteAsistenciaCelulas {
  //----------------METODO PARA GENERAR EL REPORTE----------------//
  /* 
    Recorre las células seleccionadas y calcula los indicadores requeridos por el reporte:
    - Promedio de asistencia del período 1.
    - Promedio de asistencia del período 2.
    - Variación entre ambos períodos.
    - Nivel de participación.
    Construye una lista de objetos ReporteCelula que será utilizada por la vista para mostrar los resultados.
  */
  public List<ReporteCelula> generarReporte(
    List<Celula> celulas, LocalDate inicioPeriodo1, LocalDate finPeriodo1, LocalDate inicioPeriodo2, LocalDate finPeriodo2
  ) {
    List<ReporteCelula> reporte = new ArrayList<>();
    for (Celula celula : celulas) {
      double promedio1 = calcularPromedioAsistencia(celula, inicioPeriodo1, finPeriodo1);
      double promedio2 = calcularPromedioAsistencia(celula, inicioPeriodo2, finPeriodo2);

      double variacion =calcularVariacion(promedio1, promedio2);;

      double participacion = calcularParticipacion(celula, inicioPeriodo2, finPeriodo2);

      reporte.add(new ReporteCelula(celula, promedio1, promedio2, variacion, participacion));
    }
    return reporte;
  }

  //----------------METODO PARA CALCULAR EL PROMEDIO DE ASISTENCIA----------------//
  // Calcula el promedio de asistentes por reunión para una célula dentro de un período determinado.
  private double calcularPromedioAsistencia(Celula celula, LocalDate inicio, LocalDate fin) {
    int asistentes = 0;
    int reuniones = 0;

    for (ReunionCelula reunion : celula.getReuniones()) {
      if (!reunion.estaEnPeriodo(inicio, fin)) {
        continue;
      }
      reuniones++;
      for (Asistencia asistencia : reunion.getAsistencias()) {
        if (asistencia.esPresente()) {
          asistentes++;
        }
      }
    }

    if (reuniones == 0) {
      return 0;
    }
    return (double) asistentes / reuniones;
  }

  //----------------METODO PARA CALCULAR EL NIVEL DE PARTICIPACION----------------//
  // Calcula el porcentaje de participación de una célula durante un período determinado.
  private double calcularParticipacion(Celula celula, LocalDate inicio, LocalDate fin) {
    int presentes = 0;
    int reuniones = 0;

    for (ReunionCelula reunion : celula.getReuniones()) {
      if (!reunion.estaEnPeriodo(inicio, fin)) {
        continue;
      }
      reuniones++;
      
      for (Asistencia asistencia : reunion.getAsistencias()) {
        if (asistencia.esPresente()) {
          presentes++;
        }
      }
    }

    int totalMiembros = celula.getMiembros().size();
    int totalPosible =totalMiembros * reuniones;

    if (totalPosible == 0) {
      return 0;
    }
    return (presentes * 100.0) / totalPosible;
  }

  private double calcularVariacion(double periodoAnterior, double periodoActual) {
    if (periodoAnterior == 0) {
      return 0;
    }
    return ((periodoActual - periodoAnterior) / periodoAnterior) * 100;
  }
}