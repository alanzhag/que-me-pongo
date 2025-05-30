package ar.edu.utn.frba.dds.modelo.asesor;

import ar.edu.utn.frba.dds.modelo.asesor.motor.MotorSugerencias;
import ar.edu.utn.frba.dds.modelo.atuendo.Atuendo;
import ar.edu.utn.frba.dds.modelo.atuendo.Guardarropas;
import ar.edu.utn.frba.dds.modelo.clima.EstadoDelTiempo;
import ar.edu.utn.frba.dds.modelo.clima.Humedad;
import ar.edu.utn.frba.dds.modelo.clima.ServicioMetereologico;
import ar.edu.utn.frba.dds.modelo.prenda.Formalidad;
import ar.edu.utn.frba.dds.modelo.roles.Config;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class AsesorDeImagen {
  private final ServicioMetereologico servicioMetereologico;
  private final Config config;
  private final MotorSugerencias motorSugerencias;

  public AsesorDeImagen(ServicioMetereologico servicioMetereologico, Config config, MotorSugerencias motorSugerencias) {
    this.servicioMetereologico = Objects.requireNonNull(servicioMetereologico);
    this.config = config;
    this.motorSugerencias = motorSugerencias;
  }

  public Optional<Atuendo> sugerirAtuendo(String ciudad, int edadUsuario, Guardarropas guardarropas) {
    Objects.requireNonNull(ciudad);
    Objects.requireNonNull(guardarropas);

    EstadoDelTiempo estadoDelTiempo = servicioMetereologico.obtenerClima(ciudad);
    int temperaturaActual = estadoDelTiempo.getTemperatura();
    Humedad humedadActual = estadoDelTiempo.getHumedad();

    List<Atuendo> combinaciones = guardarropas.generarCombinacionesPosibles();

    Stream<Atuendo> streamDeAtuendos = combinaciones.stream();
    //filtros de ejemplo
    //no se aclara criterio para filtrar por formalidad
    streamDeAtuendos = streamDeAtuendos
        .filter(atuendo -> atuendo.esAptoTemperatura(temperaturaActual))
        .filter(atuendo -> atuendo.esAptoHumedad(humedadActual));

    if (config.ejecutarLogicaAdicionalEnSugerencias()) {
      if (config.filtroInformalMayores55() && edadUsuario > 55) {
        streamDeAtuendos = streamDeAtuendos
            .filter(atuendo -> !atuendo.esAptoFormalidad(Formalidad.INFORMAL));
      }
    }

    List<Atuendo> atuendosAptos = streamDeAtuendos.toList();

    return motorSugerencias.elegir(atuendosAptos);
  }
}
