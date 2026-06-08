package vista.reportes;

import controlador.reportes.ControladorBajaAsistencia;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import vista.reportes.celula.VentanaReporteAsistenciaCelulas;
import vista.reportes.evento.VentanaReporteAsistenciaEventos;

//----------------VENTANA PRINCIPAL DE REPORTES----------------//
/* Permite acceder a los distintos reportes disponibles en el sistema: asistencia de células, 
  asistencia de eventos y miembros con baja asistencia. */
public class VentanaPrincipalReportes {
  //--------------ATRIBUTOS----------------//
  private BorderPane root;
  private Button btnCelulas;
  private Button btnEventos;
  private Button btnBajaAsistencia;

  //----------------METODO CONSTRUCTOR----------------//
  // Inicializa la ventana principal de reportes, crea los botones de navegación y muestra por defecto el reporte de asistencia de células.
  public VentanaPrincipalReportes() {
    root = new BorderPane();

    btnCelulas = new Button("Asistencia Células");
    btnEventos = new Button("Asistencia Eventos");
    btnBajaAsistencia = new Button("Baja Asistencia");

    HBox barra = new HBox(10, btnCelulas, btnEventos, btnBajaAsistencia);
    barra.setPadding(new Insets(10));

    root.setTop(barra);
    configurarEventos();
    mostrarReporteCelulas();
  }

  //----------------METODO PARA CONFIGURAR LOS EVENTOS----------------//
  // Asocia las acciones de los botones de navegación con la apertura de cada reporte disponible.
  private void configurarEventos() {
    btnCelulas.setOnAction( e -> mostrarReporteCelulas() );
    btnEventos.setOnAction( e -> mostrarReporteEventos() );
    btnBajaAsistencia.setOnAction( e -> mostrarReporteBajaAsistencia() );
  }

  //----------------METODO PARA MOSTRAR EL REPORTE DE CELULAS----------------//
  // Carga la vista correspondiente al reporte de asistencia de células.
  private void mostrarReporteCelulas() {
    VentanaReporteAsistenciaCelulas vista = new VentanaReporteAsistenciaCelulas();
    root.setCenter(vista.getRoot());
  }

  //----------------METODO PARA MOSTRAR EL REPORTE DE EVENTOS----------------//
  // Carga la vista correspondiente al reporte de asistencia en eventos.
  private void mostrarReporteEventos() {
    VentanaReporteAsistenciaEventos vista = new VentanaReporteAsistenciaEventos();
    root.setCenter(vista.getRoot());
  }

  //----------------METODO PARA MOSTRAR EL REPORTE DE BAJA ASISTENCIA----------------//
  // Carga la vista utilizada para identificar miembros con baja asistencia dentro de un período determinado.
  private void mostrarReporteBajaAsistencia() {
    ControladorBajaAsistencia controlador = new ControladorBajaAsistencia();
    VentanaBajaAsistencia vista = new VentanaBajaAsistencia(controlador);
    root.setCenter(vista.crearVista());
  }

  //----------------METODO GETTER DEL CONTENEDOR PRINCIPAL----------------//
  // Devuelve el contenedor principal de la ventana de reportes.
  public BorderPane getRoot() {
    return root;
  }
}