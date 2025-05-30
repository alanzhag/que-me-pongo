package ar.edu.utn.frba.dds.modelo.prenda;

public class Color {
  private final int rojo;
  private final int verde;
  private final int azul;

  public Color(int rojo, int verde, int azul) {
    if (rojo < 0 || rojo > 255 || verde < 0 || verde > 255 || azul < 0 || azul > 255) {
      throw new IllegalArgumentException("Los parámetros RGB deben ser ser enteros entre 0 y 255");
    }
    this.rojo = rojo;
    this.verde = verde;
    this.azul = azul;
  }
}
