package ar.edu.utn.frba.dds.modelo.clima;

public class EstadoDelTiempo {
  private final int temperatura;
  private final Humedad humedad;

  public EstadoDelTiempo(int temperatura, Humedad humedad) {
    this.temperatura = temperatura;
    this.humedad = humedad;
  }
}
