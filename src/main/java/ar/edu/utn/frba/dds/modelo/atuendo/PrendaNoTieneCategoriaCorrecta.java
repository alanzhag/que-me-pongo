package ar.edu.utn.frba.dds.modelo.atuendo;

import ar.edu.utn.frba.dds.modelo.Categoria;
import ar.edu.utn.frba.dds.modelo.prenda.Prenda;

public class PrendaNoTieneCategoriaCorrecta extends IllegalArgumentException {
  public PrendaNoTieneCategoriaCorrecta(Categoria esperada, Prenda recibida) {
    super("La prenda debe ser de categoría: " + esperada.name() + ", pero se asignó una " + recibida.getCategoria().name());
  }
}
