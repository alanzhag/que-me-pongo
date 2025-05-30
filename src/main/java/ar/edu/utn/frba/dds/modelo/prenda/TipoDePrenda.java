package ar.edu.utn.frba.dds.modelo.prenda;

public enum TipoDePrenda {
  BLUSA(Categoria.PARTE_SUPERIOR),
  CAMISA_MANGA_CORTA(Categoria.PARTE_SUPERIOR),
  CHOMBA(Categoria.PARTE_SUPERIOR),
  MUSCULOSA(Categoria.PARTE_SUPERIOR),
  REMERA(Categoria.PARTE_SUPERIOR),
  PANTALON(Categoria.PARTE_INFERIOR),
  POLLERA(Categoria.PARTE_INFERIOR),
  ZAPATILLAS(Categoria.CALZADO),
  ZAPATOS(Categoria.CALZADO);

  private final Categoria categoria;

  TipoDePrenda(Categoria categoria) {
    this.categoria = categoria;
  }

  public Categoria getCategoria() {
    return categoria;
  }
}
