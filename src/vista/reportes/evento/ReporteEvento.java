package vista.reportes.evento;

import modelo.Evento;

//----------------CLASE DE TRANSFERENCIA DE DATOS DEL REPORTE----------------//
// Representa la información calculada de un evento que será mostrada en el reporte de asistencia generado por el sistema.
public class ReporteEvento {
  //--------------ATRIBUTOS----------------//
  private String nombreEvento;
  private int totalRegistros;
  private int presentes;
  private int ausentes;
  private double participacion;
  private Evento evento;

  //--------------METODO CONSTRUCTOR----------------//
  // Inicializa los datos calculados del reporte para un evento.
  public ReporteEvento(
    String nombreEvento, int totalRegistros, int presentes, int ausentes, double participacion, Evento evento
  ) {
    this.nombreEvento = nombreEvento;
    this.totalRegistros = totalRegistros;
    this.presentes = presentes;
    this.ausentes = ausentes;
    this.participacion = participacion;
    this.evento = evento;
  }

  //--------------METODOS GETTERS PARA CONSULTAR INFORMACION----------------//
  // Devuelve el nombre del evento analizado.
  public String getNombreEvento() {
    return nombreEvento;
  }

  // Devuelve la cantidad total de registros de asistencia del evento.
  public int getTotalRegistros() {
    return totalRegistros;
  }

  // Devuelve la cantidad de asistentes presentes.
  public int getPresentes() {
    return presentes;
  }

  // Devuelve la cantidad de asistentes ausentes.
  public int getAusentes() {
    return ausentes;
  }

  // Devuelve el porcentaje de participación calculado para el evento.
  public double getParticipacion() {
    return participacion;
  }

  // Devuelve la referencia al evento asociado al reporte.
  public Evento getEvento() {
    return evento;
  }
}
