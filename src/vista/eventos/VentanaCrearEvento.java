package vista.eventos;

import java.time.LocalTime;
import controlador.eventos.ControladorEventos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

//----------------VENTANA DE CREACION DE EVENTOS----------------//
// Permite registrar un nuevo evento mediante el ingreso de sus datos básicos.
public class VentanaCrearEvento {

  //--------------ATRIBUTOS----------------//
  private TextField txtNombre;
  private TextArea txtDescripcion;
  private DatePicker dpFecha;
  private TextField txtHora;
  private TextField txtDireccion;
  private Button btnGuardar;
  private ControladorEventos controlador;

  //----------------METODO CONSTRUCTOR DE LA VISTA----------------//
  // Inicializa los componentes y configura los eventos de la interfaz.
  public VentanaCrearEvento() {
    controlador = new ControladorEventos();
    txtNombre = new TextField();
    txtDescripcion = new TextArea();
    dpFecha = new DatePicker();
    txtHora = new TextField();
    txtDireccion = new TextField();
    btnGuardar = new Button("Guardar");
    configurarEventos();
  }

  //----------------METODO PARA CONFIGURAR LOS EVENTOS----------------//
  // Asocia la acción del botón Guardar con el proceso de creación del evento.
  private void configurarEventos() {

    // El botón permanece deshabilitado mientras existan campos obligatorios vacíos.
    btnGuardar.disableProperty().bind(
      txtNombre.textProperty().isEmpty()
        .or(txtDescripcion.textProperty().isEmpty())
        .or(txtHora.textProperty().isEmpty())
        .or(txtDireccion.textProperty().isEmpty())
        .or(dpFecha.valueProperty().isNull())
    );

    btnGuardar.setOnAction(e -> guardarEvento());
  }

  //----------------METODO PARA GUARDAR EL EVENTO----------------//
  // Valida la información ingresada y registra el nuevo evento.
  private void guardarEvento() {
    try {
      controlador.crearEvento(
        txtNombre.getText().trim(),
        txtDescripcion.getText().trim(),
        dpFecha.getValue(),
        LocalTime.parse(txtHora.getText().trim()),
        txtDireccion.getText().trim()
      );

      Stage stage = (Stage) btnGuardar.getScene().getWindow();
      stage.close();
    } catch (Exception ex) {
      mostrarError(ex.getMessage());
    }
  }

  //----------------METODO PARA MOSTRAR LA VENTANA----------------//
  // Construye y muestra el formulario de creación de eventos.
  public void mostrar() {
    GridPane root = new GridPane();

    root.setPadding(new Insets(15));
    root.setHgap(10);
    root.setVgap(10);

    root.add(new Label("Nombre"), 0, 0);
    root.add(txtNombre, 1, 0);

    root.add(new Label("Descripción"), 0, 1);
    root.add(txtDescripcion, 1, 1);

    root.add(new Label("Fecha"), 0, 2);
    root.add(dpFecha, 1, 2);

    root.add(new Label("Hora (HH:mm)"), 0, 3);
    root.add(txtHora, 1, 3);

    root.add(new Label("Dirección"), 0, 4);
    root.add(txtDireccion, 1, 4);

    root.add(btnGuardar, 1, 5);

    Scene escena = new Scene(root, 450, 350);

    Stage stage = new Stage();
    stage.setTitle("Nuevo Evento");
    stage.setScene(escena);
    stage.show();
  }

  //----------------METODO PARA MOSTRAR MENSAJES DE ERROR----------------//
  // Muestra una alerta con el mensaje de error recibido.
  private void mostrarError(String mensaje) {
    Alert alerta = new Alert(Alert.AlertType.ERROR);
    alerta.setTitle("Error");
    alerta.setHeaderText(null);
    alerta.setContentText(mensaje);
    alerta.showAndWait();
  }
}