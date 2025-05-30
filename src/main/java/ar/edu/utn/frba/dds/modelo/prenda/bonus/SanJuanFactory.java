package ar.edu.utn.frba.dds.modelo.prenda.bonus;

import ar.edu.utn.frba.dds.modelo.prenda.Borrador;
import ar.edu.utn.frba.dds.modelo.prenda.Color;
import ar.edu.utn.frba.dds.modelo.prenda.Material;
import ar.edu.utn.frba.dds.modelo.prenda.Prenda;
import ar.edu.utn.frba.dds.modelo.prenda.TipoDePrenda;
import ar.edu.utn.frba.dds.modelo.prenda.Trama;

public class SanJuanFactory implements EstablecimientoAbstractFactory {
  private final Color colorChomba = new Color(0, 255, 0);
  private final Color colorPantalon = new Color(155, 155, 155);
  private final Color colorZapatillas = new Color(255, 255, 255);

  @Override
  public Prenda fabricarParteSuperior() {
    return new Borrador()
        .conTipoDePrenda(TipoDePrenda.CHOMBA)
        .conTrama(Trama.LISA)
        .conMaterial(Material.PIQUE)
        .conColorPrincipal(colorChomba)
        .crearPrenda();
  }

  @Override
  public Prenda fabricarParteInferior() {
    return new Borrador()
        .conTipoDePrenda(TipoDePrenda.PANTALON)
        .conTrama(Trama.LISA)
        .conMaterial(Material.ACETATO)
        .conColorPrincipal(colorPantalon)
        .crearPrenda();
  }

  @Override
  public Prenda fabricarCalzado() {
    return new Borrador()
        .conTipoDePrenda(TipoDePrenda.ZAPATILLAS)
        .conTrama(Trama.LISA)
        .conMaterial(Material.ALGODON)
        .conColorPrincipal(colorZapatillas)
        .crearPrenda();
  }

}
