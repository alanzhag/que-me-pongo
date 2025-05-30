package ar.edu.utn.frba.dds.modelo.roles;

import ar.edu.utn.frba.dds.modelo.atuendo.Guardarropas;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Usuarie {
  private final int edad;
  private final List<Guardarropas> guardarropas;

  public Usuarie(int edad, List<Guardarropas> guardarropas) {
    Objects.requireNonNull(guardarropas);
    this.edad = edad;
    this.guardarropas = new ArrayList<>(guardarropas);
  }


}
