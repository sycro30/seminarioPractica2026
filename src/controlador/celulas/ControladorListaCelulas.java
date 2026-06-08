package controlador.celulas;

import java.time.format.DateTimeFormatter;
import java.util.List;
import datos.DatosIniciales;
import modelo.Celula;
import modelo.ReunionCelula;

//----------------CONTROLADOR DE LISTA DE CÉLULAS----------------//
// Gestiona la obtención de células y la consulta de información relacionada con sus reuniones registradas.
public class ControladorListaCelulas {
  //----------------METODO PARA OBTENER TODAS LAS CÉLULAS----------------//
  // Devuelve la lista completa de células registradas en memoria.
  public List<Celula> obtenerCelulas() {
    return DatosIniciales.celulas;
  }

  //----------------METODO PARA OBTENER LA FECHA DE LA ÚLTIMA REUNIÓN----------------//
  // Retorna la fecha de la reunión más reciente de la célula. Si no existen reuniones registradas, devuelve "Sin registros".
  public String obtenerUltimaFechaReunion(Celula celula) {
    List<ReunionCelula> reuniones = celula.getReuniones();
    if (reuniones.isEmpty()) {
      return "Sin registros";
    }
    ReunionCelula ultima = reuniones.stream().max((r1, r2) ->
      r1.getFechaActividad().compareTo(r2.getFechaActividad())
    ).orElse(null);
    return ultima.getFechaActividad().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
  }

  //----------------METODO PARA OBTENER EL ÚLTIMO TEMA TRATADO----------------//
  // Retorna el tema de la reunión más reciente de la célula. Si no existen reuniones registradas, devuelve "-".
  public String obtenerUltimoTema(Celula celula) {
    List<ReunionCelula> reuniones = celula.getReuniones();
    if (reuniones.isEmpty()) {
      return "-";
    }
    ReunionCelula ultima = reuniones.stream().max((r1, r2) ->
      r1.getFechaActividad().compareTo(r2.getFechaActividad())
    ).orElse(null);
    return ultima.getTema();
  }
  
}