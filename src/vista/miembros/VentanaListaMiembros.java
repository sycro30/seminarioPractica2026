package vista.miembros;

import controlador.miembros.ControladorMiembros;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import modelo.Lider;
import modelo.Miembro;

//----------------VENTANA DE LISTA DE MIEMBROS----------------//
// Permite visualizar, agregar, modificar y eliminar miembros registrados en el sistema.
public class VentanaListaMiembros {
  //--------------ATRIBUTOS----------------//
  private BorderPane root;
  private TableView<Miembro> tabla;
  private Button btnAgregar;
  private Button btnActualizar;
  private Button btnEliminar;
  private ControladorMiembros controlador;

  //----------------METODO CONSTRUCTOR DE LA VISTA----------------//
  /* Inicializa los componentes de la ventana, configura la tabla, carga los datos de los miembros y construye la interfaz. */
  public VentanaListaMiembros() {
    controlador = new ControladorMiembros();
    root = new BorderPane();
    tabla = new TableView<>();
    btnAgregar = new Button("Agregar");
    btnActualizar = new Button("Actualizar");
    btnEliminar = new Button("Eliminar");
    btnActualizar.setDisable(true);
    btnEliminar.setDisable(true);
    
    crearColumnas();
    cargarDatos();
    configurarEventos();
    construirVista();
  }

  //----------------METODO PARA CREAR LAS COLUMNAS DE LA TABLA----------------//
  // Configura las columnas utilizadas para mostrar la información de los miembros registrados.
  private void crearColumnas() {
    TableColumn<Miembro, String> colNombre = new TableColumn<>("Nombre");
    colNombre.setCellValueFactory(dato ->
      new SimpleStringProperty(dato.getValue().getNombre())
    );
    TableColumn<Miembro, String> colApellido = new TableColumn<>("Apellido");
    colApellido.setCellValueFactory(dato ->
      new SimpleStringProperty(dato.getValue().getApellido())
    );

    TableColumn<Miembro, String> colTelefono = new TableColumn<>("Teléfono");
    colTelefono.setCellValueFactory(dato ->
      new SimpleStringProperty(dato.getValue().getTelefono())
    );

    // Columna que muestra el nombre de la célula a la que pertenece el miembro.
    TableColumn<Miembro, String> colCelula = new TableColumn<>("Célula");
    colCelula.setCellValueFactory(dato -> {
      if (dato.getValue().getCelula() == null) {
        return new SimpleStringProperty("");
      }
      return new SimpleStringProperty(dato.getValue().getCelula().getNombre());
    });

    // Columna que determina si el miembro posee el rol de líder o miembro.
    TableColumn<Miembro, String> colRol = new TableColumn<>("Rol");
    colRol.setCellValueFactory(dato -> {
      Miembro miembro = dato.getValue();
      if (miembro.getCelula() == null) {
        return new SimpleStringProperty("Sin célula");
      }

      Lider lider = miembro.getCelula().getLider();
      if (lider != null && lider.getMiembro() == miembro) {
        return new SimpleStringProperty("Líder");
      }
      return new SimpleStringProperty("Miembro");
    });

    tabla.getColumns().addAll(colNombre, colApellido, colTelefono, colCelula, colRol);
  }

  //----------------METODO PARA CARGAR LOS DATOS----------------//
  // Obtiene los miembros registrados y los carga en la tabla.
  private void cargarDatos() {
    tabla.setPlaceholder(new Label("No existen miembros registrados."));
    tabla.setItems( FXCollections.observableArrayList(controlador.obtenerMiembros()) );  
  }

  //----------------METODO PARA CONFIGURAR LOS EVENTOS----------------//
  // Asocia las acciones de los botones con las operaciones de alta, modificación y eliminación de miembros.
  private void configurarEventos() {
    btnAgregar.setOnAction(e -> agregarMiembro());
    btnActualizar.setOnAction(e -> actualizarMiembro());
    btnEliminar.setOnAction(e -> eliminarMiembro());
    
    tabla.getSelectionModel().selectedItemProperty().addListener(
      (obs, anterior, actual) -> {
        boolean sinSeleccion = actual == null;
        btnActualizar.setDisable(sinSeleccion);
        btnEliminar.setDisable(sinSeleccion);
      }
    );
  }

  //----------------METODO PARA AGREGAR UN MIEMBRO----------------//
  // Abre el formulario en modo alta y actualiza la tabla al finalizar.
  private void agregarMiembro() {
    VentanaFormularioMiembro ventana = new VentanaFormularioMiembro(ModoVentanaMiembros.ALTA, null);
    ventana.mostrar();
    refrescarTabla();
  }

  //----------------METODO PARA ACTUALIZAR UN MIEMBRO----------------//
  // Abre el formulario en modo modificación para el miembro seleccionado y actualiza la tabla al finalizar.
  private void actualizarMiembro() {
    Miembro seleccionado = tabla.getSelectionModel().getSelectedItem();
    if (seleccionado == null) {
      return;
    }
    VentanaFormularioMiembro ventana = new VentanaFormularioMiembro(ModoVentanaMiembros.MODIFICACION, seleccionado);
    ventana.mostrar();
    refrescarTabla();
  }

  //----------------METODO PARA ELIMINAR UN MIEMBRO----------------//
  // Elimina el miembro seleccionado y actualiza la tabla de resultados.
  private void eliminarMiembro() {
    Miembro seleccionado = tabla.getSelectionModel().getSelectedItem();
    if (seleccionado == null) {
      return;
    }
    controlador.eliminarMiembro(seleccionado);
    refrescarTabla();
  }

  //----------------METODO PARA REFRESCAR LA TABLA----------------//
  // Recarga los datos de los miembros y actualiza la visualización de la tabla.
  private void refrescarTabla() {
    tabla.getSelectionModel().clearSelection();
    tabla.setItems( FXCollections.observableArrayList(controlador.obtenerMiembros()) );
    tabla.refresh();
  }

  //----------------METODO PARA CONSTRUIR LA VISTA----------------//
  // Organiza los componentes visuales de la ventana, incluyendo la barra de acciones y la tabla de miembros.
  private void construirVista() {
    HBox barraBotones = new HBox(10, btnAgregar, btnActualizar, btnEliminar);
    barraBotones.setPadding(new Insets(10));
    root.setTop(barraBotones);
    root.setCenter(tabla);
  }

  //----------------METODO GETTER DEL CONTENEDOR PRINCIPAL----------------//
  // Devuelve el contenedor principal de la vista.
  public BorderPane getRoot() {
    return root;
  }
}
