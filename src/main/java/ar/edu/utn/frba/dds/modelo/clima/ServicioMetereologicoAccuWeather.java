package ar.edu.utn.frba.dds.modelo.clima;

import ar.edu.utn.frba.dds.modelo.clima.api.AccuWeatherAPI;
import com.google.common.base.Strings;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ServicioMetereologicoAccuWeather implements ServicioMetereologico {

  private final AccuWeatherAPI api;
  private final Duration periodoDeValidez;
  private final Map<String, RespuestaMeteorologica> ultimasRespuestas;

  public ServicioMetereologicoAccuWeather(
      AccuWeatherAPI api,
      Duration periodoDeValidez,
      Map<String, RespuestaMeteorologica> ultimasRespuestas
  ) {
    this.api = Objects.requireNonNull(api);
    this.periodoDeValidez = Objects.requireNonNull(periodoDeValidez);
    this.ultimasRespuestas = new HashMap<>();
  }

  @Override
  public EstadoDelTiempo obtenerClima(String ciudad) {
    if (Strings.isNullOrEmpty(ciudad)) {
      throw new IllegalArgumentException("Ciudad no válida.");
    }
    if (!ultimasRespuestas.containsKey(ciudad) || ultimasRespuestas.get(ciudad).expiro()) {
      //no hay dato valido en cache
      EstadoDelTiempo nuevoEstado = consultarApi(ciudad);
      ultimasRespuestas.put(ciudad, new RespuestaMeteorologica(nuevoEstado, proximaExpiracion()));
    }
    return ultimasRespuestas.get(ciudad).getEstadoDelTiempo();
  }

  private EstadoDelTiempo consultarApi(String ciudad) {
    List<Map<String, Object>> respuestaApi = this.api.getWeather(ciudad);

    if (respuestaApi == null || respuestaApi.isEmpty()) {
      throw new RuntimeException("No hay respuesta de AccuWeather para: " + ciudad);
    }
    //mapeo de ejemplo. enunciado no aclara.
    Map<String, Object> datosClimaticos = respuestaApi.get(0);

    Integer temperaturaObj = (Integer) datosClimaticos.get("Temperature");
    Double precipitacionProbObj = (Double) datosClimaticos.get("PrecipitationProbability");

    if (temperaturaObj == null || precipitacionProbObj == null) {
      throw new RuntimeException("Respuesta de AccuWeather incompleta");
    }

    int temperatura = temperaturaObj;
    Humedad humedad = precipitacionProbObj > 0.8 ? Humedad.LLUVIOSO : Humedad.SECO;

    return new EstadoDelTiempo(temperatura, humedad);
  }

  private LocalDateTime proximaExpiracion() {
    return LocalDateTime.now().plus(this.periodoDeValidez);
  }


}
