package vista.reportes.celula;

import controlador.reportes.ControladorReporteAsistenciaCelulas;
import datos.DatosIniciales;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import modelo.Celula;
import java.util.ArrayList;
import java.util.List;

public class VentanaReporteAsistenciaCelulas {

  //-----------ATRIBUTOS--------------//
  private BorderPane root;
  private DatePicker dpInicioPeriodo1;
  private DatePicker dpFinPeriodo1;
  private DatePicker dpInicioPeriodo2;
  private DatePicker dpFinPeriodo2;
  private VBox contenedorCelulas;
  private Button btnProcesar;
  private Button btnVerDetalle;
  private TableView<ReporteCelula> tabla;
  private ControladorReporteAsistenciaCelulas controlador;

  //----------------METODO CONSTRUCTOR DE LA VISTA-----------------//
  // Inicializa el controlador, los componentes visuales, la tabla de resultados, los eventos y la estructura gráfica.
  public VentanaReporteAsistenciaCelulas() {
    controlador = new ControladorReporteAsistenciaCelulas();
    root = new BorderPane();
    crearControles();
    crearTabla();
    configurarEventos();
    construirVista();
  }

  //----------------METODO PARA CREAR LOS CONTROLES DE LA VISTA----------------//
  // Inicializa los controles utilizados para capturar los parámetros necesarios para generar el reporte.
  private void crearControles() {
    dpInicioPeriodo1 = new DatePicker();
    dpFinPeriodo1 = new DatePicker();
    dpInicioPeriodo2 = new DatePicker();
    dpFinPeriodo2 = new DatePicker();

    contenedorCelulas = new VBox(8);
    for (Celula celula : DatosIniciales.celulas) {
      CheckBox chkCelula = new CheckBox(celula.getNombre());

      // Se almacena la referencia de la célula para recuperarla posteriormente.
      chkCelula.setUserData(celula);

      contenedorCelulas.getChildren().add(chkCelula);
    }

    btnProcesar = new Button("Generar Reporte");
    btnProcesar.setPrefWidth(180);
    btnVerDetalle = new Button("Ver detalle");
  }

  //----------------METODO PARA CREAR LA TABLA DE RESULTADOS----------------//
  // Configura la tabla donde se mostrarán los indicadores calculados para cada célula seleccionada.
  private void crearTabla() {
    tabla = new TableView<>();
    tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    TableColumn<ReporteCelula, String> colCelula = new TableColumn<>("Célula");
    colCelula.setCellValueFactory(d ->
      new SimpleStringProperty(d.getValue().getNombreCelula())
    );

    TableColumn<ReporteCelula, Number> colPromedio1 = new TableColumn<>("Promedio P1");
    colPromedio1.setCellValueFactory(d ->
      new SimpleDoubleProperty(d.getValue().getPromedioPeriodo1())
    );

    TableColumn<ReporteCelula, Number> colPromedio2 = new TableColumn<>("Promedio P2");
    colPromedio2.setCellValueFactory(d ->
      new SimpleDoubleProperty(d.getValue().getPromedioPeriodo2())
    );

    TableColumn<ReporteCelula, Number> colVariacion = new TableColumn<>("Variación");
    colVariacion.setCellValueFactory(dato ->
      new SimpleDoubleProperty(dato.getValue().getVariacion())
    );
    colVariacion.setCellFactory(col ->
      new TableCell<>() {        
        @Override
        protected void updateItem(Number valor, boolean empty) {
          super.updateItem(valor, empty);
          if (empty || valor == null) {
            setText(null);
          } else {
            setText(String.format("%.2f %%", valor.doubleValue()));
          }
        }
      }
    );

    TableColumn<ReporteCelula, Number> colParticipacion = new TableColumn<>("Participación %");
    colParticipacion.setCellValueFactory(dato ->
      new SimpleDoubleProperty(dato.getValue().getParticipacion())
    );
    colParticipacion.setCellFactory(col ->
      new TableCell<>() {
        @Override
        protected void updateItem(Number valor, boolean empty) {
          super.updateItem(valor, empty);
          if (empty || valor == null) {
            setText(null);
          } else {
            setText(String.format("%.2f %%", valor.doubleValue()));
          }
        }
      }
    );

    tabla.getColumns().addAll(colCelula, colPromedio1, colPromedio2, colVariacion, colParticipacion);
    tabla.setPlaceholder(new Label("Seleccione células y genere un reporte"));
  }

  //----------------METODO PARA CONFIGURAR LOS EVENTOS DE LA INTERFAZ----------------//
  // Configura las acciones asociadas a los botones y la selección de registros en la tabla.
  private void configurarEventos() {
    btnProcesar.setOnAction(e -> procesar());
    btnVerDetalle.setDisable(true);

    tabla.getSelectionModel().selectedItemProperty().addListener(
      (obs, anterior, actual) -> btnVerDetalle.setDisable(actual == null)
    );

    btnVerDetalle.setOnAction(e -> verDetalle());
  }

  //----------------METODO PARA GENERAR EL REPORTE----------------//
  // Valida los parámetros ingresados por el usuario, obtiene las células seleccionadas y solicita al controlador la generación del reporte.
  private void procesar() {
    if (dpInicioPeriodo1.getValue().isAfter(dpFinPeriodo1.getValue())) {
      mostrarError("Validación", "La fecha de inicio del período 1 no puede ser mayor a la fecha final.");
      return;
    }
    if (dpInicioPeriodo2.getValue().isAfter(dpFinPeriodo2.getValue())) {
      mostrarError("Validación","La fecha de inicio del período 2 no puede ser mayor a la fecha final.");
      return;
    }
    if (obtenerCelulasSeleccionadas().isEmpty()) {
      mostrarError("Validación", "Debe seleccionar al menos una célula.");
      return;
    }

    try {
      validarParametros();
      List<Celula> seleccionadas = obtenerCelulasSeleccionadas();
      List<ReporteCelula> resultado = controlador.generarReporte(
        seleccionadas,
        dpInicioPeriodo1.getValue(),
        dpFinPeriodo1.getValue(),
        dpInicioPeriodo2.getValue(),
        dpFinPeriodo2.getValue()
      );

      //Verifica si existe al menos una célula con datos en alguno de los períodos analizados.
      boolean hayDatos = resultado.stream().anyMatch(reporte ->
        reporte.getPromedioPeriodo1() > 0 || reporte.getPromedioPeriodo2() > 0
      );

      if (!hayDatos) {
        mostrarError("Sin datos", "No existen registros de asistencia para los períodos seleccionados.");
        return;
      }
      
      if (resultado.isEmpty()) {
        mostrarError("Sin resultados", "No existen registros para los períodos seleccionados.");
        return;
      }

      tabla.setItems(FXCollections.observableArrayList(resultado));
    } catch (IllegalArgumentException e) {
      mostrarError("Error de validación", e.getMessage());
    }
  }

  //----------------METODO PARA VALIDAR LOS PARAMETROS----------------//
  // Verifica que todos los parámetros requeridos hayan sido ingresados correctamente.
  private void validarParametros() {
    if (dpInicioPeriodo1.getValue() == null || dpFinPeriodo1.getValue() == null
      || dpInicioPeriodo2.getValue() == null || dpFinPeriodo2.getValue() == null
    ) {
      throw new IllegalArgumentException("Debe completar todos los períodos.");
    }

    if (dpInicioPeriodo1.getValue().isAfter(dpFinPeriodo1.getValue())) {
      throw new IllegalArgumentException("La fecha inicial del período 1 no puede ser posterior a la fecha final.");
    }

    if (dpInicioPeriodo2.getValue().isAfter(dpFinPeriodo2.getValue())) {
      throw new IllegalArgumentException("La fecha inicial del período 2 no puede ser posterior a la fecha final.");
    }

    if (obtenerCelulasSeleccionadas().isEmpty()) {
      throw new IllegalArgumentException("Debe seleccionar al menos una célula.");
    }
  }

  //----------------METODO PARA OBTENER LAS CELULAS SELECCIONADAS----------------//
  // Recorre los CheckBox y devuelve únicamente las células seleccionadas por el usuario.
  private List<Celula> obtenerCelulasSeleccionadas() {
    List<Celula> seleccionadas = new ArrayList<>();
    for (Node nodo : contenedorCelulas.getChildren()) {
      CheckBox checkBox = (CheckBox) nodo;
      if (checkBox.isSelected()) {
        Celula celula = (Celula) checkBox.getUserData();
        seleccionadas.add(celula);
      }
    }
    return seleccionadas;
  }

  //----------------METODO PARA MOSTRAR EL DETALLE DE UNA CELULA----------------//
  // Abre la ventana de detalle para la célula seleccionada en la tabla de resultados.
  private void verDetalle() {
    ReporteCelula seleccionado = tabla.getSelectionModel().getSelectedItem();
    if (seleccionado == null) {
      return;
    }
    new VentanaDetalleReporteCelula(
      seleccionado.getCelula(),
      dpInicioPeriodo1.getValue(),
      dpFinPeriodo1.getValue(),
      dpInicioPeriodo2.getValue(),
      dpFinPeriodo2.getValue()
    ).mostrar();
  }

  //----------------METODO PARA BUSCAR UNA CELULA----------------//
  // Busca la referencia de una célula utilizando su nombre.
  private Celula buscarCelula(String nombre) {
    for (Celula celula : DatosIniciales.celulas) {
      if (celula.getNombre().equals(nombre)) {
        return celula;
      }
    }
    return null;
  }

  //----------------METODO PARA CREAR EL PANEL DE PARAMETROS----------------//
  // Construye el panel que contiene los filtros necesarios para generar el reporte.
  private VBox crearPanelParametros() {
    GridPane grid = new GridPane();
    grid.setHgap(15);
    grid.setVgap(15);

    grid.add(new Label("Inicio período 1"), 0, 0);
    grid.add(dpInicioPeriodo1, 1, 0);
    grid.add(new Label("Fin período 1"), 2, 0);
    grid.add(dpFinPeriodo1, 3, 0);
    grid.add(new Label("Inicio período 2"), 0, 1);
    grid.add(dpInicioPeriodo2, 1, 1);
    grid.add(new Label("Fin período 2"), 2, 1);
    grid.add(dpFinPeriodo2, 3, 1);

    ScrollPane scrollCelulas = new ScrollPane(contenedorCelulas);
    scrollCelulas.setFitToWidth(true);
    scrollCelulas.setPrefHeight(150);

    VBox panel = new VBox(15);

    panel.setPadding(new Insets(20));
    panel.setStyle("""
      -fx-background-color: white;
      -fx-background-radius: 10;
      -fx-border-color: #d9d9d9;
      -fx-border-radius: 10;
    """);

    panel.getChildren().addAll(grid, new Label("Células"), scrollCelulas, btnProcesar);
    return panel;
  }

  //----------------METODO PARA CONSTRUIR LA VISTA----------------//
  private void construirVista() {
    VBox contenedor = new VBox(20);
    contenedor.setPadding(new Insets(20));

    Label titulo = new Label("Reporte de Asistencia de Células");
    titulo.setStyle("""
      -fx-font-size: 24px;
      -fx-font-weight: bold;
    """);

    VBox panelParametros = crearPanelParametros();
    HBox barraInferior = new HBox(btnVerDetalle);
    barraInferior.setAlignment(Pos.CENTER_RIGHT);

    contenedor.getChildren().addAll(titulo, panelParametros, tabla, barraInferior);

    VBox.setVgrow(tabla, Priority.ALWAYS);

    root.setCenter(contenedor);
    root.setStyle("""
      -fx-background-color: #f4f4f4;
    """);
  }

  //----------------METODO PARA MOSTRAR MENSAJES DE ERROR----------------//
  private void mostrarError(String titulo, String mensaje) {
    Alert alerta = new Alert(Alert.AlertType.ERROR);
    alerta.setTitle(titulo);
    alerta.setHeaderText(null);
    alerta.setContentText(mensaje);
    alerta.showAndWait();
  }

  //----------------METODO GETTER DEL CONTENEDOR PRINCIPAL----------------//
  public BorderPane getRoot() {
    return root;
  }
}
