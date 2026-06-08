package modelo;

public class Lider {
  //--------------ATRIBUTOS----------------//
  private int id;
  private Miembro miembro;
  private String rol;

  //--------------METODO CONSTRUCTOR----------------//
  public Lider(int id, Miembro miembro, String rol) {
    this.id = id;
    this.miembro = miembro;
    this.rol = rol;
  }

  //--------------METODOS GETTERS PARA CONSULTAR INFORMACION----------------//
  public int getId() {
      return id;
  }

  public Miembro getMiembro() {
      return miembro;
  }

  public String getRol() {
      return rol;
  }

  //--------------METODOS SETTERS PARA MODIFICAR INFORMACION----------------//
  public void setMiembro(Miembro miembro) {
      this.miembro = miembro;
  }

  public void setRol(String rol) {
      this.rol = rol;
  }

  //--------------METODO PARA CONSULTAR NOMBRE Y APELLIDO DEL LIDER----------------//
  public String getNombreCompleto() {
    if (miembro == null) {
      return "";
    }
    return miembro.getNombre() + " " + miembro.getApellido();
  }

  @Override
  public String toString() {
    return getNombreCompleto();
  }
}