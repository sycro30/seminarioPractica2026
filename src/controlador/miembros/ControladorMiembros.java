package controlador.miembros;

import java.util.List;
import datos.DatosIniciales;
import modelo.Celula;
import modelo.Miembro;

//----------------CONTROLADOR DE MIEMBROS----------------//
/* Gestiona las operaciones de consulta, alta, modificación y baja de miembros. También valida los datos ingresados y 
  mantiene la consistencia entre miembros y células. */
public class ControladorMiembros {
  // Identificador autoincremental para nuevos miembros
  private static int siguienteIdMiembro = 1;

  //----------------METODO PARA OBTENER LOS MIEMBROS----------------//
  // Devuelve la lista completa de miembros registrados en el sistema.
  public List<Miembro> obtenerMiembros() {
    return DatosIniciales.miembros;
  }

  //----------------METODO PARA AGREGAR UN MIEMBRO----------------//
  // Valida los datos recibidos, crea una nueva instancia de Miembro y la registra en el sistema.
  public void agregarMiembro(String nombre, String apellido, String telefono, String correo, Celula celula) {
    validarDatos(nombre, apellido, telefono, correo, celula);
    Miembro miembro = new Miembro(generarIdMiembro(), nombre, apellido, telefono, correo, celula);
    DatosIniciales.miembros.add(miembro);
    if (celula != null) {
      celula.agregarMiembro(miembro);
    }
  }

  //----------------METODO PARA MODIFICAR UN MIEMBRO----------------//
  // Actualiza los datos de un miembro existente y gestiona el cambio de célula en caso de corresponder.
  public void modificarMiembro(Miembro miembro, String nombre, String apellido, String telefono, String correo, Celula nuevaCelula) {
    if (miembro == null) {
      throw new IllegalArgumentException("Debe seleccionar un miembro.");
    }
    validarDatos(nombre, apellido, telefono, correo, nuevaCelula);
    Celula celulaAnterior = miembro.getCelula();

    // Remueve al miembro de la célula anterior si cambió
    if (celulaAnterior != null && celulaAnterior != nuevaCelula) {
      celulaAnterior.removerMiembro(miembro);
    }

    // Actualiza los datos
    miembro.setNombre(nombre);
    miembro.setApellido(apellido);
    miembro.setTelefono(telefono);
    miembro.setCorreo(correo);

    // Agrega el miembro a la nueva célula
    if (nuevaCelula != null && nuevaCelula != celulaAnterior) {
      nuevaCelula.agregarMiembro(miembro);
    }

    miembro.setCelula(nuevaCelula);
  }

  //----------------METODO PARA ELIMINAR UN MIEMBRO----------------//
  // Elimina un miembro del sistema. Si el miembro es líder de una célula, elimina previamente dicha asignación.
  public void eliminarMiembro(Miembro miembro) {
    if (miembro == null) {
      throw new IllegalArgumentException("Debe seleccionar un miembro.");
    }
    Celula celula = miembro.getCelula();

    if (celula != null) {

      // Si el miembro es líder de la célula, elimina la referencia
      if (celula.getLider() != null && celula.getLider().getMiembro() == miembro) {
        celula.setLider(null);
      }

      // Remueve al miembro de la célula
      celula.removerMiembro(miembro);
    }

    DatosIniciales.miembros.remove(miembro);
  }

  //----------------METODO PARA VALIDAR DATOS----------------//
  // Verifica que los datos obligatorios del miembro sean válidos antes de realizar una operación de alta o modificación.
  private void validarDatos(String nombre, String apellido, String telefono, String correo, Celula celula) {
    if (nombre == null || nombre.isBlank()) {
      throw new IllegalArgumentException("El nombre es obligatorio.");
    }

    if (apellido == null || apellido.isBlank()) {
      throw new IllegalArgumentException("El apellido es obligatorio.");
    }

    if (telefono == null || telefono.isBlank()) {
      throw new IllegalArgumentException("El teléfono es obligatorio.");
    }

    if (!telefono.matches("\\d+")) {
      throw new IllegalArgumentException("El teléfono debe contener solo números.");
    }

    if (correo == null || correo.isBlank()) {
      throw new IllegalArgumentException("El correo es obligatorio.");
    }

    if (!correo.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
      throw new IllegalArgumentException("Debe ingresar un correo electrónico válido.");
    }

    if (celula == null) {
      throw new IllegalArgumentException("Debe seleccionar una célula.");
    }
  }

  //----------------METODO PARA GENERAR ID----------------//
  //Genera un identificador único para cada nuevo miembro.
  private int generarIdMiembro() {
    return siguienteIdMiembro++;
  }
}