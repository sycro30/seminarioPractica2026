package vista.eventos;

import controlador.eventos.ControladorEventos;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import modelo.Evento;

//----------------VENTANA DE LISTADO DE EVENTOS----------------//
// Permite visualizar los eventos registrados y acceder a las acciones de creación de eventos y registro de asistencias.
public class VentanaListaEventos {
  //--------------ATRIBUTOS----------------//
  private BorderPane root;
  private TableView<Evento> tabla;
  private Button btnNuevoEvento;
  private Button btnRegistrarAsistencia;
  private ControladorEventos controlador;

  //----------------METODO CONSTRUCTOR DE LA VISTA----------------//
  // Inicializa los componentes, carga los datos y configura la interfaz.
  public VentanaListaEventos() {
    controlador = new ControladorEventos();
    root = new BorderPane();
    tabla = new TableView<>();
    btnNuevoEvento = new Button("Nuevo Evento");
    btnRegistrarAsistencia = new Button("Registrar Asistencia");
    btnRegistrarAsistencia.setDisable(true);
    
    crearColumnas();
    cargarDatos();
    configurarEventos();
    construirVista();
  }

  //----------------METODO PARA CARGAR LOS DATOS----------------//
  // Carga en la tabla todos los eventos registrados.
  private void cargarDatos() {
    tabla.setItems( FXCollections.observableArrayList(controlador.obtenerEventos()) );
  }

  //----------------METODO PARA CONFIGURAR LOS EVENTOS----------------//
  // Configura las acciones de los botones y el comportamiento de la selección.
  private void configurarEventos() {
    // El botón permanece deshabilitado mientras no exista un evento seleccionado.
    btnRegistrarAsistencia.setDisable(true);

    tabla.getSelectionModel().selectedItemProperty().addListener(
      (obs, anterior, actual) -> {
          boolean sinSeleccion = actual == null;
          btnRegistrarAsistencia.setDisable(sinSeleccion);
      }
    );

    btnNuevoEvento.setOnAction(e -> crearEvento());
    btnRegistrarAsistencia.setOnAction(e -> registrarAsistencia());
  }

  //----------------METODO PARA CREAR UN EVENTO----------------//
  // Abre la ventana de creación de eventos y actualiza la tabla al finalizar.
  private void crearEvento() {
    VentanaCrearEvento ventana = new VentanaCrearEvento();
    ventana.mostrar();
    refrescarTabla();
  }

  //----------------METODO PARA REGISTRAR UNA ASISTENCIA----------------//
  // Abre la ventana de registro de asistencia para el evento seleccionado.
  private void registrarAsistencia() {
    Evento evento = tabla.getSelectionModel().getSelectedItem();
    if (evento == null) {
      return;
    }
    VentanaRegistroAsistenciaEvento ventana = new VentanaRegistroAsistenciaEvento(evento);
    ventana.mostrar();
  }

  //----------------METODO PARA ACTUALIZAR LA TABLA----------------//
  // Recarga los eventos desde el controlador y actualiza la vista.
  private void refrescarTabla() {
    tabla.setItems( FXCollections.observableArrayList(controlador.obtenerEventos()) );
    tabla.refresh();
  }

  //----------------METODO PARA CREAR LAS COLUMNAS----------------//
  // Define las columnas y los datos que se mostrarán en la tabla de eventos.
  private void crearColumnas() {
    TableColumn<Evento, String> colNombre = new TableColumn<>("Nombre");
    colNombre.setCellValueFactory(dato ->
      new SimpleStringProperty(dato.getValue().getNombre())
    );

    TableColumn<Evento, String> colDescripcion = new TableColumn<>("Descripción");
    colDescripcion.setCellValueFactory(dato -> 
      new SimpleStringProperty(dato.getValue().getDescripcion())
    );

    TableColumn<Evento, String> colFecha = new TableColumn<>("Fecha");
    colFecha.setCellValueFactory(dato ->
      new SimpleStringProperty(dato.getValue().getFechaActividad().toString())
    );

    TableColumn<Evento, String> colHora = new TableColumn<>("Hora");
    colHora.setCellValueFactory(dato ->
      new SimpleStringProperty(dato.getValue().getHoraActividad().toString())
    );
    
    TableColumn<Evento, String> colDireccion = new TableColumn<>("Dirección");
    colDireccion.setCellValueFactory(dato ->
      new SimpleStringProperty(dato.getValue().getDireccionActividad())
    );
  
    TableColumn<Evento, String> colEstado = new TableColumn<>("Estado");
    colEstado.setCellValueFactory(dato ->
      new SimpleStringProperty(dato.getValue().getEstado())
    );

    tabla.getColumns().addAll(colNombre, colDescripcion, colFecha, colHora, colDireccion, colEstado);
  }

  //----------------METODO PARA CONSTRUIR LA VISTA----------------//
  // Organiza los componentes visuales dentro del contenedor principal.
  private void construirVista() {
    HBox barra = new HBox(10, btnNuevoEvento, btnRegistrarAsistencia);
    barra.setPadding(new Insets(10));
    root.setTop(barra);
    root.setCenter(tabla);
  }

  //----------------METODO GETTER DEL CONTENEDOR PRINCIPAL----------------//
  // Devuelve la raíz de la vista para ser utilizada desde otras ventanas.
  public BorderPane getRoot() {
    return root;
  }
}
