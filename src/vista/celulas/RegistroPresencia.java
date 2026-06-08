package vista.celulas;

import modelo.Miembro;

//----------------REGISTRO DE PRESENCIA----------------//
// Representa el estado de asistencia de un miembro
// durante el registro de una reunión de célula.
public class RegistroPresencia {
  private Miembro miembro;
  private boolean presente;

  //----------------CONSTRUCTOR----------------//
  // Crea un registro de presencia para el miembro indicado. Por defecto se considera presente.
  public RegistroPresencia(Miembro miembro) {
    this.miembro = miembro;
    this.presente = true;
  }

  //----------------METODOS GETTERS----------------//
  // Devuelve el miembro asociado al registro.
  public Miembro getMiembro() {
    return miembro;
  }

  // Indica si el miembro asistió a la reunión.
  public boolean isPresente() {
    return presente;
  }

  // Actualiza el estado de asistencia del miembro.
  public void setPresente(boolean presente) {
    this.presente = presente;
  }
}