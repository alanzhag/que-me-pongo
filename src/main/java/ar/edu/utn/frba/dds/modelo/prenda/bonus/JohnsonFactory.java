package ar.edu.utn.frba.dds.modelo.prenda.bonus;

import ar.edu.utn.frba.dds.modelo.prenda.Borrador;
import ar.edu.utn.frba.dds.modelo.prenda.Color;
import ar.edu.utn.frba.dds.modelo.prenda.Material;
import ar.edu.utn.frba.dds.modelo.prenda.Prenda;
import ar.edu.utn.frba.dds.modelo.prenda.TipoDePrenda;
import ar.edu.utn.frba.dds.modelo.prenda.Trama;

public class JohnsonFactory implements EstablecimientoAbstractFactory {
  private final Color colorCamisa = new Color(255, 255, 255);
  private final Color colorPantalon = new Color(0, 0, 0);
  private final Color colorZapatos = new Color(0, 0, 0);

  @Override
  public Prenda fabricarParteSuperior() {
    return new Borrador()
        .conTipoDePrenda(TipoDePrenda.CAMISA)
        .conTrama(Trama.LISA)
        .conColorPrincipal(colorCamisa)
        .conMaterial(Material.ALGODON)
        .crearPrenda();
  }

  @Override
  public Prenda fabricarParteInferior() {
    return new Borrador()
        .conTipoDePrenda(TipoDePrenda.PANTALON)
        .conTrama(Trama.LISA)
        .conColorPrincipal(colorPantalon)
        .conMaterial(Material.SEDA)
        .crearPrenda();
  }

  @Override
  public Prenda fabricarCalzado() {
    return new Borrador()
        .conTipoDePrenda(TipoDePrenda.ZAPATOS)
        .conTrama(Trama.LISA)
        .conColorPrincipal(colorZapatos)
        .conMaterial(Material.CUERO)
        .crearPrenda();
  }
}
