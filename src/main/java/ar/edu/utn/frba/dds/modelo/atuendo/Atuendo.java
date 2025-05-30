package ar.edu.utn.frba.dds.modelo.atuendo;

import ar.edu.utn.frba.dds.modelo.clima.Humedad;
import ar.edu.utn.frba.dds.modelo.prenda.Categoria;
import ar.edu.utn.frba.dds.modelo.prenda.Prenda;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class Atuendo {
  private final Prenda prendaSuperior;
  private final Prenda prendaInferior;
  private final Prenda calzado;
  private final List<Prenda> accesorios;

  public Atuendo(
      Prenda prendaSuperior,
      Prenda prendaInferior,
      Prenda calzado,
      List<Prenda> accesorios
  ) {
    if (prendaSuperior.getCategoria() != Categoria.PARTE_SUPERIOR) {
      throw new PrendaNoTieneCategoriaCorrecta(Categoria.PARTE_SUPERIOR, prendaSuperior);
    }
    if (prendaInferior.getCategoria() != Categoria.PARTE_INFERIOR) {
      throw new PrendaNoTieneCategoriaCorrecta(Categoria.PARTE_INFERIOR, prendaInferior);
    }
    if (calzado.getCategoria() != Categoria.CALZADO) {
      throw new PrendaNoTieneCategoriaCorrecta(Categoria.CALZADO, calzado);
    }
    if (accesorios == null || accesorios.isEmpty()) {
      this.accesorios = Collections.emptyList();
    } else {
      accesorios.forEach(accesorio -> {
        Objects.requireNonNull(accesorio);
        if (accesorio.getCategoria() != Categoria.ACCESORIOS) {
          throw new PrendaNoTieneCategoriaCorrecta(Categoria.ACCESORIOS, accesorio);
        }
      });
      this.accesorios = new ArrayList<>(accesorios);
    }

    this.prendaSuperior = Objects.requireNonNull(prendaSuperior);
    this.prendaInferior = Objects.requireNonNull(prendaInferior);
    this.calzado = Objects.requireNonNull(calzado);
  }

  public Atuendo(Prenda prendaSuperior, Prenda prendaInferior, Prenda calzado) {
    this(prendaSuperior, prendaInferior, calzado, Collections.emptyList());
  }

  public boolean esAptoHumedad(Humedad humedad) {
    return true; //no se aclara
  }

  public boolean esAptoTemperatura(int temperaturaActual) {
    boolean noAccesoriosAptos = Stream.of(prendaSuperior, prendaInferior, calzado)
        .allMatch(prenda -> prenda.esAptoTemperatura(temperaturaActual));

    boolean accesoriosAptos = accesorios.stream().allMatch(a -> a.esAptoTemperatura(temperaturaActual));
    return noAccesoriosAptos && accesoriosAptos;
  }
}
