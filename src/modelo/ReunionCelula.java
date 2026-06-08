package modelo;

import java.time.LocalDate;
import java.time.LocalTime;

public class ReunionCelula extends Actividad {
  //--------------ATRIBUTOS----------------//
  private Celula celula;
  private String tema;
  private String observaciones;

  //--------------METODO CONSTRUCTOR----------------//
  public ReunionCelula( int id, LocalDate fechaActividad, LocalTime horaActividad, 
    String direccionActividad, String estado, Celula celula, String tema, String observaciones
  ) {
    super( id, fechaActividad, horaActividad, direccionActividad, estado);
    this.celula = celula;
    this.tema = tema;
    this.observaciones = observaciones;
  }

  //--------------METODO PARA CONSULTAR EL NOMBRE DE LA ACTIVIDAD Y SU FECHA----------------//
  @Override
  public String toString() {
    return "Reunión " + celula.getNombre() + " - " + getFechaActividad();
  }

  //--------------METODOS GETTERS PARA CONSULTAR INFORMACION----------------//
  //Consulta la celula a la que corresponde la reunión
  public Celula getCelula() {
    return celula;
  }

  public String getTema() {
    return tema;
  }

  public String getObservaciones() {
    return observaciones;
  }
}