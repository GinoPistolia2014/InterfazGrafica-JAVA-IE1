# Simulador de Ecosistema — Interfaz Gráfica (1° Instancia Evaluativa)

Simulación por turnos en Java que corre en la terminal. El jugador configura un ecosistema con **plantas, conejos y lobos**, elige el clima y la cantidad de turnos, y cada 3 turnos puede intervenir cambiando el clima o agregando entidades. Al finalizar se muestra un reporte completo con estadísticas de la simulación.

## Integrantes y roles

| Integrante | GitHub | Aporte principal |
|---|---|---|
| Tomi Gauna | @TomiGauna | Armado inicial de la estructura de clases (`Entidad`, `Animal`, `Planta`, `Conejo`, `Lobo`, interfaces y `Ecosistema`) y validaciones de la configuración inicial. |
| Gino Pistolia | @GinoPistolia2014 | Creación del repositorio y del proyecto de NetBeans (`build.xml`, `nbproject`). |
| Milagros Tissera | @militissera11 | Correcciones de compilación y rutas, encapsulamiento con validaciones, creación de entidades, sobrecarga de `agregarEntidad()`, límite de 5 lobos, alimentación y reproducción de conejos, caza de lobos, reproducción de plantas según el clima, registro de eventos, nacimientos y muertes, `PlantaVenenosa` e interfaz `Peligroso`. |
| Joaquín Espil | @joaquinespil-code | `procesarTurno()` con el orden completo del turno, efectos del clima, verificación de muertes, bucle principal, menú de intervención cada 3 turnos, reporte final, estadísticas (historial, máximos y mínimos) y ranking de entidades peligrosas. |

## Requisitos y ejecución

- **NetBeans:** Apache NetBeans 27 (probado).
- **JDK:** 17 o superior (probado con JDK 21).
- **Librerías externas:** ninguna, solo Java estándar.

**Pasos:**
1. Clonar el repositorio: `https://github.com/GinoPistolia2014/InterfazGrafica-JAVA-IE1.git`
2. En NetBeans: **File → Open Project** y elegir la carpeta `InterfazGrafica-IE1`.
3. Si NetBeans no encuentra el JDK: clic derecho en el proyecto → **Properties → Libraries → Java Platform** y elegir un JDK 17 o superior.
4. **Run** (clase principal: `ie1.interfazgrafica.IE1InterfazGrafica`).
5. Ingresar la configuración inicial y presionar **Enter** para avanzar cada turno.

## Estructura del proyecto

```
InterfazGrafica-JAVA-IE1/
├── InterfazGrafica-IE1/        # Proyecto de NetBeans (build.xml, nbproject)
└── src/ie1/interfazgrafica/
    ├── IE1InterfazGrafica.java # main: configuración, loop principal e intervención
    ├── Ecosistema.java         # listas de entidades, turno, clima, eventos y reporte final
    ├── Entidad.java            # clase abstracta base (nombre, energía, edad, viva)
    ├── Animal.java             # clase abstracta intermedia (velocidad, peso, comer)
    ├── Planta.java             # extends Entidad, implements Reproducible, Mortal
    ├── PlantaVenenosa.java     # extends Planta, implements Peligroso (bonus)
    ├── Conejo.java             # extends Animal, implements Reproducible
    ├── Lobo.java               # extends Animal, implements Peligroso
    ├── Reproducible.java       # interface con método default intentarReproduccion()
    ├── Mortal.java             # interface con método default verificarMuerte()
    ├── Peligroso.java          # interface getNivelPeligro() (bonus)
    └── Clima.java              # enum: soleado, lluvioso, sequia, invierno
```

## Funcionamiento

**Orden de cada turno:**
1. Las plantas se reproducen según el clima.
2. Los conejos comen una planta (si no encuentran, pierden 15 de energía) y se reproducen.
3. Los lobos intentan cazar un conejo; la probabilidad crece con su energía: `energia / (energia + 50)`.
4. Todas las entidades envejecen y gastan energía base.
5. Se aplican los efectos del clima.
6. Las entidades sin energía mueren.
7. Se muestran los eventos y el estado del ecosistema.

Plantas y conejos se reproducen en un único recorrido de un `ArrayList<Reproducible>` (polimorfismo), así nadie intenta reproducirse dos veces por turno.

**Clima:**

| Clima | Plantas | Conejos | Lobos |
|---|---|---|---|
| Soleado | reproducción x1.5 | +5 energía | sin cambio |
| Lluvioso | reproducción x2 | +3 energía | −5 energía |
| Sequía | reproducción x0.5 | −5 energía | sin cambio |
| Invierno | no se reproducen | −8 energía | +20% éxito de caza |

**Intervención (cada 3 turnos):** cambiar el clima, agregar una entidad (planta, planta venenosa, conejo o lobo, con un máximo de 5 lobos en toda la simulación) o solo avanzar. Cada acción se confirma antes de ejecutarse.

**Fin de la simulación:** al completar los turnos configurados o cuando se extingue alguna población (colapso).

**Reporte final:** causa de fin y población extinguida, turno de mayor actividad, entidad más longeva de cada tipo, lobo con más cacerías, nacimientos y muertes por tipo.

**Bonus implementados:**
- **Planta Venenosa:** se guarda en el mismo `ArrayList<Planta>`; el conejo no la distingue y al comerla pierde 30 de energía.
- **Estadísticas:** historial de poblaciones turno a turno, con el máximo y mínimo de cada una.
- **Peligroso:** en el reporte final se listan las entidades peligrosas vivas ordenadas por nivel (lobo: 3 + cacerías exitosas; planta venenosa: 2).

## Desafíos encontrados

- **Ruta de fuentes de NetBeans:** el proyecto apuntaba a una carpeta local de un integrante; se corrigió a `../src` para que abra en cualquier computadora.
- **Errores de compilación iniciales:** constructores que no coincidían entre `Entidad`, `Animal` y sus subclases.
- **Energía negativa y muertes:** se validó que la energía nunca baje de 0 y que la muerte se registre una sola vez.
- **Reproducción duplicada:** evitar que el conejo se reproduzca dos veces por turno al usar el `ArrayList<Reproducible>`.
- **Listas protegidas:** los getters devuelven copias, por eso las crías se agregan con `registrarNacimiento()` y no directamente en las listas.
- **Balance:** con las reglas iniciales el ecosistema colapsaba entre el turno 3 y el 6. Se ajustó la probabilidad de reproducción (conejos 30% por turno, plantas base 0.60) para que la simulación dure más y la intervención tenga sentido.
- **Trabajo en equipo con Git:** ramas por integrante, `NoSuchMethodError` resuelto con *Clean and Build* tras cambiar la firma de un método, y cuidado de no subir archivos de `nbproject` modificados por distintas versiones de NetBeans.

## Uso de IA y herramientas externas

Se utilizaron herramientas de inteligencia artificial como apoyo durante el desarrollo. La documentación de su uso (conversaciones y prompts de cada integrante) se entrega por separado en la carpeta de documentación del proyecto, como indica la consigna.
