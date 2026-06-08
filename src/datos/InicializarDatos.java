package datos;

import modelo.Asistencia;
import modelo.Celula;
import modelo.Evento;
import modelo.Lider;
import modelo.Miembro;
import modelo.ReunionCelula;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class InicializarDatos {
  public static List<Miembro> miembros = new ArrayList<>();
  public static List<Lider> lideres = new ArrayList<>();
  public static List<Celula> celulas = new ArrayList<>();
  public static List<Evento> eventos = new ArrayList<>();
  public static List<ReunionCelula> reuniones = new ArrayList<>();
  public static List<Asistencia> asistencias = new ArrayList<>();
  
  public static void cargarDatos() {
// =====================================
// MIEMBROS
// =====================================

    Miembro juan =
      new Miembro(
        1,
        "Juan",
        "Perez",
        "2901123456",
        "juan@gmail.com",
        null
      );

    Miembro maria =
      new Miembro(
        2,
        "Maria",
        "Gomez",
        "2901123457",
        "maria@gmail.com",
        null
      );

    Miembro carlos =
      new Miembro(
        3,
        "Carlos",
        "Ruiz",
        "2901123001",
        "carlos@gmail.com",
        null
      );

    Miembro lucia =
      new Miembro(
        4,
        "Lucia",
        "Fernandez",
        "2901123002",
        "lucia@gmail.com",
        null
      );

    Miembro diego =
      new Miembro(
        5,
        "Diego",
        "Ramirez",
        "2901123003",
        "diego@gmail.com",
        null
      );

    Miembro valentina =
      new Miembro(
        6,
        "Valentina",
        "Castro",
        "2901123004",
        "vale@gmail.com",
        null
      );

    DatosIniciales.miembros.add(juan);
    DatosIniciales.miembros.add(maria);
    DatosIniciales.miembros.add(carlos);
    DatosIniciales.miembros.add(lucia);
    DatosIniciales.miembros.add(diego);
    DatosIniciales.miembros.add(valentina);

// =====================================
// LIDER
// =====================================

    Lider lider1 =
      new Lider(
        1,
        juan,
        "Lider de Celula"
      );

    Lider lider2 =
      new Lider(
        2,
        maria,
        "Lider de Celula"
      );

    lideres.add(lider1);
    lideres.add(lider2);

// =====================================
// CELULA
// =====================================

    
    Celula celulaAdultos =
      new Celula(
        1,
        "Celula Adultos",
        LocalDate.of(2026, 3, 1),
        LocalTime.of(19, 0),
        "San Martin 450",
        lider1
      );

    Celula celulaJovenes =
      new Celula(
        2,
        "Celula Jovenes",
        LocalDate.of(2026, 3, 1),
        LocalTime.of(20, 0),
        "Belgrano 890",
        lider2
      );
    celulaAdultos.agregarMiembro(juan);
    celulaJovenes.agregarMiembro(maria);
    DatosIniciales.celulas.add(celulaJovenes);
    DatosIniciales.celulas.add(celulaAdultos);

    //---------------------------------------------
    // ASIGNAR MIEMBROS A CELULAS
    //---------------------------------------------

    celulaAdultos.agregarMiembro(juan);
    celulaAdultos.agregarMiembro(carlos);
    celulaAdultos.agregarMiembro(lucia);

    celulaJovenes.agregarMiembro(maria);
    celulaJovenes.agregarMiembro(diego);
    celulaJovenes.agregarMiembro(valentina);

// =====================================
// REUNIONES
// =====================================
    //---------------------------------------------
    // REUNIONES CELULA ADULTOS
    //---------------------------------------------

    ReunionCelula reunion1 =
      new ReunionCelula(
        1,
        LocalDate.of(2026, 3, 3),
        LocalTime.of(19, 0),
        "San Martin 450",
        "Finalizada",
        celulaAdultos,
        "Fe y perseverancia",
        "Reunion semanal"
      );

    ReunionCelula reunion2 =
      new ReunionCelula(
        2,
        LocalDate.of(2026, 3, 10),
        LocalTime.of(19, 0),
        "San Martin 450",
        "Finalizada",
        celulaAdultos,
        "Oracion",
        "Reunion semanal"
      );

    ReunionCelula reunion3 =
      new ReunionCelula(
        3,
        LocalDate.of(2026, 3, 17),
        LocalTime.of(19, 0),
        "San Martin 450",
        "Finalizada",
        celulaAdultos,
        "Amor del padre",
        "Reunion semanal"
      );

    ReunionCelula reunion4 =
      new ReunionCelula(
        4,
        LocalDate.of(2026, 3, 24),
        LocalTime.of(19, 0),
        "San Martin 450",
        "Finalizada",
        celulaAdultos,
        "La gran comision",
        "Reunion semanal"
      );

    //---------------------------------------------
    // REUNIONES CELULA JOVENES
    //---------------------------------------------

    ReunionCelula reunion5 =
      new ReunionCelula(
        5,
        LocalDate.of(2026, 3, 4),
        LocalTime.of(20, 0),
        "Belgrano 890",
        "Finalizada",
        celulaJovenes,
        "Comunion",
        "Reunion semanal"
      );

    ReunionCelula reunion6 =
      new ReunionCelula(
        6,
        LocalDate.of(2026, 3, 11),
        LocalTime.of(20, 0),
        "Belgrano 890",
        "Finalizada",
        celulaJovenes,
        "Servicio",
        "Reunion semanal"
      );

    ReunionCelula reunion7 =
      new ReunionCelula(
        7,
        LocalDate.of(2026, 3, 18),
        LocalTime.of(20, 0),
        "Belgrano 890",
        "Finalizada",
        celulaJovenes,
        "Gratitud",
        "Reunion semanal"
      );

    ReunionCelula reunion8 =
      new ReunionCelula(
        8,
        LocalDate.of(2026, 3, 25),
        LocalTime.of(20, 0),
        "Belgrano 890",
        "Finalizada",
        celulaJovenes,
        "Compromiso",
        "Reunion semanal"
      );

    celulaAdultos.agregarReunion(reunion1);
    celulaAdultos.agregarReunion(reunion2);
    celulaAdultos.agregarReunion(reunion3);
    celulaAdultos.agregarReunion(reunion4);

    celulaJovenes.agregarReunion(reunion5);
    celulaJovenes.agregarReunion(reunion6);
    celulaJovenes.agregarReunion(reunion7);
    celulaJovenes.agregarReunion(reunion8);

    DatosIniciales.reuniones.add(reunion1);
    DatosIniciales.reuniones.add(reunion2);
    DatosIniciales.reuniones.add(reunion3);
    DatosIniciales.reuniones.add(reunion4);

    DatosIniciales.reuniones.add(reunion5);
    DatosIniciales.reuniones.add(reunion6);
    DatosIniciales.reuniones.add(reunion7);
    DatosIniciales.reuniones.add(reunion8);

// =====================================
// EVENTOS
// =====================================

    Evento evento1 =
      new Evento(
        9,
        LocalDate.of(2026, 3, 14),
        LocalTime.of(18, 0),
        "Abel Cardenas 196",
        "Finalizada",
        "Seminario de Liderazgo",
        "Capacitacion para lideres"
      );

    Evento evento2 =
      new Evento(
        10,
        LocalDate.of(2026, 3, 28),
        LocalTime.of(17, 0),
        "Abel Cardenas 196",
        "Finalizada",
        "Encuentro Familiar",
        "Evento general de integracion"
      );

    DatosIniciales.eventos.add(evento1);
    DatosIniciales.eventos.add(evento2);
// =====================================
// ASISTENCIAS
// =====================================

    // ================================
// ASISTENCIAS CÉLULA ADULTOS
// ================================

  Asistencia asistencia1 = new Asistencia(
    1,
    juan,
    reunion1,
    LocalDate.of(2026, 3, 3),
    LocalDate.of(2026, 3, 3),
    "Presente",
    ""
  );

  Asistencia asistencia2 = new Asistencia(
    2,
    carlos,
    reunion1,
    LocalDate.of(2026, 3, 3),
    LocalDate.of(2026, 3, 3),
    "Presente",
    ""
  );

  Asistencia asistencia3 = new Asistencia(
    3,
    lucia,
    reunion1,
    LocalDate.of(2026, 3, 3),
    LocalDate.of(2026, 3, 3),
    "Presente",
    ""
  );

  Asistencia asistencia4 = new Asistencia(
    4,
    juan,
    reunion2,
    LocalDate.of(2026, 3, 10),
    LocalDate.of(2026, 3, 10),
    "Presente",
    ""
  );

  Asistencia asistencia5 = new Asistencia(
    5,
    carlos,
    reunion2,
    LocalDate.of(2026, 3, 10),
    LocalDate.of(2026, 3, 10),
    "Ausente",
    ""
  );

  Asistencia asistencia6 = new Asistencia(
    6,
    lucia,
    reunion2,
    LocalDate.of(2026, 3, 10),
    LocalDate.of(2026, 3, 10),
    "Presente",
    ""
  );

  Asistencia asistencia7 = new Asistencia(
    7,
    juan,
    reunion3,
    LocalDate.of(2026, 3, 17),
    LocalDate.of(2026, 3, 17),
    "Presente",
    ""
  );

  Asistencia asistencia8 = new Asistencia(
    8,
    carlos,
    reunion3,
    LocalDate.of(2026, 3, 17),
    LocalDate.of(2026, 3, 17),
    "Ausente",
    ""
  );

  Asistencia asistencia9 = new Asistencia(
    9,
    lucia,
    reunion3,
    LocalDate.of(2026, 3, 17),
    LocalDate.of(2026, 3, 17),
    "Presente",
    ""
  );

  Asistencia asistencia10 = new Asistencia(
    10,
    juan,
    reunion4,
    LocalDate.of(2026, 3, 24),
    LocalDate.of(2026, 3, 24),
    "Presente",
    ""
  );

  Asistencia asistencia11 = new Asistencia(
    11,
    carlos,
    reunion4,
    LocalDate.of(2026, 3, 24),
    LocalDate.of(2026, 3, 24),
    "Presente",
    ""
  );

  Asistencia asistencia12 = new Asistencia(
    12,
    lucia,
    reunion4,
    LocalDate.of(2026, 3, 24),
    LocalDate.of(2026, 3, 24),
    "Ausente",
    ""
  );

  // ================================
  // ASISTENCIAS CÉLULA JÓVENES
  // ================================

  Asistencia asistencia13 = new Asistencia(
    13,
    maria,
    reunion5,
    LocalDate.of(2026, 3, 4),
    LocalDate.of(2026, 3, 4),
    "Presente",
    ""
  );

  Asistencia asistencia14 = new Asistencia(
    14,
    diego,
    reunion5,
    LocalDate.of(2026, 3, 4),
    LocalDate.of(2026, 3, 4),
    "Presente",
    ""
  );

  Asistencia asistencia15 = new Asistencia(
    15,
    valentina,
    reunion5,
    LocalDate.of(2026, 3, 4),
    LocalDate.of(2026, 3, 4),
    "Ausente",
    ""
  );

  Asistencia asistencia16 = new Asistencia(
    16,
    maria,
    reunion6,
    LocalDate.of(2026, 3, 11),
    LocalDate.of(2026, 3, 11),
    "Presente",
    ""
  );

  Asistencia asistencia17 = new Asistencia(
    17,
    diego,
    reunion6,
    LocalDate.of(2026, 3, 11),
    LocalDate.of(2026, 3, 11),
    "Ausente",
    ""
  );

  Asistencia asistencia18 = new Asistencia(
    18,
    valentina,
    reunion6,
    LocalDate.of(2026, 3, 11),
    LocalDate.of(2026, 3, 11),
    "Ausente",
    ""
  );

  Asistencia asistencia19 = new Asistencia(
    19,
    maria,
    reunion7,
    LocalDate.of(2026, 3, 18),
    LocalDate.of(2026, 3, 18),
    "Presente",
    ""
  );

  Asistencia asistencia20 = new Asistencia(
    20,
    diego,
    reunion7,
    LocalDate.of(2026, 3, 18),
    LocalDate.of(2026, 3, 18),
    "Presente",
    ""
  );

  Asistencia asistencia21 = new Asistencia(
    21,
    valentina,
    reunion7,
    LocalDate.of(2026, 3, 18),
    LocalDate.of(2026, 3, 18),
    "Ausente",
    ""
  );

  Asistencia asistencia22 = new Asistencia(
    22,
    maria,
    reunion8,
    LocalDate.of(2026, 3, 25),
    LocalDate.of(2026, 3, 25),
    "Presente",
    ""
  );

  Asistencia asistencia23 = new Asistencia(
    23,
    diego,
    reunion8,
    LocalDate.of(2026, 3, 25),
    LocalDate.of(2026, 3, 25),
    "Ausente",
    ""
  );

  Asistencia asistencia24 = new Asistencia(
    24,
    valentina,
    reunion8,
    LocalDate.of(2026, 3, 25),
    LocalDate.of(2026, 3, 25),
    "Presente",
    ""
  );

  // ================================
  // ASISTENCIAS EVENTOS
  // ================================

  Asistencia asistencia25 = new Asistencia(
    25,
    juan,
    evento1,
    LocalDate.of(2026, 3, 14),
    LocalDate.of(2026, 3, 14),
    "Presente",
    ""
  );

  Asistencia asistencia26 = new Asistencia(
    26,
    maria,
    evento1,
    LocalDate.of(2026, 3, 14),
    LocalDate.of(2026, 3, 14),
    "Presente",
    ""
  );

  Asistencia asistencia27 = new Asistencia(
    27,
    lucia,
    evento1,
    LocalDate.of(2026, 3, 14),
    LocalDate.of(2026, 3, 14),
    "Presente",
    ""
  );

  Asistencia asistencia28 = new Asistencia(
    28,
    juan,
    evento2,
    LocalDate.of(2026, 3, 28),
    LocalDate.of(2026, 3, 28),
    "Presente",
    ""
  );

  Asistencia asistencia29 = new Asistencia(
    29,
    maria,
    evento2,
    LocalDate.of(2026, 3, 28),
    LocalDate.of(2026, 3, 28),
    "Presente",
    ""
  );

  Asistencia asistencia30 = new Asistencia(
    30,
    lucia,
    evento2,
    LocalDate.of(2026, 3, 28),
    LocalDate.of(2026, 3, 28),
    "Presente",
    ""
  );

  Asistencia asistencia31 = new Asistencia(
    31,
    diego,
    evento2,
    LocalDate.of(2026, 3, 28),
    LocalDate.of(2026, 3, 28),
    "Presente",
    ""
  );

    DatosIniciales.asistencias.add(asistencia1);
    DatosIniciales.asistencias.add(asistencia2);
    DatosIniciales.asistencias.add(asistencia3);
    DatosIniciales.asistencias.add(asistencia4);
    DatosIniciales.asistencias.add(asistencia5);
    DatosIniciales.asistencias.add(asistencia6);
    DatosIniciales.asistencias.add(asistencia7);
    DatosIniciales.asistencias.add(asistencia8);
    DatosIniciales.asistencias.add(asistencia9);
    DatosIniciales.asistencias.add(asistencia10);
    DatosIniciales.asistencias.add(asistencia11);
    DatosIniciales.asistencias.add(asistencia12);
    DatosIniciales.asistencias.add(asistencia13);
    DatosIniciales.asistencias.add(asistencia14);
    DatosIniciales.asistencias.add(asistencia15);
    DatosIniciales.asistencias.add(asistencia16);
    DatosIniciales.asistencias.add(asistencia17);
    DatosIniciales.asistencias.add(asistencia18);
    DatosIniciales.asistencias.add(asistencia19);
    DatosIniciales.asistencias.add(asistencia20);
    DatosIniciales.asistencias.add(asistencia21);
    DatosIniciales.asistencias.add(asistencia22);
    DatosIniciales.asistencias.add(asistencia23);
    DatosIniciales.asistencias.add(asistencia24);
    DatosIniciales.asistencias.add(asistencia25);
    DatosIniciales.asistencias.add(asistencia26);
    DatosIniciales.asistencias.add(asistencia27);
    DatosIniciales.asistencias.add(asistencia28);
    DatosIniciales.asistencias.add(asistencia29);
    DatosIniciales.asistencias.add(asistencia30);
    DatosIniciales.asistencias.add(asistencia31);
  }
}
