package vista.reportes.celula;

import modelo.Celula;

public class ReporteCelula {
  private Celula celula;
  private String nombreCelula;
  private double promedioPeriodo1;
  private double promedioPeriodo2;
  private double variacion;
  private double participacion;

  public ReporteCelula(Celula celula, double promedioPeriodo1, double promedioPeriodo2, double variacion, double participacion) {
    this.celula = celula;
    this.nombreCelula = celula.getNombre();
    this.promedioPeriodo1 = promedioPeriodo1;
    this.promedioPeriodo2 = promedioPeriodo2;
    this.variacion = variacion;
    this.participacion = participacion;
  }

  public Celula getCelula() {
    return celula;
  }

  public String getNombreCelula() {
    return nombreCelula;
  }

  public double getPromedioPeriodo1() {
    return promedioPeriodo1;
  }

  public double getPromedioPeriodo2() {
    return promedioPeriodo2;
  }

  public double getVariacion() {
    return variacion;
  }

  public double getParticipacion() {
    return participacion;
  }
}