package ar.edu.utn.frba.dds.modelo.prenda;

import java.util.Objects;

// esto es un builder
public class Borrador {
  private TipoDePrenda tipoDePrenda;
  private Material material;
  private Color colorPrincipal;
  private Color colorSecundario;
  private Trama trama = Trama.LISA;
  private Formalidad formalidad = Formalidad.NEUTRA;

  public Borrador conTipoDePrenda(TipoDePrenda tipoDePrenda) {
    this.tipoDePrenda = Objects.requireNonNull(tipoDePrenda);
    return this;
  }

  public Borrador conColorPrincipal(Color color) {
    this.colorPrincipal = Objects.requireNonNull(color);
    return this;
  }

  public Borrador conColorSecundario(Color color) {
    this.colorSecundario = Objects.requireNonNull(color);
    return this;
  }

  public Borrador conMaterial(Material material) {
    this.material = Objects.requireNonNull(material);
    // no se como implementar esto
    // tal vez un mapa con las posibles asignaciones pero sería dificil de mantener
    //if (!this.tipoDePrenda.esMaterialConsistente(material)) {
    //  throw new IllegalArgumentException("El material y el tipo de prenda no son consistentes");
    //}
    return this;
  }

  public Borrador conFormalidad(Formalidad formalidad) {
    this.formalidad = Objects.requireNonNull(formalidad);
    return this;
  }

  public Borrador conTrama(Trama trama) {
    this.trama = Objects.requireNonNull(trama);
    return this;
  }

  public Prenda crearPrenda() {
    if (this.tipoDePrenda == null
        || this.material == null
        || this.colorPrincipal == null
        || this.trama == null
        || this.formalidad == null) { // los obligatorios
      throw new IllegalStateException("Faltan atributos obligatorios pra crear la prenda.");
    }

    return new Prenda(
        this.tipoDePrenda,
        this.material,
        this.colorPrincipal,
        this.colorSecundario,
        this.trama, this.formalidad
    );
  }
}
