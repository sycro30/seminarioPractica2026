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
import modelo.ReunionCelula;

//----------------VENTANA DE DETALLE DE REUNIÓN DE CÉLULA----------------//
// Muestra el detalle de las asistencias registradas para una reunión de célula específica.
public class VistaDetalleReunionCelula {
  private ReunionCelula reunion;

  //----------------METODO CONSTRUCTOR----------------//
  // Recibe la reunión de célula cuyo detalle de asistencias será mostrado.
  public VistaDetalleReunionCelula(ReunionCelula reunion) {
    this.reunion = reunion;
  }

  //----------------METODO PARA MOSTRAR LA VENTANA----------------//
  // Construye y muestra una ventana con el detalle de asistencia de todos los miembros registrados en la reunión.
  public void mostrar() {
    if (reunion == null) {
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

    // Columna que muestra el estado de asistencia (Presente o Ausente).
    TableColumn<Asistencia, String> colEstado = new TableColumn<>("Estado");
    colEstado.setCellValueFactory(dato ->
      new SimpleStringProperty(dato.getValue().getEstado())
    );

    // Columna que muestra las observaciones registradas para cada asistencia.
    TableColumn<Asistencia, String> colObservaciones = new TableColumn<>("Observaciones");
    colObservaciones.setCellValueFactory(dato ->
      new SimpleStringProperty(dato.getValue().getObservaciones())
    );

    tabla.getColumns().addAll(colMiembro,colEstado,colObservaciones);

    tabla.setItems( FXCollections.observableArrayList(reunion.getAsistencias()) );
    tabla.setColumnResizePolicy( TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS );

    BorderPane root = new BorderPane();
    root.setPadding(new Insets(10));
    root.setCenter(tabla);

    Scene escena = new Scene(root, 700, 400);

    Stage stage = new Stage();
    stage.setTitle("Detalle Reunión - " + reunion.getCelula().getNombre());
    stage.setScene(escena);
    stage.showAndWait();
  }
}