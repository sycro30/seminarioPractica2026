package modelo;

import java.time.LocalDate;
import java.time.LocalTime;

public class Evento extends Actividad {
  //--------------ATRIBUTOS----------------//
  private String nombre;
  private String descripcion;

  //--------------METODO CONSTRUCTOR----------------//
  public Evento( int id, LocalDate fechaActividad, LocalTime horaActividad, String direccionActividad, 
    String estado, String nombre, String descripcion
  ) {
    super(id, fechaActividad, horaActividad, direccionActividad, estado);
    this.nombre = nombre;
    this.descripcion = descripcion;
  }

  //--------------METODOS GETTERS PARA CONSULTAR INFORMACION----------------//
  public String getNombre() {
    return nombre;
  }
  public String getDescripcion() {
    return descripcion;
  }

  //--------------METODOS SETTERS PARA MODIFICAR INFORMACION----------------//
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  //METODO PARA MOSTRAR EL NOMBRE DEL EVENTO Y LA FECHA
  @Override
  public String toString() {
    return nombre + " - " + getFechaActividad();
  }
}