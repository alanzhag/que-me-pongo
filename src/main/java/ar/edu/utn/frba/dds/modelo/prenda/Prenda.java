package ar.edu.utn.frba.dds.modelo.prenda;

import java.util.Objects;

public class Prenda {
  private final TipoDePrenda tipoDePrenda;
  private final Material material;
  private final Color colorPrincipal;
  private final Color colorSecundario;
  private final Trama trama;
  private final Formalidad formalidad;
  private final int temperaturaMaxima;

  public Prenda(TipoDePrenda tipoDePrenda,
                Material material,
                Color colorPrincipal,
                Color colorSecundario,
                Trama trama,
                int temperaturaMaxima,
                Formalidad formalidad
  ) {
    //si bien valido en el builder, no esta de mas validar en el constructor.
    this.tipoDePrenda = Objects.requireNonNull(tipoDePrenda);
    this.material = Objects.requireNonNull(material);
    this.colorPrincipal = Objects.requireNonNull(colorPrincipal);
    this.colorSecundario = colorSecundario;
    this.trama = Objects.requireNonNull(trama);
    this.temperaturaMaxima = temperaturaMaxima;
    this.formalidad = Objects.requireNonNull(formalidad);
  }

  public Categoria getCategoria() {
    return tipoDePrenda.getCategoria();
  }

  public boolean esAptoTemperatura(int temperatura) {
    return temperatura <= this.temperaturaMaxima;
  }

  public boolean esAptoFormalidad(Formalidad formalidad) {
    return this.formalidad == formalidad; // no se aclara como implementar esto
  }
}
