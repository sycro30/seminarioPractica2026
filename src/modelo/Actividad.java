package modelo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public abstract class Actividad {
  //---------------ATRIBUTOS---------------//
  private int id;
  private LocalDate fechaActividad;
  private LocalTime horaActividad;
  private String direccionActividad;
  private String estado;
  private final List<Asistencia> asistencias;

  //---------------METODO CONSTRUCTOR---------------//
  public Actividad( int id, LocalDate fechaActividad, LocalTime horaActividad, String direccionActividad, String estado) {
    this.id = id;
    this.fechaActividad = fechaActividad;
    this.horaActividad = horaActividad;
    this.direccionActividad = direccionActividad;
    this.estado = estado;
    this.asistencias = new ArrayList<>();
  }

  //---------------METODOS GETTERS PARA CONSULTAR INFORMACION---------------//
  public int getId() {
    return id;
  }

  public LocalDate getFechaActividad() {
    return fechaActividad;
  }

  public LocalTime getHoraActividad() {
    return horaActividad;
  }

  public String getDireccionActividad() {
    return direccionActividad;
  }

  public String getEstado() {
    return estado;
  }

    //---------------METODO SETTER PARA MODIFICAR INFORMACION---------------//
  public void setEstado(String estado) {
    this.estado = estado;
  }

    //---METODO PARA VERIFICAR QUE LA ACTIVIDAD SE ENCUENTRA DENTRO DEL RANGO DE FECHAS---//
  public boolean estaEnPeriodo(LocalDate fechaInicio, LocalDate fechaFin) {
    return !fechaActividad.isBefore(fechaInicio) && !fechaActividad.isAfter(fechaFin);
  }

  //-----METODO PARA DEVOLVER LA LISTA DE ASISTENCIA------//
  public List<Asistencia> getAsistencias() {
    return asistencias;
  }

  //-------METODO PARA AGREGAR UNA ASISTENCIA----------//
  public void agregarAsistencia(Asistencia asistencia) {
    if (!asistencias.contains(asistencia)) {
        asistencias.add(asistencia);
    }
  }
}