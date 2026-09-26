# Ficha de idea — EduTycoon: Campus en ruta

> Entrega 1 · Desarrollo de aplicaciones móviles nativas (IPN–ESCOM) ·
> Equipo: Jesús Ángel González Arellano (`jesusGoliat`) y Javier Gamez (`Javier-Gamez`).

## Problema en una frase

Los estudiantes que quieren jugar un tycoon en el celular durante sus traslados
cortos pierden el progreso o se frustran con compras que no entienden, porque
estos juegos están pensados para sesiones largas.

## Ruta elegida y motivo

**EduTycoon** (juego de gestión en Kotlin + libGDX ambientado en el IPN).

- Ya tiene un recorrido completo que se puede ejecutar: inicio → partida → mapa
  del campus → comprar y mejorar edificios → ingresos por ciclo.
- Su ambientación (escuelas del IPN, presupuesto, alumnos) es cercana a
  nosotros como usuarios, lo que facilita probar la idea con compañeros.
- La lógica del juego vive en el módulo `core` y se puede probar con pruebas
  unitarias en la JVM, sin emulador.

## Usuario y contexto

| | |
|---|---|
| **Quién** | Estudiante del IPN de primeros semestres, de 18 a 22 años, que juega en el celular sin ser jugador intenso. |
| **Dónde** | En el transporte público (Metro, Metrobús) camino a la escuela, o en los tiempos libres entre clases. |
| **Cuándo** | Sesiones cortas de 5 a 10 minutos, varias veces al día, casi siempre con una sola mano y con interrupciones (llegar a su estación, entrar a clase). |

## Problema observable

- Las sesiones se interrumpen de forma abrupta: el jugador sale de la app o
  presiona Atrás y **pierde el avance** (lo comprobamos en el juego actual,
  issue #4).
- Al querer comprar un edificio el juego no dejaba claro **cuánto dinero
  faltaba**: el botón parecía disponible y sólo fallaba al tocarlo (resuelto
  en el PR #2, issue #1).
- El texto del juego no se adapta al tamaño de letra del sistema, lo que
  dificulta leerlo en movimiento (issue #5).

## Alternativa actual

Hoy ese usuario usa tycoons comerciales (p. ej. *Idle Miner*, *AdVenture
Capitalist*) o juegos casuales sin gestión. Los tycoons comerciales guardan
el progreso, pero no tienen relación con su escuela, y muchos presionan con
anuncios o compras dentro de la app para avanzar.

## Tarea principal

Hacer crecer el campus: **comprar o mejorar un edificio con el presupuesto
disponible** y ver cómo aumentan los alumnos y los ingresos en los siguientes
ciclos.

## Criterio de éxito

- Un jugador nuevo completa su **primera compra en menos de 2 minutos** desde
  que abre la app, sin ayuda externa.
- Al salir y volver a entrar (Inicio, Atrás o cerrar la app), el jugador
  **conserva el 100 % de su progreso**.
- Ninguna compra deja el saldo en negativo y el jugador siempre ve cuánto le
  falta cuando no le alcanza.

## Alcance de la primera versión

| Entra en la v1 | Estado |
|---|---|
| Comprar y mejorar edificios con una regla que impide gastar más saldo del disponible, mostrando cuánto falta | ✅ PR #2 (issue #1) |
| Ingresos por ciclo según los edificios comprados | Ya existe en el proyecto base |
| Guardar la partida al salir y no perderla con el botón Atrás | Pendiente (issue #4) |
| Tutorial inicial breve con el Ing. Cárdenas | Ya existe en el proyecto base |

## Funciones aplazadas (de forma deliberada)

- Contratación y gestión de docentes.
- Eventos aleatorios complejos (huelgas, recortes de presupuesto).
- Reputación con efectos sobre la inscripción.
- Escalado del texto según el sistema (issue #5).
- Publicación en Google Play, multijugador y tablas de puntuación.

## Evidencia e hipótesis

**Todavía no tenemos evidencia real con usuarios.** Hasta ahora la idea se
sostiene en:

- Nuestra propia experiencia como estudiantes que se trasladan a ESCOM.
- Los defectos que encontramos al probar el juego en un celular físico el
  25/09/2026 (issues #4 y #5, ver `docs/pruebas.md`).

**Hipótesis pendiente de validar:** *los estudiantes del IPN jugarían un
tycoon ambientado en su escuela en sesiones de 5 a 10 minutos durante sus
traslados, siempre que no pierdan el progreso al salir.*

Cualquier evidencia que la confirme o la descarte se registrará aquí con su
fecha, método y resultado.

## Historia de usuario principal

> **Como** estudiante del IPN que juega en el Metro camino a clases,
> **quiero** comprar y mejorar edificios de mi campus sabiendo si me alcanza el
> presupuesto,
> **para** hacer crecer mi escuela en sesiones cortas sin gastar de más ni
> frustrarme.

## Criterio de aceptación

> **Dado** que tengo un saldo de $100,000 y un edificio cuesta $120,000,
> **cuando** abro la ventana de ese edificio,
> **entonces** el botón COMPRAR aparece deshabilitado, se muestra el mensaje
> «Te faltan $20.0K» y mi saldo sigue en $100,000.

Se puede verificar sin interpretar: otra persona prepara ese saldo, abre la
ventana y responde **sí/no** a cada una de las tres condiciones. Está cubierto
por la prueba automática `ReglaCompraTest` y por la prueba manual QA-02 de
`docs/pruebas.md`.

## Material visual

Bosquejos de pantallas, estados alternos y recorrido del usuario:
[`docs/diseno/`](diseno/).
