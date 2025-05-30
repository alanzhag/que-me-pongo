package ar.edu.utn.frba.dds.modelo.prenda.bonus;

import ar.edu.utn.frba.dds.modelo.prenda.Prenda;

public interface EstablecimientoAbstractFactory {
  Prenda fabricarParteSuperior();

  Prenda fabricarParteInferior();

  Prenda fabricarCalzado();
}
