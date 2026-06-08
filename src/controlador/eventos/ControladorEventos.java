package controlador.eventos;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import datos.DatosIniciales;
import modelo.Evento;

//----------------CONTROLADOR DE EVENTOS----------------//
// Gestiona las operaciones relacionadas con los eventos: creación, consulta, actualización y eliminación.
public class ControladorEventos {
  //----------------METODO PARA OBTENER TODOS LOS EVENTOS----------------//
  // Devuelve la lista completa de eventos registrados en memoria.
  public List<Evento> obtenerEventos() {
    return DatosIniciales.eventos;
  }

  //----------------METODO PARA CREAR UN EVENTO----------------//
  // Valida los datos ingresados, crea un nuevo evento y lo agrega a la colección.
  public Evento crearEvento(String nombre, String descripcion, LocalDate fecha, LocalTime hora, String direccion) {
    validarDatos(nombre, descripcion, fecha, hora, direccion);
    Evento evento = new Evento(generarId(), fecha, hora, direccion, "PROGRAMADO", nombre, descripcion);
    DatosIniciales.eventos.add(evento);
    return evento;
  }

  //----------------METODO PARA ACTUALIZAR UN EVENTO----------------//
  // Reemplaza el evento existente por la versión actualizada, buscando coincidencia por identificador.
  public void actualizarEvento(Evento eventoActualizado) {
    for (int i = 0; i < DatosIniciales.eventos.size(); i++) {
      Evento actual = DatosIniciales.eventos.get(i);
      if (actual.getId() == eventoActualizado.getId()) {
        DatosIniciales.eventos.set(i, eventoActualizado);
        return;
      }
    }
  }

  //----------------METODO PARA ELIMINAR UN EVENTO----------------//
  // Remueve el evento de la colección de eventos registrados.
  public void eliminarEvento(Evento evento) {
    DatosIniciales.eventos.remove(evento);
  }

  //----------------METODO PARA BUSCAR UN EVENTO POR NOMBRE----------------//
  // Retorna el evento cuyo nombre coincida con el valor recibido. Si no existe, devuelve null.
  public Evento buscarEvento(String nombre) {
    for (Evento evento : DatosIniciales.eventos) {
      if (evento.getNombre().equalsIgnoreCase(nombre)) {
        return evento;
      }
    }
    return null;
  }

  //----------------METODO PARA VALIDAR LOS DATOS DEL EVENTO----------------//
  // Verifica que todos los campos obligatorios tengan valores válidos.
  private void validarDatos(String nombre,String descripcion,LocalDate fecha,LocalTime hora,String direccion) {
    if (nombre == null || nombre.isBlank()) {
      throw new IllegalArgumentException("El nombre es obligatorio.");
    }

    if (descripcion == null || descripcion.isBlank()) {
      throw new IllegalArgumentException("La descripción es obligatoria.");
    }

    if (fecha == null) {
      throw new IllegalArgumentException("Debe seleccionar una fecha.");
    }

    if (fecha.isBefore(LocalDate.now())) {
      throw new IllegalArgumentException("La fecha no puede ser anterior a la actual.");
    }

    if (hora == null) {
      throw new IllegalArgumentException("Debe ingresar una hora.");
    }

    if (direccion == null || direccion.isBlank()) {
      throw new IllegalArgumentException("La dirección es obligatoria.");
    }
  }

  //----------------METODO PARA GENERAR EL ID DEL EVENTO----------------//
  // Genera un identificador único tomando el mayor ID existente y sumando una unidad.
  private int generarId() {
    if (DatosIniciales.eventos.isEmpty()) {
      return 1;
    }

    return DatosIniciales.eventos.stream()
      .mapToInt(Evento::getId)
      .max()
      .orElse(0) + 1;
  }
}