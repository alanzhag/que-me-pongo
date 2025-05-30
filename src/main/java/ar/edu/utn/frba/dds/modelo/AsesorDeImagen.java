package ar.edu.utn.frba.dds.modelo;

import ar.edu.utn.frba.dds.modelo.atuendo.Atuendo;
import ar.edu.utn.frba.dds.modelo.atuendo.Guardarropas;
import ar.edu.utn.frba.dds.modelo.clima.EstadoDelTiempo;
import ar.edu.utn.frba.dds.modelo.clima.Humedad;
import ar.edu.utn.frba.dds.modelo.clima.ServicioMetereologico;
import java.util.List;
import java.util.Objects;

public class AsesorDeImagen {
  private final ServicioMetereologico servicioMetereologico;

  public AsesorDeImagen(ServicioMetereologico servicioMetereologico) {
    this.servicioMetereologico = Objects.requireNonNull(servicioMetereologico);
  }

  public Atuendo sugerirAtuendo(String ciudad, Guardarropas guardarropas) {
    Objects.requireNonNull(ciudad);
    Objects.requireNonNull(guardarropas);

    EstadoDelTiempo estadoDelTiempo = servicioMetereologico.obtenerClima(ciudad);
    int temperaturaActual = estadoDelTiempo.getTemperatura();
    Humedad humedadActual = estadoDelTiempo.getHumedad();

    List<Atuendo> combinaciones = guardarropas.generarCombinacionesPosibles();

    List<Atuendo> atuendosAptos = combinaciones.stream()
        .filter(atuendo -> atuendo.esAptoTemperatura(temperaturaActual))
        .filter(atuendo -> atuendo.esAptoHumedad(humedadActual))
        .toList();

    if (!atuendosAptos.isEmpty()) {
      return atuendosAptos.get(0);
    } else {
      return null;
    }
  }
}
