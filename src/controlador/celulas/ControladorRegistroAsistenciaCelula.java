package controlador.celulas;

import java.time.LocalDate;
import java.util.List;

import datos.DatosIniciales;
import modelo.Asistencia;
import modelo.Celula;
import modelo.ReunionCelula;
import vista.celulas.RegistroPresencia;

//----------------CONTROLADOR DE REGISTRO DE ASISTENCIA DE CÉLULAS----------------//
// Gestiona la creación de reuniones de célula y el registro de las asistencias de sus miembros.
public class ControladorRegistroAsistenciaCelula {
  //----------------METODO PARA CREAR UNA REUNIÓN DE CÉLULA----------------//
  // Crea una nueva reunión y registra las asistencias de los miembros.
  public void crearReunionCelula(
    Celula celula, LocalDate fecha, String tema, String observaciones, List<RegistroPresencia> registros
  ) {
    validarDatos(celula, fecha, tema, registros);

    // Evitar más de una reunión registrada en la misma semana
    if (existeReunionEnSemana(celula, fecha)) {
      throw new IllegalArgumentException("Ya existe una reunión registrada para esta semana.");
    }

    int idReunion = DatosIniciales.reuniones.size() + 1;

    ReunionCelula reunion = new ReunionCelula( 
      idReunion, fecha, celula.getHora(), celula.getDireccion(), "Realizada", celula, tema, observaciones
    );

    // Registrar reunión en las colecciones correspondientes
    DatosIniciales.reuniones.add(reunion);
    celula.agregarReunion(reunion);

    int idAsistencia = DatosIniciales.asistencias.size() + 1;

    // Registrar asistencia de cada miembro
    for (RegistroPresencia registro : registros) {
      Asistencia asistencia = new Asistencia(
        idAsistencia++,
        registro.getMiembro(),
        reunion,
        LocalDate.now(),              // Fecha de registro
        reunion.getFechaActividad(),  // Fecha de asistencia
        registro.isPresente() ? "Presente" : "Ausente",
        ""
      );

      DatosIniciales.asistencias.add(asistencia);
      reunion.agregarAsistencia(asistencia);
    }
  }

  //----------------METODO PARA VALIDAR LOS DATOS----------------//
  // Verifica que los datos mínimos requeridos sean válidos.
  private void validarDatos(
    Celula celula,
    LocalDate fecha,
    String tema,
    List<RegistroPresencia> registros
  ) {

    if (celula == null) {
      throw new IllegalArgumentException("Debe seleccionar una célula.");
    }

    if (fecha == null) {
      throw new IllegalArgumentException("Debe seleccionar una fecha.");
    }

    if (fecha.isAfter(LocalDate.now())) {
      throw new IllegalArgumentException("No se puede registrar una reunión en una fecha futura.");
    }

    if (tema == null || tema.isBlank()) {
      throw new IllegalArgumentException("Debe ingresar el tema de la reunión.");
    }

    if (registros == null || registros.isEmpty()) {
      throw new IllegalArgumentException("No existen miembros para registrar asistencia.");
    }
  }

  //----------------METODO PARA VERIFICAR REUNIONES DUPLICADAS----------------//
  // Comprueba si la célula ya tiene una reunión registrada durante la misma semana del año.
  private boolean existeReunionEnSemana(Celula celula, LocalDate fecha) {
    for (ReunionCelula reunion : celula.getReuniones()) {
      LocalDate fechaReunion = reunion.getFechaActividad();
      boolean mismaSemana = fechaReunion.getYear() == fecha.getYear() 
        && fechaReunion.get(java.time.temporal.IsoFields.WEEK_OF_WEEK_BASED_YEAR) 
          == fecha.get(java.time.temporal.IsoFields.WEEK_OF_WEEK_BASED_YEAR);
      if (mismaSemana) {
        return true;
      }
    }

    return false;
  }
}