package ar.edu.utn.frba.dds.modelo.prenda;

import ar.edu.utn.frba.dds.modelo.Formalidad;
import java.util.Objects;

public class Prenda {
  private final TipoDePrenda tipoDePrenda;
  private final Material material;
  private final Color colorPrincipal;
  private final Color colorSecundario;
  private final Trama trama;
  private final Formalidad formalidad;

  public Prenda(TipoDePrenda tipoDePrenda, Material material, Color colorPrincipal, Color colorSecundario, Trama trama, Formalidad formalidad) {
    //si bien valido en el builder, no esta de mas validar en el constructor.
    this.tipoDePrenda = Objects.requireNonNull(tipoDePrenda);
    this.material = Objects.requireNonNull(material);
    this.colorPrincipal = Objects.requireNonNull(colorPrincipal);
    this.colorSecundario = colorSecundario;
    this.trama = Objects.requireNonNull(trama);
    this.formalidad = Objects.requireNonNull(formalidad);
  }

  public Categoria getCategoria() {
    return tipoDePrenda.getCategoria();
  }
}
