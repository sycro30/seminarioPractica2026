package vista.miembros;

import controlador.miembros.ControladorMiembros;
import datos.DatosIniciales;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import modelo.Celula;
import modelo.Miembro;

//----------------VENTANA DE FORMULARIO DE MIEMBRO----------------//
// Permite registrar o modificar los datos de un miembro según el modo de apertura de la ventana.
public class VentanaFormularioMiembro {
  //--------------ATRIBUTOS----------------//
  private Stage stage;
  private Miembro miembro;
  private ModoVentanaMiembros modo;
  private ControladorMiembros controlador;
  
  private TextField txtNombre;
  private TextField txtApellido;
  private TextField txtTelefono;
  private TextField txtCorreo;
  private ComboBox<Celula> cmbCelula;
  
  private Button btnGuardar;
  private Button btnCancelar;

  //----------------METODO CONSTRUCTOR DE LA VISTA----------------//
  // Inicializa los componentes del formulario, carga las células disponibles y configura los eventos de la interfaz.
  public VentanaFormularioMiembro(ModoVentanaMiembros modo, Miembro miembro) {
    this.modo = modo;
    this.miembro = miembro;
    controlador = new ControladorMiembros();

    inicializarComponentes();
    cargarCelulas();
    configurarEventos();

    if (modo == ModoVentanaMiembros.MODIFICACION) {
      cargarDatos();
    }
  }

  //----------------METODO PARA INICIALIZAR LOS COMPONENTES----------------//
  // Crea los controles utilizados en el formulario de miembros.
  private void inicializarComponentes() {
    txtNombre = new TextField();
    txtApellido = new TextField();
    txtTelefono = new TextField();
    txtCorreo = new TextField();
    cmbCelula = new ComboBox<>();

    btnGuardar = new Button("Guardar");
    btnCancelar = new Button("Cancelar");
  }

  //----------------METODO PARA CARGAR LAS CELULAS----------------//
  // Carga en el ComboBox las células disponibles para asignar al miembro.
  private void cargarCelulas() {
    cmbCelula.setItems(FXCollections.observableArrayList(DatosIniciales.celulas));
  }

  //----------------METODO PARA CARGAR LOS DATOS DEL MIEMBRO----------------//
  // Completa el formulario con la información del miembro seleccionado cuando la ventana se encuentra en modo modificación.
  private void cargarDatos() {
    txtNombre.setText(miembro.getNombre());
    txtApellido.setText(miembro.getApellido());
    txtTelefono.setText(miembro.getTelefono());
    txtCorreo.setText(miembro.getCorreo());
    cmbCelula.setValue(miembro.getCelula());
  }

  //----------------METODO PARA CONFIGURAR LOS EVENTOS----------------//
  // Asocia las acciones de los botones Guardar y Cancelar.
  private void configurarEventos() {
    btnGuardar.disableProperty().bind(
      txtNombre.textProperty().isEmpty()
        .or(txtApellido.textProperty().isEmpty())
        .or(txtTelefono.textProperty().isEmpty())
        .or(txtCorreo.textProperty().isEmpty())
        .or(cmbCelula.valueProperty().isNull())
    );
    btnGuardar.setOnAction( e -> guardar() );
    btnCancelar.setOnAction( e -> stage.close() );
  }

  //----------------METODO PARA GUARDAR EL MIEMBRO----------------//
  // Registra o actualiza un miembro según el modo de la ventana.
  private void guardar() {
    if (!validarFormulario()) {
      return;
    }
    try {
      if (modo == ModoVentanaMiembros.ALTA) {
        controlador.agregarMiembro(
          txtNombre.getText(),
          txtApellido.getText(),
          txtTelefono.getText(),
          txtCorreo.getText(),
          cmbCelula.getValue()
        );
      } else {
        controlador.modificarMiembro(
          miembro,
          txtNombre.getText(),
          txtApellido.getText(),
          txtTelefono.getText(),
          txtCorreo.getText(),
          cmbCelula.getValue()
        );
      }
      stage.close();
    } catch (Exception ex) {
      mostrarError(ex.getMessage());
    }
  }

  private boolean validarFormulario() {
    if (txtNombre.getText().trim().isEmpty()) {
      mostrarError("Debe ingresar el nombre.");
      return false;
    }
    if (txtApellido.getText().trim().isEmpty()) {
      mostrarError("Debe ingresar el apellido.");
      return false;
    }
    if (!txtTelefono.getText().matches("\\d+")) {
      mostrarError("El teléfono debe contener solo números.");
      return false;
    }
    if (!txtCorreo.getText().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
      mostrarError("Ingrese un correo válido.");
      return false;
    }
    return true;
  }
  
  //----------------METODO PARA MOSTRAR MENSAJES DE ERROR----------------//
  private void mostrarError(String mensaje) {
    Alert alerta = new Alert(Alert.AlertType.ERROR);
    alerta.setTitle("Error");
    alerta.setHeaderText(null);
    alerta.setContentText(mensaje);
    alerta.showAndWait();
  }

  //----------------METODO PARA MOSTRAR LA VENTANA----------------//
  // Construye y muestra el formulario de alta o modificación de miembros.
  public void mostrar() {
    stage = new Stage();
    GridPane formulario = new GridPane();
    formulario.setPadding(new Insets(15));
    formulario.setHgap(10);
    formulario.setVgap(10);

    formulario.add(new Label("Nombre"), 0, 0);
    formulario.add(txtNombre, 1, 0);
    formulario.add(new Label("Apellido"), 0, 1);
    formulario.add(txtApellido, 1, 1);
    formulario.add(new Label("Teléfono"), 0, 2);
    formulario.add(txtTelefono, 1, 2);
    formulario.add(new Label("Correo"), 0, 3);
    formulario.add(txtCorreo, 1, 3);
    formulario.add(new Label("Célula"), 0, 4);
    formulario.add(cmbCelula, 1, 4);

    HBox botones = new HBox(10, btnGuardar, btnCancelar);
    botones.setPadding(new Insets(10));

    BorderPane root = new BorderPane();
    root.setCenter(formulario);
    root.setBottom(botones);

    Scene escena = new Scene(root, 450, 280);
    stage.setTitle(modo == ModoVentanaMiembros.ALTA ? "Nuevo Miembro" : "Modificar Miembro");
    stage.setScene(escena);
    stage.showAndWait();
  }
}