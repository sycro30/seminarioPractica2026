package vista.celulas;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import controlador.celulas.ControladorRegistroAsistenciaCelula;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import modelo.Celula;
import modelo.Miembro;

//----------------VENTANA DE REGISTRO DE ASISTENCIA DE CÉLULA----------------//
// Permite registrar una reunión de célula y la asistencia de cada miembro.
public class VentanaRegistroAsistenciaCelula {
  //--------------ATRIBUTOS----------------//
  private Celula celula;
  private Stage stage;
  private Button btnGuardar;
  private Button btnCancelar;
  private TextField txtTema;
  private DatePicker dpFecha;
  private TextArea txtObservaciones;
  private List<RegistroPresencia> registros;
  private ControladorRegistroAsistenciaCelula controlador;

  //----------------METODO CONSTRUCTOR DE LA VISTA----------------//
  // Inicializa los controles de la ventana y crea los registros de asistencia para los miembros de la célula seleccionada.
  public VentanaRegistroAsistenciaCelula(Celula celula) {
    this.celula = celula;
    controlador = new ControladorRegistroAsistenciaCelula();
    
    btnGuardar = new Button("Guardar");
    btnCancelar = new Button("Cancelar");
    
    txtTema = new TextField();
    dpFecha = new DatePicker();
    dpFecha.setValue(LocalDate.now());
    txtObservaciones = new TextArea();
    txtObservaciones.setPrefRowCount(3);

    crearRegistros();
  }

  //----------------METODO PARA CREAR LOS REGISTROS DE PRESENCIA----------------//
  // Genera una lista de registros temporales para cada miembro de la célula.
  private void crearRegistros() {
    registros = new ArrayList<>();
    for (Miembro miembro : celula.getMiembros()) {
      registros.add( new RegistroPresencia(miembro) );
    }
  }

  //----------------METODO PARA MOSTRAR LA VENTANA----------------//
  // Construye la interfaz gráfica y muestra el formulario de registro.
  public void mostrar() {
    stage = new Stage();
    BorderPane root = new BorderPane();
    GridPane grid = new GridPane();

    grid.setPadding(new Insets(15));
    grid.setHgap(20);
    grid.setVgap(10);

    int fila = 0;
    
    grid.add(new Label("Célula: " + celula.getNombre()), 0, fila++);
    grid.add(new Label("Fecha"), 0, fila);
    grid.add(dpFecha, 1, fila++);
    grid.add(new Label("Tema"), 0, fila);
    grid.add(txtTema, 1, fila++);
    grid.add(new Label("Observaciones"), 0, fila);
    grid.add(txtObservaciones, 1, fila++);

    fila++;

    grid.add(new Label("Miembro"), 0, fila);
    grid.add(new Label("Presente"), 1, fila++);

    // Se crea una fila por cada miembro de la célula
    for (RegistroPresencia registro : registros) {
      Label lblMiembro = new Label(
        registro.getMiembro().getNombre()
        + " "
        + registro.getMiembro().getApellido()
      );

      CheckBox chkPresente = new CheckBox();

      // Por defecto todos los miembros aparecen como presentes
      chkPresente.setSelected(true);

      // Actualiza el estado del registro al marcar o desmarcar el CheckBox
      chkPresente.selectedProperty().addListener(
        (obs, anterior, actual) -> registro.setPresente(actual)
      );

      grid.add(lblMiembro, 0, fila);
      grid.add(chkPresente, 1, fila);
      fila++;
    }

    ScrollPane scroll = new ScrollPane(grid);

    HBox botones = new HBox(10, btnGuardar, btnCancelar);
    botones.setPadding(new Insets(10));

    root.setCenter(scroll);
    root.setBottom(botones);

    configurarEventos();

    Scene escena = new Scene(root, 650, 500);

    stage.setTitle("Registro de Asistencia");
    stage.setScene(escena);
    stage.showAndWait();
  }

  //----------------METODO PARA CONFIGURAR LOS EVENTOS----------------//
  // Asocia las acciones de los botones Guardar y Cancelar.
  private void configurarEventos() {
    btnGuardar.disableProperty().bind(
      txtTema.textProperty().isEmpty().or(dpFecha.valueProperty().isNull())
    );
    btnCancelar.setOnAction( e -> stage.close() );
    btnGuardar.setOnAction( e -> guardar() );
  }

  //----------------METODO PARA GUARDAR EL REGISTRO----------------//
  // Envía la información ingresada al controlador para crear la reunión y registrar la asistencia de los miembros.
  private void guardar() {
    try{
      controlador.crearReunionCelula(celula, dpFecha.getValue(), txtTema.getText(), txtObservaciones.getText(), registros);

      Alert alerta = new Alert(Alert.AlertType.INFORMATION);
      alerta.setTitle("Registro exitoso");
      alerta.setHeaderText(null);
      alerta.setContentText("La asistencia fue registrada correctamente.");
      alerta.showAndWait();

      stage.close();
    } catch (IllegalArgumentException ex) {
      Alert alerta = new Alert(Alert.AlertType.WARNING);
      alerta.setTitle("Validación");
      alerta.setHeaderText(null);
      alerta.setContentText(ex.getMessage());
      alerta.showAndWait();
    }
  }
}