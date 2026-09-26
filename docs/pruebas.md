# Matriz de pruebas

## Entorno

| Campo | Valor |
|---|---|
| Dispositivo | Xiaomi POCO 2207117BPG (físico, por USB) |
| Android | 13 (API 33) |
| App | 1.3.1 (`versionCode 5`), APK debug |
| Código probado | rama `feature/1-purchase-balance-rule` @ `38eb79e` |
| Compilación | JDK 21, Gradle 9.5.0, AGP 9.3.1 (`./gradlew :android:assembleDebug`) |
| Fecha | 25/09/2026 |
| Probó | Jesús Ángel González Arellano (`jesusGoliat`) |

Saldo inicial de una partida nueva: **$500.0K**. Los ciclos del juego suman
ingresos mientras se prueba, por eso algunos saldos suben entre un paso y otro.

---

## Característica #1 — Regla de compra (no gastar más saldo del disponible)

### Pruebas automáticas

`./gradlew :core:test` → **14/14** en verde (8 de `ReglaCompraTest` + 6
existentes). Se ejecutan también en CI (check `build`) en cada PR.

### Pruebas manuales

| ID | Caso | Pasos | Resultado esperado | Resultado real | Estado | Evidencia |
|---|---|---|---|---|---|---|
| QA-01 | Ruta feliz: compra con saldo suficiente | Partida nueva → tocar **Cafetería** ($150K) → COMPRAR | Botón `COMPRAR $150.0K` habilitado, sin aviso rojo; al comprar se cierra la ventana, se descuentan $150K y el edificio aparece en el mapa | Botón habilitado con saldo $500.0K; tras comprar, la Cafetería (Lvl 1) aparece en el mapa y el saldo baja a $350K (luego $360.0K por el ingreso del ciclo) | ✅ Pasa | `qa-01-compra-disponible.jpg`, `qa-01b-cafeteria-comprada.jpg` |
| QA-02 | Estado alterno: saldo insuficiente | Con la Cafetería comprada → tocar **Biológicas** ($1.25M) → intentar COMPRAR | Botón deshabilitado **desde que abre la ventana**, aviso rojo `Te faltan $X` con X = precio − saldo; el saldo no cambia | Saldo $360.0K: botón `COMPRAR $1.25M` deshabilitado y aviso `Te faltan $890.0K` (1,250K − 360K = 890K); tocarlo no hace nada | ✅ Pasa | `qa-02-saldo-insuficiente.jpg` |
| QA-03 | Mejora de nivel con saldo suficiente | Abrir **Cafetería** comprada → MEJORAR | Botón `MEJORAR LVL 2 $150.0K` (`precio × nivel`); al mejorar se descuentan $150K y pasa a nivel 2 | Botón `MEJORAR LVL 2 $150.0K` con saldo $370.0K; tras mejorar: nivel 2/2 y saldo $220.0K | ✅ Pasa | `qa-03-mejora.jpg` |
| QA-04 | Estado alterno: nivel máximo | Abrir **Cafetería** en nivel 2/2 | Botón `NIVEL MÁXIMO` deshabilitado, sin aviso rojo, sin cobro | `NIVEL MÁXIMO` deshabilitado, sin aviso; saldo sin cambio | ✅ Pasa | `qa-04-nivel-maximo.jpg` |
| QA-05 | Navegación: botón Atrás / recreación | Con partida en curso (Cafetería comprada) → botón **Atrás** de Android → elegir partida | Atrás cierra la ventana o pide confirmación; la partida conserva su progreso | Atrás lleva a *Selección de partida*; los 3 slots aparecen como «vacío»: **se pierde el progreso** | ❌ Falla → **#4** | `qa-05a-atras-seleccion.jpg`, `qa-05b-slots-vacios.jpg` |
| QA-06 | Accesibilidad: texto ampliado | Ajustes → Pantalla → Tamaño del texto **XXL** → volver al juego → abrir Cafetería | Los textos del juego crecen o siguen legibles según el ajuste del sistema | Los textos del juego quedan **exactamente del mismo tamaño**; el ajuste se ignora (libGDX dibuja sus propias fuentes). No hay cortes de texto | ❌ Falla → **#5** | `qa-06a-texto-xxl-ajuste.jpg`, `qa-06b-texto-xxl-juego.jpg` |

Evidencias en [`docs/evidencia/entrega-1/qa/`](evidencia/entrega-1/qa/).

### Observaciones (sin bloquear la característica)

- **«Tu saldo» de la ventana es una foto al abrirla.** Si pasa un ciclo con la
  ventana abierta, el HUD se actualiza (p. ej. $370.0K) pero la ventana sigue
  mostrando el saldo anterior ($360.0K), y el botón conserva el estado que
  tenía al abrir. Cerrar y volver a abrir la ventana lo corrige. La compra en
  sí siempre es segura: `ReglaCompra.aplicar()` vuelve a evaluar al tocar.
- **El ingreso mostrado es 10× menor que el real**: la ventana dice
  `Ingreso/ciclo: $1.0K` y el saldo sube $10K por ciclo. Ya registrado en
  **#3**.

### Pendiente en esta ronda

- **Rotación** y **Inicio → volver a la app** (recreación sin usar Atrás) no se
  registraron todavía; se agregarán a esta matriz.

### Defectos registrados

| Issue | Título | Origen |
|---|---|---|
| #3 | fix: building window shows 10x lower income than EconomyEngine credits | Revisión de código + QA-01/03 |
| #4 | fix: Back button from the map loses the current game progress | QA-05 |
| #5 | fix: game text ignores Android system text size (accessibility) | QA-06 |
