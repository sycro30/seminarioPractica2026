package vista.reportes.evento;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import modelo.Asistencia;
import modelo.Evento;

//----------------VENTANA DE DETALLE DE ASISTENCIAS DE UN EVENTO----------------//
// Permite visualizar el detalle de las asistencias registradas para un evento específico, mostrando miembro, fecha, estado y observaciones.
public class VentanaDetalleAsistenciaEvento {
  private Evento evento;
  private TableView<Asistencia> tabla;

  //----------------METODO CONSTRUCTOR DE LA VISTA----------------//
  // Inicializa la tabla de asistencias y carga los registros asociados al evento seleccionado.
  public VentanaDetalleAsistenciaEvento(Evento evento) {
    this.evento = evento;
    tabla = new TableView<>();
    crearColumnas();
    tabla.setItems( FXCollections.observableArrayList(evento.getAsistencias()) );
  }

  //----------------METODO PARA CREAR LAS COLUMNAS DE LA TABLA----------------//
  // Configura las columnas que mostrará la información de las asistencias registradas para el evento.
  private void crearColumnas() {
    TableColumn<Asistencia, String> colMiembro = new TableColumn<>("Miembro");
    colMiembro.setCellValueFactory(dato -> {
      String nombre = dato.getValue().getMiembro().getNombre() +" "+ dato.getValue().getMiembro().getApellido();
      return new SimpleStringProperty(nombre);
    });

    TableColumn<Asistencia, String> colFecha = new TableColumn<>("Fecha");
    colFecha.setCellValueFactory(dato -> 
      new SimpleStringProperty(dato.getValue().getFechaAsistencia().toString())
    );

    TableColumn<Asistencia, String> colEstado = new TableColumn<>("Estado");
    colEstado.setCellValueFactory(dato -> 
      new SimpleStringProperty(dato.getValue().getEstado())
    );

    TableColumn<Asistencia, String> colObservaciones = new TableColumn<>("Observaciones");
    colObservaciones.setCellValueFactory(dato -> 
      new SimpleStringProperty(dato.getValue().getObservaciones())
    );

    tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    tabla.getColumns().addAll(colMiembro, colFecha, colEstado, colObservaciones);
  }

  //----------------METODO PARA MOSTRAR LA VENTANA----------------//
  // Construye y muestra la ventana con el detalle de las asistencias correspondientes al evento seleccionado.
  public void mostrar() {
    Scene escena = new Scene(tabla, 800, 400);
    Stage stage = new Stage();
    stage.setTitle("Detalle de Asistencias - " + evento.getNombre());
    stage.setScene(escena);
    stage.show();
  }
}