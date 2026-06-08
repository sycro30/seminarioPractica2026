package vista.historialAsistencias;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import modelo.Asistencia;
import modelo.Evento;

import java.util.List;

//----------------TABLA DE EVENTOS----------------//
/* Muestra el listado de eventos registrados junto con información relevante como fecha, estado, 
 cantidad de asistentes y acceso al detalle de asistencia de cada evento. */
public class TablaEventos {
  //--------------ATRIBUTOS----------------//
  private BorderPane root;
  private TableView<Evento> tabla;

  //----------------METODO CONSTRUCTOR----------------//
  // Inicializa la tabla, crea sus columnas y carga los eventos recibidos.
  public TablaEventos(List<Evento> eventos) {
    root = new BorderPane();
    tabla = new TableView<>();
    crearColumnas();
    tabla.getItems().addAll(eventos);
    root.setPadding(new Insets(10));
    root.setCenter(tabla);
  }

  //----------------METODO PARA CREAR LAS COLUMNAS----------------//
  // Define las columnas de la tabla y la información que se mostrará para cada evento.
  private void crearColumnas() {
    // Columna que muestra el nombre del evento.
    TableColumn<Evento, String> colNombre = new TableColumn<>("Evento");
    colNombre.setCellValueFactory(dato ->
      new SimpleStringProperty(dato.getValue().getNombre())
    );

    // Columna que muestra la fecha programada del evento.
    TableColumn<Evento, String> colFecha = new TableColumn<>("Fecha");
    colFecha.setCellValueFactory(dato ->
      new SimpleStringProperty(dato.getValue().getFechaActividad().toString())
    );

    // Columna que muestra el estado actual del evento.
    TableColumn<Evento, String> colEstado = new TableColumn<>("Estado");
    colEstado.setCellValueFactory(dato ->
      new SimpleStringProperty(dato.getValue().getEstado())
    );

    // Columna que muestra la cantidad de asistentes presentes.
    TableColumn<Evento, Number> colAsistentes = new TableColumn<>("Asistentes");
    colAsistentes.setCellValueFactory(dato -> {
      int presentes = 0;
      for (Asistencia asistencia : dato.getValue().getAsistencias()) {
        if (asistencia.esPresente()) {
          presentes++;
        }
      }
      return new SimpleIntegerProperty(presentes);
    });

    // Columna con un botón para visualizar el detalle de asistencia del evento seleccionado.
    TableColumn<Evento, Void> colDetalle = new TableColumn<>("Detalle");
    colDetalle.setCellFactory(param -> new javafx.scene.control.TableCell<>() {
      private final Button btn = new Button("Ver");
      {
        btn.setOnAction(event -> {
          Evento eventoSeleccionado = getTableView().getItems().get(getIndex());
          VistaDetalleEvento ventana = new VistaDetalleEvento(eventoSeleccionado);
          ventana.mostrar();
        });
      }

      @Override
      protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
          setGraphic(null);
        } else {
          setGraphic(btn);
        }
      }
    });

    tabla.getColumns().addAll(
      colNombre,
      colFecha,
      colEstado,
      colAsistentes,
      colDetalle
    );

    tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
  }

  public BorderPane getRoot() {
    return root;
  }
}