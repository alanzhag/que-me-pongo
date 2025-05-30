package ar.edu.utn.frba.dds.modelo.asesor.motor;

import ar.edu.utn.frba.dds.modelo.atuendo.Atuendo;
import java.util.List;

public class PrimeroQueEncuentra extends MotorSugerencias {

  @Override
  public Atuendo estrategia(List<Atuendo> atuendosAptos) {
    return atuendosAptos.get(0);
  }
}
