package vista.historialAsistencias;

import controlador.historialAsistencias.ControladorHistorialAsistencias;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import modelo.Celula;

//----------------VENTANA DE HISTORIAL DE ASISTENCIAS----------------//
// Permite consultar el historial de reuniones de una célula o los eventos generales registrados.
public class VentanaHistorialAsistencias {
  //--------------ATRIBUTOS----------------//
  private BorderPane root;
  private ComboBox<Object> comboSeleccion;
  private VBox contenedorTabla;
  private ControladorHistorialAsistencias controlador;

  //----------------METODO CONSTRUCTOR DE LA VISTA----------------//
  // Inicializa los componentes de la ventana, carga los datos disponibles y configura los eventos de la interfaz.
  public VentanaHistorialAsistencias() {
    controlador = new ControladorHistorialAsistencias();
    root = new BorderPane();
    comboSeleccion = new ComboBox<>();
    contenedorTabla = new VBox();

    crearVista();
    cargarDatos();
    configurarEventos();
  }

  //----------------METODO PARA CONSTRUIR LA VISTA----------------//
  // Crea la estructura principal de la ventana con el selector y el contenedor donde se mostrará la información consultada.
  private void crearVista() {
    VBox superior = new VBox(10);
    superior.setPadding(new Insets(15));
    superior.getChildren().addAll( new Label("Seleccionar célula o eventos"), comboSeleccion );
    root.setTop(superior);
    root.setCenter(contenedorTabla);
  }

  //----------------METODO PARA CARGAR LOS DATOS----------------//
  // Carga en el ComboBox todas las células registradas y la opción para visualizar los eventos generales.
  private void cargarDatos() {
    comboSeleccion.getItems().addAll(controlador.obtenerCelulas());
    comboSeleccion.getItems().add("Eventos Generales");
  }

  //----------------METODO PARA CONFIGURAR LOS EVENTOS----------------//
  // Asocia la acción de cambio de selección para actualizar automáticamente la información mostrada.
  private void configurarEventos() {
    comboSeleccion.setOnAction(e -> actualizarVista());
  }

  //----------------METODO PARA ACTUALIZAR LA VISTA----------------//
  // Muestra la tabla correspondiente según el elemento seleccionado: una tabla de reuniones de célula o una tabla de eventos generales.
  private void actualizarVista() {
    Object seleccionado = comboSeleccion.getValue();
    if (seleccionado instanceof Celula celula) {
      TablaReunionesCelula tabla = new TablaReunionesCelula( controlador.obtenerReunionesDeCelula(celula) );
      contenedorTabla.getChildren().setAll(tabla.getRoot());
    } else if ("Eventos Generales".equals(seleccionado)) {
      TablaEventos tabla = new TablaEventos(controlador.obtenerEventos());
      contenedorTabla.getChildren().setAll(tabla.getRoot());
    }
  }

  //----------------METODO GETTER DEL CONTENEDOR PRINCIPAL----------------//
  // Devuelve el nodo raíz de la ventana para ser integrado en otras vistas del sistema.
  public BorderPane getRoot() {
    return root;
  }
}