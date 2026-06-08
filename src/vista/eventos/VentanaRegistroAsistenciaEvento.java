package vista.eventos;

import controlador.eventos.ControladorRegistroAsistenciaEvento;
import datos.DatosIniciales;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import modelo.Evento;
import modelo.Miembro;

//----------------VENTANA DE REGISTRO DE ASISTENCIA A EVENTOS----------------//
// Permite registrar la asistencia de un miembro a un evento específico.
public class VentanaRegistroAsistenciaEvento {
  //--------------ATRIBUTOS----------------//
  private Evento evento;
  private ComboBox<Miembro> cmbMiembro;
  private ComboBox<String> cmbEstado;
  private DatePicker dpFechaAsistencia;
  private TextArea txtObservaciones;
  private Button btnGuardar;
  private ControladorRegistroAsistenciaEvento controlador;

  //----------------METODO CONSTRUCTOR DE LA VISTA----------------//
  // Inicializa los componentes y carga la información necesaria para el registro.
  public VentanaRegistroAsistenciaEvento(Evento evento) {
    this.evento = evento;
    controlador = new ControladorRegistroAsistenciaEvento();
    cmbMiembro = new ComboBox<>();
    cmbEstado = new ComboBox<>();
    dpFechaAsistencia = new DatePicker();
    txtObservaciones = new TextArea();

    btnGuardar = new Button("Registrar");

    cargarDatos();
    configurarEventos();
  }

  //----------------METODO PARA CARGAR LOS DATOS INICIALES----------------//
  // Carga los miembros disponibles, los estados de asistencia y configura los controles.
  private void cargarDatos() {
    cmbMiembro.setItems( FXCollections.observableArrayList(DatosIniciales.miembros) );
    cmbEstado.setItems( FXCollections.observableArrayList("Presente", "Ausente") );

    dpFechaAsistencia.setValue(evento.getFechaActividad());

    cmbMiembro.setMaxWidth(Double.MAX_VALUE);
    cmbEstado.setMaxWidth(Double.MAX_VALUE);
    dpFechaAsistencia.setMaxWidth(Double.MAX_VALUE);

    txtObservaciones.setPrefRowCount(4);
    txtObservaciones.setWrapText(true);
  }

  //----------------METODO PARA CONFIGURAR LOS EVENTOS----------------//
  // Asocia la acción del botón Registrar con el proceso de guardado.
  private void configurarEventos() {
    btnGuardar.disableProperty().bind(
      cmbMiembro.valueProperty().isNull().or(cmbEstado.valueProperty().isNull())
    );
    btnGuardar.setOnAction(e -> guardarAsistencia());
  }

  //----------------METODO PARA GUARDAR LA ASISTENCIA----------------//
  // Registra la asistencia del miembro seleccionado y muestra mensajes según el resultado.
  private void guardarAsistencia() {
    Miembro miembro = cmbMiembro.getValue();
    String estado = cmbEstado.getValue();

    if (miembro == null || estado == null) {
      return;
    }

    boolean registrado = controlador.registrarAsistencia(
      evento, miembro, estado, dpFechaAsistencia.getValue(), txtObservaciones.getText()
    );

    // Valida que el miembro no tenga una asistencia previamente registrada.
    if (!registrado) {
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setTitle("Asistencia duplicada");
      alert.setHeaderText(null);
      alert.setContentText("El miembro ya tiene asistencia registrada para este evento.");
      alert.showAndWait();
      return;
    }

    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Registro exitoso");
    alert.setHeaderText(null);
    alert.setContentText("La asistencia fue registrada correctamente.");
    alert.showAndWait();

    Stage stage = (Stage) btnGuardar.getScene().getWindow();
    stage.close();
  }

  //----------------METODO PARA MOSTRAR LA VENTANA----------------//
  // Construye y muestra el formulario de registro de asistencia.
  public void mostrar() {
    GridPane root = new GridPane();
    root.setPadding(new Insets(15));
    root.setHgap(10);
    root.setVgap(10);

    ColumnConstraints col1 = new ColumnConstraints();
    col1.setMinWidth(150);
    ColumnConstraints col2 = new ColumnConstraints();
    col2.setHgrow(Priority.ALWAYS);
    root.getColumnConstraints().addAll(col1, col2);

    Label titulo = new Label("Registro de Asistencia");
    titulo.setStyle("""
      -fx-font-size: 18px;
      -fx-font-weight: bold;
    """);
    root.add(titulo, 0, 0, 2, 1);

    root.add(new Label("Evento:"), 0, 1);
    root.add(new Label(evento.getNombre()), 1, 1);
    root.add(new Label("Miembro:"), 0, 2);
    root.add(cmbMiembro, 1, 2);
    root.add(new Label("Estado:"), 0, 3);
    root.add(cmbEstado, 1, 3);
    root.add(new Label("Fecha asistencia:"), 0, 4);
    root.add(dpFechaAsistencia, 1, 4);
    root.add(new Label("Observaciones:"), 0, 5);
    root.add(txtObservaciones, 1, 5);
    root.add(btnGuardar, 1, 6);

    Scene escena = new Scene(root, 650, 420);

    Stage stage = new Stage();
    stage.setTitle("Registrar Asistencia Evento");
    stage.setScene(escena);
    stage.show();
  }
}