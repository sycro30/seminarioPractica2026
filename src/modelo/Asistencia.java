package modelo;

import java.time.LocalDate;

public class Asistencia {
  //-------------ATRIBUTOS------------//
  private int id;
  private Miembro miembro;
  private Actividad actividad;
  private LocalDate fechaRegistro;
  private LocalDate fechaAsistencia;
  private String estado;
  private String observaciones;

    //-------------METODO CONSTRUCTOR------------//
  public Asistencia(int id, Miembro miembro, Actividad actividad, LocalDate fechaRegistro, 
    LocalDate fechaAsistencia, String estado, String observaciones
  ) {
    this.id = id;
    this.miembro = miembro;
    this.actividad = actividad;
    this.fechaRegistro = fechaRegistro;
    this.fechaAsistencia = fechaAsistencia;
    this.estado = estado;
    this.observaciones = observaciones;

    //Agrega la asistencia a la actividad
    if (actividad != null) {
      actividad.agregarAsistencia(this);
    }

    //agrega la asistencia al miembro
    if (miembro != null) {
      miembro.agregarAsistencia(this);
    }
  }

  //METODOS GETTERS PARA CONSULTAR INFORMACIÓN
  public int getId() {
    return id;
  }

  public Miembro getMiembro() {
    return miembro;
  }

  public Actividad getActividad() {
    return actividad;
  }

  public LocalDate getFechaRegistro() {
    return fechaRegistro;
  }

  public LocalDate getFechaAsistencia() {
    return fechaAsistencia;
  }

  public String getEstado() {
    return estado;
  }

  public String getObservaciones() {
    return observaciones;
  }

  //METODOS SETTERS
  public void setEstado(String estado) {
    this.estado = estado;
  }

  public void setObservaciones(String observaciones) {
    this.observaciones = observaciones;
  }

  //METODOS DE CONSULTA DEL ESTADO DE ASISTENCIA (ignorando mayusculas y minusculas)
  public boolean esPresente() {
    return "Presente".equalsIgnoreCase(estado);
  }

  public boolean esAusente() {
    return "Ausente".equalsIgnoreCase(estado);
  }

  //METODO PARA MOSTRAR EL NOMBRE DEL MIEMBRO, SU ESTADO (PRESENTE O AUSENTE) Y LA FECHA DE LA ASISTENCIA
  @Override
  public String toString() {
    String nombreMiembro = (miembro != null) ? miembro.getNombre() + " " + miembro.getApellido() : "Sin miembro";
    return nombreMiembro + " - " + estado + " - " + fechaAsistencia;
  }
}