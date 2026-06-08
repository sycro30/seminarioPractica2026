package vista.reportes.celula;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import modelo.Asistencia;
import modelo.Celula;
import modelo.ReunionCelula;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VentanaDetalleReporteCelula {
  private TableView<ReunionCelula> tabla;

  public VentanaDetalleReporteCelula(
    Celula celula, LocalDate inicioPeriodo1, LocalDate finPeriodo1, LocalDate inicioPeriodo2, LocalDate finPeriodo2
  ) {
    tabla = new TableView<>();
    crearColumnas();
    List<ReunionCelula> reuniones = new ArrayList<>();

    for (ReunionCelula reunion : celula.getReuniones()) {
      boolean periodo1 = reunion.estaEnPeriodo(inicioPeriodo1, finPeriodo1);
      boolean periodo2 = reunion.estaEnPeriodo(inicioPeriodo2, finPeriodo2);
      if (periodo1 || periodo2) {
        reuniones.add(reunion);
      }
    }

    tabla.setItems( FXCollections.observableArrayList(reuniones) );
  }

  private void crearColumnas() {
    TableColumn<ReunionCelula, String> colFecha = new TableColumn<>("Fecha");
    colFecha.setCellValueFactory(dato -> 
      new SimpleStringProperty( dato.getValue().getFechaActividad().toString() )
    );

    TableColumn<ReunionCelula, String> colTema = new TableColumn<>("Tema");
    colTema.setCellValueFactory(dato -> 
      new SimpleStringProperty( dato.getValue().getTema() )
    );

    TableColumn<ReunionCelula, Number> colPresentes = new TableColumn<>("Presentes");
    colPresentes.setCellValueFactory(dato -> {
      int presentes = 0;
      for (Asistencia asistencia : dato.getValue().getAsistencias()) {
        if (asistencia.esPresente()) {
          presentes++;
        }
      }
      return new SimpleIntegerProperty(presentes);
    });

    TableColumn<ReunionCelula, Number> colTotal = new TableColumn<>("Total Registros");
    colTotal.setCellValueFactory(dato -> 
      new SimpleIntegerProperty( dato.getValue().getAsistencias().size() )
    );

    tabla.getColumns().addAll(colFecha, colTema, colPresentes, colTotal);
  }

  public void mostrar() {
    Scene escena = new Scene(tabla, 700, 400);

    Stage stage = new Stage();
    stage.setTitle("Detalle Reporte Célula");
    stage.setScene(escena);
    stage.show();
  }
}