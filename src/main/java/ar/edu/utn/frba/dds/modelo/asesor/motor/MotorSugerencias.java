package ar.edu.utn.frba.dds.modelo.asesor.motor;

import ar.edu.utn.frba.dds.modelo.atuendo.Atuendo;
import java.util.List;
import java.util.Optional;

public abstract class MotorSugerencias {
  public Optional<Atuendo> elegir(List<Atuendo> atuendosAptos) {
    if (atuendosAptos.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(estrategia(atuendosAptos));
  }

  protected abstract Atuendo estrategia(List<Atuendo> atuendosAptos);
}
