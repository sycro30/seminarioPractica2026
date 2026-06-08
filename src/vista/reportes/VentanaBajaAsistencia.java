package vista.reportes;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import modelo.Asistencia;
import modelo.Miembro;

import java.util.List;

import controlador.reportes.ControladorBajaAsistencia;

public class VentanaBajaAsistencia {
  private ControladorBajaAsistencia controlador;
  private BorderPane root;
  
  //----------------METODO CONSTRUCTOR DE LA VISTA----------------//
  // Inicializa la vista de identificación de miembros con baja asistencia.
  public VentanaBajaAsistencia(ControladorBajaAsistencia controlador) {
    this.controlador = controlador;
  }

  //----------------METODO PARA CREAR LA VISTA----------------//
  /* Construye la interfaz gráfica del caso de uso de identificación de miembros con baja asistencia, 
  incluyendo formulario de búsqueda, tabla de resultados y panel de detalle. */
  public VBox crearVista() {
    Label titulo = new Label("Identificar miembros con baja asistencia");
    titulo.setStyle("-fx-font-size: 24px;" + "-fx-font-weight: bold;");

    //----------------Sección formulario de busqueda----------------//
    // Permite ingresar el período y el porcentaje mínimo de asistencia.
    GridPane formulario = new GridPane();

    formulario.setHgap(15);
    formulario.setVgap(15);
    formulario.setPadding(new Insets(20));

    Label labelFechaInicio = new Label("Fecha inicio:");
    DatePicker fechaInicio = new DatePicker();

    Label labelFechaFin = new Label("Fecha fin:");
    DatePicker fechaFin = new DatePicker();

    Label labelUmbral = new Label("Umbral mínimo (%):");
    TextField textoUmbral = new TextField();

    textoUmbral.setPromptText("Ej: 70");

    formulario.add(labelFechaInicio, 0, 0);
    formulario.add(fechaInicio, 1, 0);

    formulario.add(labelFechaFin, 0, 1);
    formulario.add(fechaFin, 1, 1);

    formulario.add(labelUmbral, 0, 2);
    formulario.add(textoUmbral, 1, 2);

    formulario.setStyle(
      "-fx-background-color: white;" +
      "-fx-background-radius: 10;" +
      "-fx-border-color: #DDD;" +
      "-fx-border-radius: 10;"
    );

    //----------------Seccion Tabla de Resultados----------------//
    // Muestra los miembros que cumplen el criterio de baja asistencia.
    TableView<Miembro> tabla = new TableView<>();
    TableColumn<Miembro, String> colNombre = new TableColumn<>("Nombre");
    colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

    TableColumn<Miembro, String> colApellido = new TableColumn<>("Apellido");
    colApellido.setCellValueFactory(new PropertyValueFactory<>("apellido"));

    TableColumn<Miembro, String> colTelefono = new TableColumn<>("Teléfono");
    colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));

    tabla.getColumns().add(colNombre);
    tabla.getColumns().add(colApellido);
    tabla.getColumns().add(colTelefono);
    tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
    tabla.setPrefHeight(300);

    //----------------Sección panel de detalle----------------//
    // Presenta el resumen de asistencia del miembro seleccionado.
    Label labelDetalleTitulo = new Label("Detalle del miembro");
    labelDetalleTitulo.setStyle("-fx-font-size: 18px;" + "-fx-font-weight: bold;");

    Label labelAsistencias = new Label("Asistencias: -");
    Label labelInasistencias = new Label("Inasistencias: -");
    Label labelPorcentaje = new Label("% Asistencia: -");

    VBox panelDetalle = new VBox(10, labelDetalleTitulo, labelAsistencias, labelInasistencias, labelPorcentaje);

    panelDetalle.setPadding(new Insets(15));
    panelDetalle.setStyle(
      "-fx-background-color: white;" +
      "-fx-border-color: #DDD;" +
      "-fx-border-radius: 10;"
    );

    //----------------Configuración de la selección de la tabla----------------//
    // Actualiza el panel de detalle al seleccionar un miembro.
    tabla.getSelectionModel().selectedItemProperty().addListener(
      (obs, anterior, miembroSeleccionado) -> {
        if (miembroSeleccionado == null) {
          labelAsistencias.setText("Asistencias: -");
          labelInasistencias.setText("Inasistencias: -");
          labelPorcentaje.setText("% Asistencia: -");
          return;
        }
        
        if (fechaInicio.getValue() != null && fechaFin.getValue() != null) {  
          List<Asistencia> registros = controlador.obtenerRegistrosDeMiembro( miembroSeleccionado, fechaInicio.getValue(), fechaFin.getValue());
          int asistencias = controlador.contarAsistencias(registros);
          int total = controlador.contarTotalReuniones(registros);
          int inasistencias = total - asistencias;
          double porcentaje = controlador.calcularPorcentajeAsistencia(asistencias, total);
          labelAsistencias.setText("Asistencias: " + asistencias);
          labelInasistencias.setText("Inasistencias: " + inasistencias);
          labelPorcentaje.setText(String.format("%% Asistencia: %.2f%%", porcentaje));
        }
      }
    );
    
    //----------------Configuración del botón procesar----------------//
    // Valida los parámetros ingresados y ejecuta la búsqueda.
    Button btnProcesar = new Button("Procesar");
    btnProcesar.setStyle(
      "-fx-background-color: #2E86DE;" +
      "-fx-text-fill: white;" +
      "-fx-font-weight: bold;" +
      "-fx-font-size: 14px;" +
      "-fx-padding: 10 20 10 20;"
    );

    btnProcesar.setOnAction(e -> {
      try {
        // Validar que se hayan ingresado fechas de inicio y fin
        if (fechaInicio.getValue() == null || fechaFin.getValue() == null) {
          mostrarError("Debe seleccionar ambas fechas.");
          return;
        }

        // Validar que la fecha de inicio no sea posterior a la fecha de fin
        if (fechaInicio.getValue().isAfter(fechaFin.getValue())) {
          mostrarError("La fecha de inicio no puede ser posterior a la fecha de fin.");
          return;
        }

        // Validar que se haya ingresado un valor para el umbral
        if (textoUmbral.getText().trim().isEmpty()) {
          mostrarError("Ingrese un valor.");
          return;
        }

        double valorMinimo = Double.parseDouble(textoUmbral.getText());
        
        // Validar que el valor del umbral esté entre 0 y 100
        if (valorMinimo < 0 || valorMinimo > 100) {
          mostrarError("Valor inválido. El valor debe estar entre 0 y 100.");
          return;
        }

        // Limpia la selección anterior para evitar mostrar datos desactualizados.
        tabla.getSelectionModel().clearSelection();
        labelAsistencias.setText("Asistencias: -");
        labelInasistencias.setText("Inasistencias: -");
        labelPorcentaje.setText("% Asistencia: -");
          
        List<Miembro> resultado = controlador.buscarMiembrosBajaAsistencia(
          fechaInicio.getValue(),
          fechaFin.getValue(),
          valorMinimo
        );

        tabla.setItems(FXCollections.observableArrayList(resultado));
      } catch (NumberFormatException ex) {
        mostrarError("Ingrese un porcentaje válido.");
      }
    });

    HBox contenedorBoton = new HBox(btnProcesar);
    contenedorBoton.setAlignment(Pos.CENTER_RIGHT);

    // CONTENEDOR PRINCIPAL
    VBox contenido = new VBox(
      20,
      titulo,
      formulario,
      contenedorBoton,
      tabla,
      panelDetalle
    );

    contenido.setPadding(new Insets(30));
    contenido.setStyle("-fx-background-color: #F5F6FA;");
    return contenido;
  }
  
  //----------------METODO PARA MOSTRAR MENSAJES DE ERROR----------------//
  // Muestra un mensaje de error cuando se detecta una validación inválida o una situación que impide procesar la búsqueda.
  private void mostrarError(String mensaje) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText(null);
    alert.setContentText(mensaje);
    alert.showAndWait();
  }

  //----------------METODO GETTER DEL CONTENEDOR PRINCIPAL----------------//
  // Devuelve el contenedor principal asociado a la vista.
  public BorderPane getRoot() {
    return root;
  }
}