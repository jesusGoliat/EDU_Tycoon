# Material visual de la idea

Bosquejos de baja fidelidad de *EduTycoon: Campus en ruta* (ver
[`docs/idea.md`](../idea.md)). Se basan en las pantallas reales del juego;
lo que todavía no existe se marca como **propuesto**.

> **Autoría:** todos los bosquejos y diagramas de esta carpeta fueron
> elaborados por **Jesús Ángel González Arellano con apoyo de IA (Claude)**.

---

## 1. Pantallas principales

![Bosquejo de las cuatro pantallas principales: inicio, selección de partida, mapa del campus y ventana de compra](01-pantallas-principales.svg)

*Figura 1. Pantallas principales y cómo se navega entre ellas: Inicio →
Selección de partida → Mapa del campus → Ventana de compra. Elaborado por
Jesús Ángel González Arellano con apoyo de IA (Claude).*

## 2. Estados fuera de la ruta feliz

![Bosquejo de los estados de carga, lista vacía, error y dato inválido](02-estados-alternos.svg)

*Figura 2. Estados alternos. Elaborado por Jesús Ángel González Arellano con
apoyo de IA (Claude).*

| Estado | Dónde aparece | Situación actual |
|---|---|---|
| **A. Carga** | Al abrir o cargar una partida | Propuesto: hoy no hay indicador de carga |
| **B. Lista vacía** | Selección de partida sin partidas guardadas | Ya existe («Slot N vacío»); el mensaje de ayuda es propuesto |
| **C. Error** | No se pudo cargar o guardar un slot | Propuesto: hoy el error sólo se escribe en el log (`MAP_ERROR`) |
| **D. Dato inválido** | Ventana de compra: saldo insuficiente, nivel máximo o costo ≤ 0 | ✅ Implementado en el PR #2 (issue #1) |

## 3. Pantalla de juego: elementos y controles

![Esquema de la pantalla de juego con HUD, engrane de pausa, edificios, terrenos, aviso de ciclo y controles táctiles](03-pantalla-juego.svg)

*Figura 3. Esquema de la pantalla de juego. Los controles se tomaron del
código: `GameScreen.kt` (`GestureDetector`: tocar, arrastrar, pellizcar) y
`PauseMenuWindow.kt`. Elaborado por Jesús Ángel González Arellano con apoyo
de IA (Claude).*

## 4. Recorrido del usuario

Desde que abre la aplicación hasta que completa la tarea principal (comprar o
mejorar un edificio), incluyendo los estados alternos.

```mermaid
flowchart TD
    A([Abre la app]) --> B[Inicio: toca START]
    B --> C{Selección de partida}
    C -- "Slot vacío<br/>(lista vacía)" --> D[Nueva partida]
    C -- Slot con partida --> E[Cargar partida]
    D --> F[Tutorial del Ing. Cárdenas]
    E -. "falla la carga<br/>(error, propuesto)" .-> C
    E --> G
    F --> G[Mapa del campus<br/>HUD con saldo<br/>arrastrar / pellizcar]
    G -- toca un terreno --> H[Ventana de compra]
    H --> I{ReglaCompra.evaluar}
    I -- Exitosa --> J[Toca COMPRAR / MEJORAR]
    J --> K[Se descuenta el costo<br/>y aparece el edificio]
    K --> L([Tarea completada:<br/>el campus creció])
    L -. "siguiente ciclo: +ingresos" .-> G
    I -- SaldoInsuficiente --> M["Botón deshabilitado<br/>«Te faltan $X»"]
    I -- NivelMaximo --> N[Botón «NIVEL MÁXIMO»<br/>deshabilitado]
    I -- CostoInvalido --> O["«Costo inválido»<br/>botón deshabilitado"]
    M -- "cierra y espera ingresos" --> G
    N -- elige otro terreno --> G
    O --> G
```

*Figura 4. Recorrido del usuario hasta completar la tarea principal.
Elaborado por Jesús Ángel González Arellano con apoyo de IA (Claude).*
