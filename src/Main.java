import datos.InicializarDatos;

import javafx.application.Platform;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import vista.celulas.VentanaListaCelulas;
import vista.eventos.VentanaListaEventos;
import vista.historialAsistencias.VentanaHistorialAsistencias;
import vista.miembros.VentanaListaMiembros;
import vista.reportes.VentanaPrincipalReportes;

public class Main extends Application {
  private BorderPane root;

  @Override
  public void start(Stage stage) {
    InicializarDatos.cargarDatos();

    root = new BorderPane();
    root.setLeft(crearMenuLateral());
    root.setCenter(crearDashboard());

    Scene scene = new Scene(root, 1300, 750);
    stage.setTitle("Sistema Gestión MCI Ushuaia");
    stage.setScene(scene);
    stage.show();
  }

  // MENÚ LATERAL
  private VBox crearMenuLateral() {
    Label titulo = new Label("MCI USHUAIA");
    titulo.setStyle("""
      -fx-text-fill: white;
      -fx-font-size: 20px;
      -fx-font-weight: bold;
    """);

    Button btnInicio = crearBotonMenu("🏠 Inicio");
    Button btnMiembros = crearBotonMenu("👥 Miembros");
    Button btnCelulas = crearBotonMenu("🏡 Células");
    Button btnEventos = crearBotonMenu("📅 Eventos");
    Button btnAsistencias = crearBotonMenu("✅ Historial Asistencias");
    Button btnReportes = crearBotonMenu("📊 Reportes");
    Button btnSalir = crearBotonMenu("🚪 Salir");

    // EVENTOS
    btnInicio.setOnAction( e -> root.setCenter(crearDashboard()) );
    btnMiembros.setOnAction( e -> mostrarVistaMiembros() );
    btnCelulas.setOnAction( e -> mostrarVistaCelulas() );
    btnEventos.setOnAction( e -> mostrarVistaEventos() );
    btnAsistencias.setOnAction( e -> mostrarVistaHistorialAsistencia() );
    btnReportes.setOnAction( e -> mostrarVistaReportes() );
    btnSalir.setOnAction( e -> Platform.exit() );

    VBox menu = new VBox(15);
    menu.getChildren().addAll(
      titulo, new Separator(), btnInicio, btnMiembros, btnCelulas, btnEventos, btnAsistencias, btnReportes, btnSalir
    );
    menu.setPadding(new Insets(20));
    menu.setPrefWidth(250);
    menu.setStyle("""
      -fx-background-color: #1F2A40;
    """);
    return menu;
  }

// MIEMBROS
  private void mostrarVistaMiembros() {
    VentanaListaMiembros vista = new VentanaListaMiembros();
    root.setCenter(vista.getRoot());
  }

// CÉLULAS
  private void mostrarVistaCelulas() {
    VentanaListaCelulas vista = new VentanaListaCelulas();
    root.setCenter(vista.getRoot());
  }

// EVENTOS
  private void mostrarVistaEventos() {
    VentanaListaEventos vista = new VentanaListaEventos();
    root.setCenter(vista.getRoot()); 
  }

// HISTORIAL ASISTENCIAS
  private void mostrarVistaHistorialAsistencia() {
    VentanaHistorialAsistencias vista = new VentanaHistorialAsistencias();
    root.setCenter(vista.getRoot());
  }

// REPORTES
  private void mostrarVistaReportes() {
    VentanaPrincipalReportes vista = new VentanaPrincipalReportes();
    root.setCenter(vista.getRoot());
  }

// DASHBOARD
  private VBox crearDashboard() {
    Label titulo = new Label("Inicio - Resumen General");
    titulo.setStyle("""
      -fx-font-size: 28px;
      -fx-font-weight: bold;
    """);

    VBox card1 = crearCard("👥 Total Miembros", "120");
    VBox card2 = crearCard("🏡 Células Activas", "12");
    VBox card3 = crearCard("📅 Eventos Realizados", "18");
    VBox card4 = crearCard("📊 Promedio Asistencia", "78%");

    HBox cards = new HBox(20, card1, card2, card3, card4);
    VBox contenido = new VBox(30);
    contenido.getChildren().addAll(titulo, cards);
    contenido.setPadding(new Insets(30));

    return contenido;
  }

// CARDS
  private VBox crearCard(String titulo, String valor) {
    Label lblTitulo = new Label(titulo);
    lblTitulo.setStyle("""
      -fx-font-size: 16px;
      -fx-text-fill: #555;
    """);

    Label lblValor = new Label(valor);
    lblValor.setStyle("""
      -fx-font-size: 30px;
      -fx-font-weight: bold;
    """);

    VBox card = new VBox(15, lblTitulo, lblValor);
    card.setPadding(new Insets(20));
    card.setPrefWidth(220);
    card.setStyle("""
      -fx-background-color: white;
      -fx-background-radius: 10;
      -fx-border-radius: 10;
      -fx-border-color: #DDD;
    """);

    return card;
  }

// BOTONES MENU
  private Button crearBotonMenu(String texto) {
    Button btn = new Button(texto);
    btn.setMaxWidth(Double.MAX_VALUE);
    btn.setPrefHeight(45);
    btn.setStyle("""
      -fx-background-color: transparent;
      -fx-text-fill: white;
      -fx-font-size: 15px;
      -fx-alignment: CENTER-LEFT;
      -fx-cursor: hand;
    """);
    btn.setOnMouseEntered(e ->
      btn.setStyle("""
        -fx-background-color: #32415E;
        -fx-text-fill: white;
        -fx-font-size: 15px;
        -fx-alignment: CENTER-LEFT;
        -fx-cursor: hand;
      """)
    );

  btn.setOnMouseExited(e ->
    btn.setStyle("""
      -fx-background-color: transparent;
      -fx-text-fill: white;
      -fx-font-size: 15px;
      -fx-alignment: CENTER-LEFT;
      -fx-cursor: hand;
    """)
  );
  return btn;
}

  public static void main(String[] args) {
    launch();
  }
}
