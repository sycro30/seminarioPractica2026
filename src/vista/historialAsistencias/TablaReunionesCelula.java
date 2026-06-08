package vista.historialAsistencias;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import modelo.Asistencia;
import modelo.ReunionCelula;

import java.util.List;

//----------------TABLA DE REUNIONES DE CÉLULA----------------//
/* Muestra el historial de reuniones de una célula junto con la información principal de cada reunión y 
 permite acceder al detalle de asistencia. */
public class TablaReunionesCelula {
  private BorderPane root;
  private TableView<ReunionCelula> tabla;

  //----------------METODO CONSTRUCTOR----------------//
  // Inicializa la tabla, crea las columnas y carga las reuniones recibidas.
  public TablaReunionesCelula(List<ReunionCelula> reuniones) {
    root = new BorderPane();
    tabla = new TableView<>();
    crearColumnas();
    tabla.getItems().addAll(reuniones);
    root.setPadding(new Insets(10));
    root.setCenter(tabla);
  }

  //----------------METODO PARA CREAR LAS COLUMNAS----------------//
  // Define las columnas que mostrarán la información de cada reunión.
  private void crearColumnas() {
    // Columna que muestra la fecha de la reunión.
    TableColumn<ReunionCelula, String> colFecha = new TableColumn<>("Fecha");
    colFecha.setCellValueFactory(dato ->
      new SimpleStringProperty( dato.getValue().getFechaActividad().toString() )
    );

    // Columna que muestra el tema tratado en la reunión.
    TableColumn<ReunionCelula, String> colTema = new TableColumn<>("Tema");
    colTema.setCellValueFactory(dato ->
      new SimpleStringProperty( dato.getValue().getTema() )
    );

    // Columna que muestra las observaciones registradas.
    TableColumn<ReunionCelula, String> colObservaciones = new TableColumn<>("Observaciones");
    colObservaciones.setCellValueFactory(dato ->
      new SimpleStringProperty( dato.getValue().getObservaciones() )
    );

    // Columna que muestra la cantidad de asistentes presentes.
    TableColumn<ReunionCelula, Number> colAsistentes = new TableColumn<>("Asistentes");
    colAsistentes.setCellValueFactory(dato -> {
      int presentes = 0;
      for (Asistencia asistencia : dato.getValue().getAsistencias()) {
        if (asistencia.esPresente()) {
          presentes++;
        }
      }
      return new SimpleIntegerProperty(presentes);
    });

    // Columna con un botón para visualizar el detalle completo de asistencia de la reunión seleccionada.
    TableColumn<ReunionCelula, Void> colDetalle = new TableColumn<>("Detalle");
    colDetalle.setCellFactory(param -> new javafx.scene.control.TableCell<>() {
      private Button btn = new Button("Ver");
      {
        btn.setOnAction(event -> {
          ReunionCelula reunion = getTableView().getItems().get(getIndex());
          VistaDetalleReunionCelula ventana = new VistaDetalleReunionCelula(reunion);
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

    // Agrega todas las columnas a la tabla.
    tabla.getColumns().addAll(colFecha, colTema, colObservaciones, colAsistentes, colDetalle);

    // Ajusta automáticamente el ancho de las columnas disponibles.
    tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
  }

  public BorderPane getRoot() {
    return root;
  }
}