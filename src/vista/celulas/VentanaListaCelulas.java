package vista.celulas;

import controlador.celulas.ControladorListaCelulas;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import modelo.Celula;

//----------------VENTANA DE LISTADO DE CÉLULAS----------------//
// Muestra las células registradas y permite acceder al registro de asistencia de la célula seleccionada.
public class VentanaListaCelulas {
  private BorderPane root;
  private TableView<Celula> tabla;
  private Button btnRegistrarAsistencia;
  private ControladorListaCelulas controlador;

  //----------------CONSTRUCTOR----------------//
  // Inicializa la vista, carga los datos y configura los eventos.
  public VentanaListaCelulas() {
    controlador = new ControladorListaCelulas();
    root = new BorderPane();
    tabla = new TableView<>();
    btnRegistrarAsistencia = new Button("Registrar Asistencia");

    crearColumnas();
    cargarDatos();
    configurarEventos();
    construirVista();
  }

  //----------------CARGA DE DATOS----------------//
  // Obtiene las células registradas y las muestra en la tabla.
  private void cargarDatos() {
    tabla.setItems(FXCollections.observableArrayList(controlador.obtenerCelulas()));
  }

  //----------------CONFIGURACIÓN DE EVENTOS----------------//
  // Habilita el botón de registro únicamente cuando existe una célula seleccionada.
  private void configurarEventos() {
    btnRegistrarAsistencia.setDisable(true);
    tabla.getSelectionModel().selectedItemProperty().addListener(
      (obs, anterior, actual) -> btnRegistrarAsistencia.setDisable(actual == null)
    );
    btnRegistrarAsistencia.setOnAction( e -> registrarAsistencia() );
  }

  //----------------REGISTRO DE ASISTENCIA----------------//
  // Abre la ventana para registrar asistencia de la célula seleccionada.
  private void registrarAsistencia() {
    Celula seleccionada = tabla.getSelectionModel().getSelectedItem();
    if (seleccionada == null) {
      return;
    }
    VentanaRegistroAsistenciaCelula ventana = new VentanaRegistroAsistenciaCelula(seleccionada);
    ventana.mostrar();
  }

  //----------------CREACIÓN DE COLUMNAS----------------//
  // Define las columnas que se mostrarán en la tabla de células.
  private void crearColumnas() {
    TableColumn<Celula, String> colNombre = new TableColumn<>("Nombre");
    colNombre.setCellValueFactory(dato ->
      new SimpleStringProperty(dato.getValue().getNombre())
    );
    
    TableColumn<Celula, String> colDireccion = new TableColumn<>("Dirección");
    colDireccion.setCellValueFactory(dato ->
      new SimpleStringProperty(dato.getValue().getDireccion())
    );

    TableColumn<Celula, String> colFecha = new TableColumn<>("Fecha");
    colFecha.setCellValueFactory(dato ->
      new SimpleStringProperty(dato.getValue().getFecha().toString())
    );

    TableColumn<Celula, String> colHora = new TableColumn<>("Hora");
    colHora.setCellValueFactory(dato ->
      new SimpleStringProperty(dato.getValue().getHora().toString())
    );

    TableColumn<Celula, String> colLider = new TableColumn<>("Líder");
    colLider.setCellValueFactory(dato -> {
      if (dato.getValue().getLider() == null) {
        return new SimpleStringProperty(
          "Sin líder"
        );
      }
      return new SimpleStringProperty(
        dato.getValue()
          .getLider()
          .getNombreCompleto()
      );
    });

    TableColumn<Celula, String> colMiembros = new TableColumn<>("Miembros");
    colMiembros.setCellValueFactory(dato ->
      new SimpleStringProperty( String.valueOf(dato.getValue().getMiembros().size()) )
    );

    TableColumn<Celula, String> colUltimaReunion = new TableColumn<>("Última reunión");
    colUltimaReunion.setCellValueFactory(dato ->
      new SimpleStringProperty( controlador.obtenerUltimaFechaReunion(dato.getValue()) )
    );

    TableColumn<Celula, String> colUltimoTema = new TableColumn<>("Último tema");
    colUltimoTema.setCellValueFactory(dato ->
      new SimpleStringProperty( controlador.obtenerUltimoTema(dato.getValue()) )
    );

    tabla.getColumns().addAll(
      colNombre, colDireccion, colFecha, colHora, colLider, colMiembros, colUltimaReunion, colUltimoTema
    );
  }

  //----------------CONSTRUCCIÓN DE LA VISTA----------------//
  // Organiza los componentes visuales dentro del contenedor principal.
  private void construirVista() {
    HBox barra = new HBox(10, btnRegistrarAsistencia);
    barra.setPadding(new Insets(10));

    root.setTop(barra);
    root.setCenter(tabla);
  }

  public BorderPane getRoot() {
    return root;
  }
}
