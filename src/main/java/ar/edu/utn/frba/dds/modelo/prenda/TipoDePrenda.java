package ar.edu.utn.frba.dds.modelo.prenda;

import ar.edu.utn.frba.dds.modelo.Categoria;

public enum TipoDePrenda {
  REMERA(Categoria.CALZADO),
  PANTALON(Categoria.CALZADO),
  ZAPATOS(Categoria.CALZADO),
  ZAPATILLAS(Categoria.CALZADO),
  CAMISA_MANGA_CORTA(Categoria.CALZADO),
  CHOMBA(Categoria.CALZADO);

  private final Categoria categoria;

  private TipoDePrenda(Categoria categoria) {
    this.categoria = categoria;
  }

  public Categoria getCategoria() {
    return categoria;
  }
}
