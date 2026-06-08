package modelo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Celula {
  //-------------ATRIBUTOS------------//
  private int id;
  private String nombre;
  private LocalDate fecha;
  private LocalTime hora;
  private String direccion;
  private Lider lider;
  private List<ReunionCelula> reuniones = new ArrayList<>();
  private List<Miembro> miembros = new ArrayList<>();

    //-------------METODO CONSTRUCTOR------------//
  public Celula(int id, String nombre, LocalDate fecha, LocalTime hora, String direccion, Lider lider) {
    this.id = id;
    this.nombre = nombre;
    this.fecha = fecha;
    this.hora = hora;
    this.direccion = direccion;
    this.lider = lider;
  }

  //-------------METODOS GETTERS PARA CONSULTAR INFORMACION------------//
  public int getId() {
    return id;
  }

  public String getNombre() {
    return nombre;
  }
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public LocalDate getFecha() {
    return fecha;
  }
  public void setFecha(LocalDate fecha) {
    this.fecha = fecha;
  }

  public LocalTime getHora() {
    return hora;
  }
  public void setHora(LocalTime hora) {
    this.hora = hora;
  }

  public String getDireccion() {
    return direccion;
  }
  public void setDireccion(String direccion) {
    this.direccion = direccion;
  }

  public Lider getLider() {
    return lider;
  }
  public void setLider(Lider lider) {
    this.lider = lider;
  }

  //-------------METODO PARA DEVOLVER LISTA MIEMBROS------------//
  public List<Miembro> getMiembros() {
    return miembros;
  }

  //-------------METODO PARA DEVOLVER LISTA DE REUNIONES DE CELULA------------//
  public List<ReunionCelula> getReuniones() {
    return reuniones;
  }

  //-------------METODO PARA AGREGAR MIEMBROS A LA CELULA------------//
  public void agregarMiembro(Miembro miembro) {
    if (!miembros.contains(miembro)) {
      miembros.add(miembro);
      miembro.setCelula(this);
    }
  }

  //-------------METODO PARA REMOVER MIEMBRO DE UNA CELULA------------//
  public void removerMiembro(Miembro miembro) {
    miembros.remove(miembro);

    if (miembro.getCelula() == this) {
        miembro.setCelula(null);
    }
  }

  //-------------METODO PARA AGREGAR REUNIONES A UNA CELULA------------//
  public void agregarReunion(ReunionCelula reunion) {
    if (!reuniones.contains(reunion)) {
        reuniones.add(reunion);
    }
  }

  //---- METODO PARA DEVOLVER EL NOMBRE DE LA CÉLULA COMO REPRESENTACIÓN DEL OBJETO ----//
  @Override
  public String toString() {
    return nombre;
  }
}