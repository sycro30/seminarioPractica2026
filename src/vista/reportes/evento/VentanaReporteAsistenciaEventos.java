package vista.reportes.evento;

import controlador.reportes.ControladorReporteAsistenciaEventos;
import datos.DatosIniciales;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import modelo.Evento;

import java.util.ArrayList;
import java.util.List;

public class VentanaReporteAsistenciaEventos {
  //-----------ATRIBUTOS--------------//
  private BorderPane root;
  private VBox contenedorEventos;
  private Button btnProcesar;
  private Button btnVerDetalle;
  private TableView<ReporteEvento> tabla;
  private Label lblEventosAnalizados;
  private Label lblTotalAsistencias;
  private Label lblPromedioAsistencia;
  private Label lblParticipacionPromedio;
  private ControladorReporteAsistenciaEventos controlador;

  //----------------METODO CONSTRUCTOR DE LA VISTA-----------------//
  // Inicializa los componentes, la tabla de resultados, los eventos de la interfaz y la estructura visual de la ventana.
  public VentanaReporteAsistenciaEventos() {
    controlador = new ControladorReporteAsistenciaEventos();
    root = new BorderPane();
    crearControles();
    crearTabla();
    configurarEventos();
    construirVista();
  }

  //----------------METODO PARA CREAR LOS CONTROLES DE LA VISTA----------------//
  /* Inicializa los componentes de la interfaz, incluyendo la lista de eventos disponibles para el reporte, 
     los botones de acción y las etiquetas que muestran los indicadores del reporte. */
  private void crearControles() {
    contenedorEventos = new VBox(8);
    for (Evento evento : controlador.obtenerEventosFinalizados()) {
      CheckBox chkEvento = new CheckBox(evento.toString());
      chkEvento.setUserData(evento);
      contenedorEventos.getChildren().add(chkEvento);
    }

    btnProcesar = new Button("Generar Reporte");
    btnProcesar.setPrefWidth(180);
    btnVerDetalle = new Button("Ver detalle");

    lblEventosAnalizados = new Label("0");
    lblTotalAsistencias = new Label("0");
    lblPromedioAsistencia = new Label("0");

    lblParticipacionPromedio = new Label("0%");
  }

  //----------------METODO PARA CREAR LA TABLA DE RESULTADOS----------------//
  /* Configura la tabla donde se mostrarán los indicadores calculados para cada evento seleccionado, 
  definiendo sus columnas y propiedades. */
  private void crearTabla() {
    tabla = new TableView<>();
    tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    TableColumn<ReporteEvento, String> colEvento = new TableColumn<>("Evento");
    colEvento.setCellValueFactory(d -> 
      new SimpleStringProperty(d.getValue().getNombreEvento())
    );

    TableColumn<ReporteEvento, Number> colTotal = new TableColumn<>("Total registros");
    colTotal.setCellValueFactory(d ->
      new SimpleIntegerProperty(d.getValue().getTotalRegistros())
    );

    TableColumn<ReporteEvento, Number> colPresentes = new TableColumn<>("Asistentes");
    colPresentes.setCellValueFactory(d ->
      new SimpleIntegerProperty(d.getValue().getPresentes())
    );
    
    TableColumn<ReporteEvento, Number> colAusentes = new TableColumn<>("Ausentes");
    colAusentes.setCellValueFactory(d ->
      new SimpleIntegerProperty(d.getValue().getAusentes())
    );

    TableColumn<ReporteEvento, Number> colParticipacion = new TableColumn<>("Participación %");
    colParticipacion.setCellValueFactory(d ->
      new SimpleDoubleProperty(d.getValue().getParticipacion())
    );

    tabla.getColumns().addAll(colEvento, colTotal, colPresentes, colAusentes, colParticipacion);
    tabla.setPlaceholder(new Label("Seleccione eventos y genere un reporte"));
  }

  //----------------METODO PARA CONFIGURAR LOS EVENTOS DE LA INTERFAZ----------------//
  // Asocia las acciones de los botones y configura el comportamiento de los controles de la vista según las interacciones del usuario.
  private void configurarEventos() {
    btnProcesar.setOnAction( e -> procesar() );
    btnVerDetalle.setDisable(true);

    tabla.getSelectionModel().selectedItemProperty().addListener(
      (obs, anterior, actual) -> btnVerDetalle.setDisable(actual == null)
    );

    btnVerDetalle.setOnAction(e -> verDetalle());
  }

  //----------------METODO PARA GENERAR EL REPORTE----------------//
  /* Obtiene los eventos seleccionados, valida la selección y solicita al controlador la generación del reporte.
     Luego actualiza la tabla de resultados y los indicadores generales. */
  private void procesar() {
    try{
      List<Evento> seleccionados = obtenerEventosSeleccionados();
      if (seleccionados.isEmpty()) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Validación");
        alerta.setHeaderText(null);
        alerta.setContentText("Debe seleccionar al menos un evento.");
        alerta.showAndWait();
        return;
      }

      List<ReporteEvento> resultado = controlador.generarReporte(seleccionados, DatosIniciales.miembros.size());
      tabla.setItems(FXCollections.observableArrayList(resultado));
      actualizarIndicadores(resultado);
    } catch (IllegalArgumentException e) {
      mostrarError("Error de validación", e.getMessage());
    } 
  }

  //----------------METODO PARA ACTUALIZAR LOS INDICADORES----------------//
  /* Calcula y muestra los indicadores generales del reporte: cantidad de eventos analizados, total de asistencias, 
  promedio de asistencia y participación promedio. */
  private void actualizarIndicadores(List<ReporteEvento> resultado) {
    int eventosAnalizados = resultado.size();
    int totalAsistencias = resultado.stream().mapToInt(ReporteEvento::getPresentes).sum();

    double promedioAsistencia = eventosAnalizados == 0 ? 0 : (double) totalAsistencias / eventosAnalizados;

    double participacionPromedio = resultado.stream().mapToDouble(ReporteEvento::getParticipacion).average().orElse(0);

    lblEventosAnalizados.setText(String.valueOf(eventosAnalizados));
    lblTotalAsistencias.setText(String.valueOf(totalAsistencias));
    lblPromedioAsistencia.setText(String.format("%.2f",promedioAsistencia));
    lblParticipacionPromedio.setText(String.format("%.2f%%", participacionPromedio));
  }

  //----------------METODO PARA MOSTRAR EL DETALLE DE UN EVENTO----------------//
  // Abre la ventana de detalle correspondiente al evento seleccionado en la tabla de resultados.
  private void verDetalle() {
    ReporteEvento seleccionado = tabla.getSelectionModel().getSelectedItem();
    if (seleccionado == null) {
      return;
    }
    new VentanaDetalleAsistenciaEvento(seleccionado.getEvento()).mostrar();
  }

  //----------------METODO PARA OBTENER LOS EVENTOS SELECCIONADOS----------------//
  // Recorre los CheckBox de eventos y devuelve una lista con aquellos que fueron seleccionados por el usuario para generar el reporte.
  private List<Evento> obtenerEventosSeleccionados() {
    List<Evento> eventosSeleccionados = new ArrayList<>();
    for (javafx.scene.Node nodo : contenedorEventos.getChildren()) {
      CheckBox checkBox = (CheckBox) nodo;
      if (checkBox.isSelected()) {
        Evento evento = (Evento) checkBox.getUserData();
        eventosSeleccionados.add(evento);
      }
    }
    return eventosSeleccionados;
  }

  //----------------METODO PARA CREAR EL PANEL DE PARAMETROS----------------//
  // Construye el panel que contiene la selección de eventos y el botón para iniciar la generación del reporte.
  private VBox crearPanelParametros() {
    ScrollPane scrollEventos = new ScrollPane(contenedorEventos);
    scrollEventos.setFitToWidth(true);
    scrollEventos.setPrefHeight(180);

    VBox panel = new VBox(10);
    panel.setPadding(new Insets(20));
    panel.setStyle("""
      -fx-background-color: white;
      -fx-background-radius: 10;
      -fx-border-color: #d9d9d9;
      -fx-border-radius: 10;
    """);

    Label lblEventos = new Label("Eventos finalizados");
    panel.getChildren().addAll(lblEventos, scrollEventos, btnProcesar);
    return panel;
  }

  //----------------METODO PARA CREAR EL PANEL DE INDICADORES----------------//
  // Construye el contenedor que agrupa los indicadores generales mostrados en la parte superior del reporte.
  private HBox crearIndicadores() {
    HBox indicadores = new HBox(20);
    indicadores.setAlignment(Pos.CENTER);
    indicadores.getChildren().addAll(
      crearTarjeta("Eventos analizados", lblEventosAnalizados),
      crearTarjeta("Total asistencias", lblTotalAsistencias),
      crearTarjeta("Promedio asistencia", lblPromedioAsistencia),
      crearTarjeta("Participación promedio", lblParticipacionPromedio)
    );
    return indicadores;
  }

  //----------------METODO PARA CREAR UNA TARJETA DE INDICADOR----------------//
  // Genera una tarjeta visual compuesta por un título y un valor, utilizada para mostrar información resumida del reporte.
  private VBox crearTarjeta(String titulo, Label valor) {
    Label lblTitulo = new Label(titulo);
    lblTitulo.setStyle("""
      -fx-font-size: 13px;
      -fx-text-fill: #666666;
    """);

    valor.setStyle("""
      -fx-font-size: 20px;
      -fx-font-weight: bold;
    """);

    VBox tarjeta = new VBox(8, lblTitulo, valor);
    tarjeta.setAlignment(Pos.CENTER);
    tarjeta.setPadding(new Insets(15));
    tarjeta.setPrefWidth(200);
    tarjeta.setStyle("""
      -fx-background-color: white;
      -fx-background-radius: 10;
      -fx-border-color: #d9d9d9;
      -fx-border-radius: 10;
    """);
    return tarjeta;
  }

  //----------------METODO PARA CONSTRUIR LA VISTA----------------//
  /* Organiza y distribuye todos los componentes visuales de la ventana: título, panel de parámetros, indicadores, 
  tabla de resultados y acciones disponibles. */
  private void construirVista() {
    VBox contenedor = new VBox(20);
    contenedor.setPadding(new Insets(20));

    Label titulo = new Label("Reporte de Asistencia en Eventos");
    titulo.setStyle("""
      -fx-font-size: 24px;
      -fx-font-weight: bold;
    """);

    VBox panelParametros = crearPanelParametros();
    HBox indicadores = crearIndicadores();
    HBox barraInferior = new HBox(btnVerDetalle);
    barraInferior.setAlignment(Pos.CENTER_RIGHT);

    contenedor.getChildren().addAll(titulo, panelParametros, indicadores, tabla, barraInferior);

    VBox.setVgrow(tabla, Priority.ALWAYS);
    root.setCenter(contenedor);
    root.setStyle("""
      -fx-background-color: #f4f4f4;
    """);
  }

  //----------------METODO PARA MOSTRAR VENTANA DE ERROR----------------//
  //Muestra un mensaje de error al usuario indicando el problema detectado durante la ejecución de una operación.
  private void mostrarError( String titulo, String mensaje) {
    Alert alerta = new Alert(Alert.AlertType.ERROR);
    alerta.setTitle(titulo);
    alerta.setHeaderText(null);
    alerta.setContentText(mensaje);
    alerta.showAndWait();
  }

  //----------------METODO GETTER DEL CONTENEDOR PRINCIPAL----------------//
  // Devuelve el panel raíz de la vista para ser incorporado en la ventana principal de la aplicación.
  public BorderPane getRoot() {
    return root;
  }
}