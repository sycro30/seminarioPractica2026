package controlador.reportes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import datos.DatosIniciales;
import modelo.Asistencia;
import modelo.Miembro;

//----------------CONTROLADOR DE BAJA ASISTENCIA----------------//
// Gestiona la búsqueda de miembros cuyo porcentaje de asistencia se encuentra por debajo de un umbral determinado dentro de un período.
public class ControladorBajaAsistencia {
  private double valorMinimo;
  private LocalDate fechaInicio;
  private LocalDate fechaFin;

    //----------------BUSCAR MIEMBROS CON BAJA ASISTENCIA----------------//
  /* Recorre todos los miembros y calcula su porcentaje de asistencia dentro del período indicado. 
  Devuelve únicamente aquellos cuyo porcentaje es menor al valor mínimo establecido. */
  public List<Miembro> buscarMiembrosBajaAsistencia(LocalDate fechaInicio, LocalDate fechaFin, double valorMinimo) {
    this.fechaInicio = fechaInicio;
    this.fechaFin = fechaFin;
    this.valorMinimo = valorMinimo;

    List<Miembro> resultado = new ArrayList<>();
    for (Miembro miembro : obtenerMiembros()) {
      List<Asistencia> asistenciasMiembro = obtenerRegistrosDeMiembro(miembro, fechaInicio, fechaFin);

      if (asistenciasMiembro.isEmpty()) {
        continue;
      }

      int totalAsistencias = contarAsistencias(asistenciasMiembro);
      int totalReuniones = contarTotalReuniones(asistenciasMiembro);
      double porcentaje = calcularPorcentajeAsistencia(totalAsistencias, totalReuniones);
      if (esBajaAsistencia(porcentaje, valorMinimo)) {
        resultado.add(miembro);
      }
    }
    return resultado;
  }

  //METODO PARA OBTENER TODOS LOS MIEMBROS
  // Actualmente los miembros se obtienen desde una colección en memoria.
  public List<Miembro> obtenerMiembros() {
    return DatosIniciales.miembros;
  }

  //----------------OBTENER REGISTROS DE UN MIEMBRO----------------//
  // Filtra las asistencias correspondientes al miembro indicado dentro del período seleccionado.
  public List<Asistencia> obtenerRegistrosDeMiembro(Miembro miembro, LocalDate fechaInicio, LocalDate fechaFin) {
    return DatosIniciales.asistencias.stream().filter(a -> 
      a.getMiembro().equals(miembro) && a.getActividad().estaEnPeriodo(fechaInicio, fechaFin)
    ).collect(Collectors.toList());
  }

  //----------------CALCULAR PORCENTAJE DE ASISTENCIA----------------//
  // Calcula el porcentaje de asistencia en función de la cantidad de asistencias registradas y el total de reuniones.
  public double calcularPorcentajeAsistencia(int totalAsistencias, int totalReuniones) {
    if (totalReuniones == 0) {
      return 0;
    }
    return ((double) totalAsistencias / totalReuniones) * 100;
  }

  //----------------CONTAR TOTAL DE REUNIONES----------------//
  // Devuelve la cantidad total de registros considerados.
  public int contarTotalReuniones(List<Asistencia> registros) {
    return registros.size();
  }

  //----------------CONTAR ASISTENCIAS----------------//
  // Cuenta únicamente los registros marcados como presentes.
  public int contarAsistencias(List<Asistencia> registros) {
    return (int) registros.stream().filter(Asistencia::esPresente).count();
  }

  //----------------VALIDAR BAJA ASISTENCIA----------------//
  // Determina si el porcentaje calculado es inferior al umbral mínimo.
  public boolean esBajaAsistencia(double porcentaje, double valorMinimo) {
    return porcentaje < valorMinimo;
  }
}