package modelo;

import java.util.ArrayList;
import java.util.List;

public class Miembro {
  //--------------ATRIBUTOS----------------//
  private int id;
  private String nombre;
  private String apellido;
  private String telefono;
  private String correo;
  private Celula celula;
  private List<Asistencia> asistencias = new ArrayList<>();

  //--------------METODO CONSTRUCTOR----------------//
  public Miembro(int id, String nombre, String apellido, String telefono, String correo, Celula celula) {
    this.id = id;
    this.nombre = nombre;
    this.apellido = apellido;
    this.telefono = telefono;
    this.correo = correo;
    this.celula = celula;
  }
  
  //----------METODOS GETTERS PARA CONSULTAR INFORMACION-----------//
  public int getId() {
    return id;
  }
  public String getNombre() {
    return nombre;
  }
  public String getCorreo() {
    return correo;
  }
  public String getApellido() {
    return apellido;
  }
  public String getTelefono() {
    return telefono;
  }

  //------------METODOS SETTERS PARA MODIFICAR INFORMACION------//
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  public void setApellido(String apellido) {
    this.apellido = apellido;
  }
  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }
  public void setCorreo(String correo) {
    this.correo = correo;
  }

  //-----METODOS PARA CONSULTAR Y CAMBIAR DE CELULA EL MIEMBRO-------//
  public Celula getCelula() {
    return celula;
  }
  public void setCelula(Celula celula) {
    this.celula = celula;
  }

  //METODO PARA AGREGAR ASISTENCIA
  public void agregarAsistencia(Asistencia asistencia) {
    if (!asistencias.contains(asistencia)) {
      asistencias.add(asistencia);
    }
  }
}