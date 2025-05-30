package ar.edu.utn.frba.dds.modelo.asesor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import ar.edu.utn.frba.dds.modelo.asesor.motor.MotorSugerencias;
import ar.edu.utn.frba.dds.modelo.atuendo.Atuendo;
import ar.edu.utn.frba.dds.modelo.atuendo.Guardarropas;
import ar.edu.utn.frba.dds.modelo.clima.EstadoDelTiempo;
import ar.edu.utn.frba.dds.modelo.clima.Humedad;
import ar.edu.utn.frba.dds.modelo.clima.ServicioMetereologico;
import ar.edu.utn.frba.dds.modelo.prenda.Formalidad;
import ar.edu.utn.frba.dds.modelo.roles.Config;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AsesorDeImagenTest {

  private final String CIUDAD_VALIDA = "Buenos Aires";
  private final int EDAD_JOVEN = 30;
  private final int EDAD_MAYOR_55 = 60;
  private final int TEMPERATURA_VALIDA = 20;
  private final Humedad HUMEDAD_VALIDA = Humedad.SECO;
  @Mock
  private ServicioMetereologico servicioMetereologicoMock;
  @Mock
  private Config configMock;
  @Mock
  private MotorSugerencias motorSugerenciasMock;
  @Mock
  private Guardarropas guardarropasMock;
  @Mock
  private Atuendo atuendo1Mock;
  @Mock
  private Atuendo atuendo2Mock;
  @Mock
  private Atuendo atuendoInformalMock;
  private AsesorDeImagen asesor;

  @BeforeEach
  void setUp() {
    asesor = new AsesorDeImagen(servicioMetereologicoMock, configMock, motorSugerenciasMock);

    // Configuración común del clima
    EstadoDelTiempo estadoDelTiempoMock = mock(EstadoDelTiempo.class);
    lenient().when(estadoDelTiempoMock.getTemperatura()).thenReturn(TEMPERATURA_VALIDA);
    lenient().when(estadoDelTiempoMock.getHumedad()).thenReturn(HUMEDAD_VALIDA);
    lenient().when(servicioMetereologicoMock.obtenerClima(CIUDAD_VALIDA)).thenReturn(estadoDelTiempoMock);
  }

  @Test
  @DisplayName("Debería sugerir un atuendo cuando hay combinaciones y no hay filtros especiales de config")
  void sugerirAtuendo_cuandoHayCombinacionesYConfigSimple_retornaAtuendo() {
    List<Atuendo> combinaciones = Arrays.asList(atuendo1Mock, atuendo2Mock);
    when(guardarropasMock.generarCombinacionesPosibles()).thenReturn(combinaciones);
    when(configMock.ejecutarLogicaAdicionalEnSugerencias()).thenReturn(false);

    when(atuendo1Mock.esAptoTemperatura(TEMPERATURA_VALIDA)).thenReturn(true);
    when(atuendo1Mock.esAptoHumedad(HUMEDAD_VALIDA)).thenReturn(true);
    when(atuendo2Mock.esAptoTemperatura(TEMPERATURA_VALIDA)).thenReturn(true);
    when(atuendo2Mock.esAptoHumedad(HUMEDAD_VALIDA)).thenReturn(true);

    // El motor de sugerencias elige uno
    when(motorSugerenciasMock.elegir(anyList())).thenAnswer(invocation -> {
      List<Atuendo> lista = invocation.getArgument(0);
      return lista.isEmpty() ? Optional.empty() : Optional.of(lista.get(0));
    });

    Optional<Atuendo> sugerencia = asesor.sugerirAtuendo(CIUDAD_VALIDA, EDAD_JOVEN, guardarropasMock);

    assertTrue(sugerencia.isPresent());
    assertEquals(atuendo1Mock, sugerencia.get());
    verify(motorSugerenciasMock).elegir(combinaciones); // Verifica que se llamó con la lista original
  }

  @Test
  @DisplayName("Debería sugerir un atuendo aplicando filtros de temperatura y humedad")
  void sugerirAtuendo_conFiltrosDeClima_retornaAtuendoApto() {
    when(guardarropasMock.generarCombinacionesPosibles()).thenReturn(Arrays.asList(atuendo1Mock, atuendo2Mock));
    when(configMock.ejecutarLogicaAdicionalEnSugerencias()).thenReturn(true); // Se ejecutan filtros
    when(configMock.filtroInformalMayores55()).thenReturn(false); // Filtro de informalidad desactivado

    when(atuendo1Mock.esAptoTemperatura(TEMPERATURA_VALIDA)).thenReturn(false); // atuendo1 no es apto
    when(atuendo2Mock.esAptoTemperatura(TEMPERATURA_VALIDA)).thenReturn(true);  // atuendo2 sí es apto
    when(atuendo2Mock.esAptoHumedad(HUMEDAD_VALIDA)).thenReturn(true);

    when(motorSugerenciasMock.elegir(List.of(atuendo2Mock))).thenReturn(Optional.of(atuendo2Mock));


    Optional<Atuendo> sugerencia = asesor.sugerirAtuendo(CIUDAD_VALIDA, EDAD_JOVEN, guardarropasMock);


    assertTrue(sugerencia.isPresent());
    assertEquals(atuendo2Mock, sugerencia.get());
    verify(motorSugerenciasMock).elegir(List.of(atuendo2Mock));
  }

  @Test
  @DisplayName("Debería aplicar filtro de informalidad para usuarios mayores de 55")
  void sugerirAtuendo_conFiltroInformalParaMayores_retornaAtuendoNoInformal() {
    when(guardarropasMock.generarCombinacionesPosibles()).thenReturn(Arrays.asList(atuendoInformalMock, atuendo1Mock));
    when(configMock.ejecutarLogicaAdicionalEnSugerencias()).thenReturn(true);
    when(configMock.filtroInformalMayores55()).thenReturn(true); // Filtro de informalidad activado

    // Todos aptos para clima
    when(atuendoInformalMock.esAptoTemperatura(TEMPERATURA_VALIDA)).thenReturn(true);
    when(atuendoInformalMock.esAptoHumedad(HUMEDAD_VALIDA)).thenReturn(true);
    when(atuendo1Mock.esAptoTemperatura(TEMPERATURA_VALIDA)).thenReturn(true);
    when(atuendo1Mock.esAptoHumedad(HUMEDAD_VALIDA)).thenReturn(true);

    // Configuración de formalidad
    when(atuendoInformalMock.esAptoFormalidad(Formalidad.INFORMAL)).thenReturn(true); // Este es informal
    when(atuendo1Mock.esAptoFormalidad(Formalidad.INFORMAL)).thenReturn(false); // Este no es informal

    when(motorSugerenciasMock.elegir(List.of(atuendo1Mock))).thenReturn(Optional.of(atuendo1Mock));


    Optional<Atuendo> sugerencia = asesor.sugerirAtuendo(CIUDAD_VALIDA, EDAD_MAYOR_55, guardarropasMock);


    assertTrue(sugerencia.isPresent());
    assertEquals(atuendo1Mock, sugerencia.get()); // Debería elegir el no informal
    verify(motorSugerenciasMock).elegir(List.of(atuendo1Mock));
  }

  @Test
  @DisplayName("No debería aplicar filtro de informalidad si el usuario no es mayor de 55")
  void sugerirAtuendo_conFiltroInformalParaJovenes_noAplicaFiltroFormalidad() {
    List<Atuendo> combinaciones = Arrays.asList(atuendoInformalMock, atuendo1Mock);
    when(guardarropasMock.generarCombinacionesPosibles()).thenReturn(combinaciones);
    when(configMock.ejecutarLogicaAdicionalEnSugerencias()).thenReturn(true);
    when(configMock.filtroInformalMayores55()).thenReturn(true); // Filtro activado, pero edad no cumple

    // Todos aptos para clima
    when(atuendoInformalMock.esAptoTemperatura(TEMPERATURA_VALIDA)).thenReturn(true);
    when(atuendoInformalMock.esAptoHumedad(HUMEDAD_VALIDA)).thenReturn(true);
    when(atuendo1Mock.esAptoTemperatura(TEMPERATURA_VALIDA)).thenReturn(true);
    when(atuendo1Mock.esAptoHumedad(HUMEDAD_VALIDA)).thenReturn(true);

    // El motor de sugerencias elige uno (el filtro de formalidad no se aplica por edad)
    when(motorSugerenciasMock.elegir(combinaciones)).thenReturn(Optional.of(atuendoInformalMock));

    Optional<Atuendo> sugerencia = asesor.sugerirAtuendo(CIUDAD_VALIDA, EDAD_JOVEN, guardarropasMock);

    assertTrue(sugerencia.isPresent());
    assertEquals(atuendoInformalMock, sugerencia.get());
    verify(motorSugerenciasMock).elegir(combinaciones); // Se llama con la lista filtrada solo por clima
  }

  @Test
  @DisplayName("Debería retornar Optional vacío si no hay combinaciones posibles")
  void sugerirAtuendo_sinCombinaciones_retornaEmpty() {
    when(guardarropasMock.generarCombinacionesPosibles()).thenReturn(Collections.emptyList());
    when(motorSugerenciasMock.elegir(Collections.emptyList())).thenReturn(Optional.empty());

    Optional<Atuendo> sugerencia = asesor.sugerirAtuendo(CIUDAD_VALIDA, EDAD_JOVEN, guardarropasMock);

    assertFalse(sugerencia.isPresent());
    verify(motorSugerenciasMock).elegir(Collections.emptyList());
  }

  @Test
  @DisplayName("Debería retornar Optional vacío si ninguna combinación es apta tras los filtros")
  void sugerirAtuendo_ningunaCombinacionApta_retornaEmpty() {
    when(guardarropasMock.generarCombinacionesPosibles()).thenReturn(List.of(atuendo1Mock));
    when(configMock.ejecutarLogicaAdicionalEnSugerencias()).thenReturn(true);
    when(configMock.filtroInformalMayores55()).thenReturn(false);

    when(atuendo1Mock.esAptoTemperatura(TEMPERATURA_VALIDA)).thenReturn(false); // No apto por temperatura

    when(motorSugerenciasMock.elegir(Collections.emptyList())).thenReturn(Optional.empty());

    Optional<Atuendo> sugerencia = asesor.sugerirAtuendo(CIUDAD_VALIDA, EDAD_JOVEN, guardarropasMock);

    assertFalse(sugerencia.isPresent());
    verify(motorSugerenciasMock).elegir(Collections.emptyList());
  }

  @Test
  @DisplayName("Constructor debería lanzar NullPointerException si servicioMetereologico es null")
  void constructor_conServicioNull_lanzaNullPointerException() {
    assertThrows(NullPointerException.class, () -> {
      new AsesorDeImagen(null, configMock, motorSugerenciasMock);
    });
  }

  @Test
  @DisplayName("sugerirAtuendo debería lanzar NullPointerException si ciudad es null")
  void sugerirAtuendo_conCiudadNull_lanzaNullPointerException() {
    assertThrows(NullPointerException.class, () -> {
      asesor.sugerirAtuendo(null, EDAD_JOVEN, guardarropasMock);
    });
  }

  @Test
  @DisplayName("sugerirAtuendo debería lanzar NullPointerException si guardarropas es null")
  void sugerirAtuendo_conGuardarropasNull_lanzaNullPointerException() {
    assertThrows(NullPointerException.class, () -> {
      asesor.sugerirAtuendo(CIUDAD_VALIDA, EDAD_JOVEN, null);
    });
  }
}