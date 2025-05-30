package ar.edu.utn.frba.dds.modelo.atuendo;

import ar.edu.utn.frba.dds.modelo.prenda.Prenda;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Guardarropas {
  private final List<Prenda> prendasSuperiores = new ArrayList<>();
  private final List<Prenda> prendasInferiores = new ArrayList<>();
  private final List<Prenda> calzados = new ArrayList<>();
  private final List<Prenda> accesorios = new ArrayList<>();

  public Guardarropas() {
  }

  public void agregarPrenda(Prenda prenda) {
    Objects.requireNonNull(prenda);
    switch (prenda.getCategoria()) {
      case PARTE_SUPERIOR:
        prendasSuperiores.add(prenda);
        break;
      case PARTE_INFERIOR:
        prendasInferiores.add(prenda);
        break;
      case CALZADO:
        calzados.add(prenda);
        break;
      case ACCESORIOS:
        accesorios.add(prenda);
        break;
      default:
        break;
    }
  }

  public List<Atuendo> generarCombinacionesPosibles() {
    if (prendasSuperiores.isEmpty() || prendasInferiores.isEmpty() || calzados.isEmpty()) {
      return Collections.emptyList();
    }
    Set<List<Prenda>> combinaciones = Sets.cartesianProduct(
        Set.copyOf(prendasSuperiores),
        Set.copyOf(prendasInferiores),
        Set.copyOf(calzados)
    );

    return combinaciones.stream().map(combinacion ->
        new Atuendo(combinacion.get(0), combinacion.get(1), combinacion.get(2))
    ).toList();
  }


}
