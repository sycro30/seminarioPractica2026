package vista.historialAsistencias;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import modelo.Asistencia;
import modelo.Evento;

//----------------VENTANA DE DETALLE DE EVENTO----------------//
// Muestra el detalle de las asistencias registradas para un evento, incluyendo miembro, estado de asistencia y observaciones.
public class VistaDetalleEvento {
  
  private Evento evento;

  //----------------METODO CONSTRUCTOR----------------//
  // Recibe el evento del cual se desea visualizar el detalle de asistencias.
  public VistaDetalleEvento(Evento evento) {
    this.evento = evento;
  }

  //----------------METODO PARA MOSTRAR LA VENTANA----------------//
  // Construye y muestra una ventana con la lista de asistencias asociadas al evento seleccionado.
  public void mostrar() {
    if (evento == null) {
      return;
    }

    TableView<Asistencia> tabla = new TableView<>();

    // Columna que muestra el nombre completo del miembro.
    TableColumn<Asistencia, String> colMiembro = new TableColumn<>("Miembro");
    colMiembro.setCellValueFactory(dato -> {
      String nombreCompleto = "Sin miembro";  
      if (dato.getValue().getMiembro() != null) {
        nombreCompleto = dato.getValue().getMiembro().getNombre() + " " + dato.getValue().getMiembro().getApellido();
      }
      return new SimpleStringProperty(nombreCompleto);
    });

    // Columna que muestra el estado de asistencia.
    TableColumn<Asistencia, String> colEstado = new TableColumn<>("Estado");
    colEstado.setCellValueFactory(dato ->
      new SimpleStringProperty(dato.getValue().getEstado())
    );

    // Columna que muestra las observaciones registradas.
    TableColumn<Asistencia, String> colObservaciones = new TableColumn<>("Observaciones");
    colObservaciones.setCellValueFactory(dato ->
      new SimpleStringProperty(dato.getValue().getObservaciones())
    );

    // Agrega las columnas a la tabla.
    tabla.getColumns().addAll(colMiembro, colEstado, colObservaciones);

    // Carga las asistencias registradas para el evento.
    tabla.setItems( FXCollections.observableArrayList(evento.getAsistencias()) );
    
    tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

    BorderPane root = new BorderPane();
    root.setPadding(new Insets(10));
    root.setCenter(tabla);

    Scene escena = new Scene(root, 700, 400);

    Stage stage = new Stage();
    stage.setTitle("Detalle Evento - " + evento.getNombre());
    stage.setScene(escena);
    stage.showAndWait();
  }
}