package controlador.historialAsistencias;

import java.util.ArrayList;
import java.util.List;

import datos.DatosIniciales;
import modelo.Celula;
import modelo.Evento;
import modelo.ReunionCelula;

//----------------CONTROLADOR DE HISTORIAL DE ASISTENCIAS----------------//
// Proporciona acceso a la información necesaria para consultar el historial de reuniones de células y eventos registrados.
public class ControladorHistorialAsistencias {

  //----------------OBTENER CÉLULAS----------------//
  // Devuelve la lista de células registradas en el sistema.
  public List<Celula> obtenerCelulas() {
    return DatosIniciales.celulas;
  }

  //----------------OBTENER EVENTOS----------------//
  // Devuelve la lista de eventos registrados en el sistema.
  public List<Evento> obtenerEventos() {
    return DatosIniciales.eventos;
  }

  //----------------OBTENER REUNIONES DE UNA CÉLULA----------------//
  // Filtra y devuelve las reuniones asociadas a la célula seleccionada.
  public List<ReunionCelula> obtenerReunionesDeCelula(Celula celula) {
    List<ReunionCelula> reuniones = new ArrayList<>();

    for (ReunionCelula reunion : DatosIniciales.reuniones) {
      if (reunion.getCelula().equals(celula)) {
        reuniones.add(reunion);
      }
    }

    return reuniones;
  }
}