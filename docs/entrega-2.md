# Entrega 2 — Prueba cerrada y primera actualización en Google Play

> Índice de verificación de la Entrega 2 de **EduTycoon**. Se completa al cerrar
> la entrega; los datos que aún no existen se marcan como `PENDIENTE`.

## Datos de la entrega

| Campo | Valor |
|---|---|
| Repositorio | <https://github.com/jesusGoliat/EDU_Tycoon> |
| Paquete | `io.moviles.IPN_Tycoon` |
| Materia | Desarrollo de aplicaciones móviles nativas — IPN ESCOM |
| Profesor | Gabriel Hurtado Avilés |
| Equipo | Jesús Ángel González Arellano (`jesusGoliat`) · Javier Gamez (`Javier-Gamez`) |
| Fecha límite | 09/11/2026 |
| Tag | `parcial-2` (PENDIENTE) |

## Tablero Scrum

| Elemento | Liga |
|---|---|
| Tablero en GitHub Projects | PENDIENTE |
| Captura del tablero y del sprint | [`docs/evidencia/entrega-2/scrum.png`](evidencia/entrega-2/scrum.png) (PENDIENTE) |

Columnas: **Backlog · Por hacer · En progreso · En revisión · Terminado**.

## Pull Requests fusionados

| PR | Título | Issue | Autor | Revisor | CI | Merge |
|---|---|---|---|---|---|---|
| [#2](https://github.com/jesusGoliat/EDU_Tycoon/pull/2) | feat: block purchases that exceed available balance | #1 | Jesús | Javier | verde | 24/09/2026 |
| [#10](https://github.com/jesusGoliat/EDU_Tycoon/pull/10) | feat: add AI-controlled rival institute | #9 | Jesús | PENDIENTE | verde | PENDIENTE |
| [#14](https://github.com/jesusGoliat/EDU_Tycoon/pull/14) | fix: show the economy income multiplier in the building window | #3 | Jesús | PENDIENTE | PENDIENTE | PENDIENTE |
| [#15](https://github.com/jesusGoliat/EDU_Tycoon/pull/15) | fix: render the inverted exclamation mark in the victory dialog | #11 | Jesús | PENDIENTE | PENDIENTE | PENDIENTE |
| [#16](https://github.com/jesusGoliat/EDU_Tycoon/pull/16) | docs: add the Entrega 2 QA matrix and verification index | #12 | Jesús | PENDIENTE | PENDIENTE | PENDIENTE |
| [#17](https://github.com/jesusGoliat/EDU_Tycoon/pull/17) | chore: publish release 1.3.2 (versionCode 6) | #13 | Jesús | PENDIENTE | PENDIENTE | PENDIENTE |
| PENDIENTE | feat: show the rival market share in the pause stats | PENDIENTE | Javier | Jesús | PENDIENTE | PENDIENTE |
| PENDIENTE | fix: repair DatabaseTest and cover the Room 4→5 migration | PENDIENTE | Javier | Jesús | PENDIENTE | PENDIENTE |

Evidencias de revisión: `pr-01.png`, `pr-02.png`, `pr-03.png` (PENDIENTE).

## Issues

| Issue | Título | Estado |
|---|---|---|
| [#1](https://github.com/jesusGoliat/EDU_Tycoon/issues/1) | feat: block purchases and upgrades that exceed available balance | cerrada |
| [#3](https://github.com/jesusGoliat/EDU_Tycoon/issues/3) | fix: building window shows 10x lower income than EconomyEngine credits | abierta (se cierra con el PR de corrección) |
| [#9](https://github.com/jesusGoliat/EDU_Tycoon/issues/9) | feat: add AI-controlled rival institute (Edutycoon-IA) | abierta |
| [#11](https://github.com/jesusGoliat/EDU_Tycoon/issues/11) | fix: render the inverted exclamation mark in the victory dialog | abierta |
| [#12](https://github.com/jesusGoliat/EDU_Tycoon/issues/12) | docs: add the Entrega 2 QA matrix and verification index | abierta |
| [#13](https://github.com/jesusGoliat/EDU_Tycoon/issues/13) | chore: publish release 1.3.2 (versionCode 6) | abierta |
| PENDIENTE | feat: show the rival market share in the pause stats | PENDIENTE |
| PENDIENTE | fix: repair DatabaseTest and cover the Room 4→5 migration | PENDIENTE |

## Publicaciones en Google Play

| Versión | versionCode | Fecha | Canal | Estado | Evidencia |
|---|---|---|---|---|---|
| 1.3.1 | 5 | 14/08/2026 | Prueba cerrada | Publicada ✅ | [`play-cerrada-v1.png`](evidencia/entrega-2/play-cerrada-v1.png) (PENDIENTE de captura) |
| 1.3.2 | 6 | __/11/2026 | Prueba cerrada | PENDIENTE | [`play-actualizacion-v2.png`](evidencia/entrega-2/play-actualizacion-v2.png) (PENDIENTE) |

La ficha pública muestra **Última actualización: 14 ago 2026** y el paquete
`io.moviles.IPN_Tycoon`, lo que confirma la primera publicación.

### Notas de versión (1.3.2)

```text
Novedades de 1.3.2
- Nuevo Instituto Rival controlado por IA: compite por los alumnos del campus,
  compra y mejora edificios, y muestra la cuota de mercado en el HUD.
- Los edificios del rival se ven en rojo y no se pueden comprar.
- Nuevo final de partida con diálogo de victoria o derrota.
- El estado del rival se guarda y se recupera junto con la partida.
- Corregido: la ventana de edificio mostraba un ingreso 10 veces menor al real.
- Corregido: el título de victoria muestra «¡Victoria!».
```

## Cómo instalar la versión de prueba

1. Abrir <https://play.google.com/apps/testing/io.moviles.IPN_Tycoon> con la
   cuenta de Google autorizada por el propietario.
2. Pulsar **Convertirse en tester**.
3. Instalar o actualizar desde Google Play.
4. Para reportar un problema, abrir un issue con el `versionCode` y los pasos.

## Integración continua

| Check | Estado | Liga |
|---|---|---|
| `build` del PR #10 | verde (ejecución anterior); la última corrida está en curso | <https://github.com/jesusGoliat/EDU_Tycoon/actions/runs/42527825673> |
| `build` del PR #14 (ingreso ×10) | en curso | <https://github.com/jesusGoliat/EDU_Tycoon/actions/runs/42531266505> |
| `build` del PR #15 (glifo) | en cola | <https://github.com/jesusGoliat/EDU_Tycoon/actions/runs/42531270985> |
| `build` del PR #16 (documentación) | PENDIENTE | PENDIENTE |
| `build` del PR #17 (release) | en curso | <https://github.com/jesusGoliat/EDU_Tycoon/actions/runs/42531245188> |
| `build` del PR de Javier | PENDIENTE | PENDIENTE |

## Pruebas y QA

- Matriz de pruebas: [`docs/pruebas.md`](pruebas.md#entrega-2--edutycoon-ia-en-prueba-cerrada-de-google-play).
- Evidencias: [`docs/evidencia/entrega-2/`](evidencia/entrega-2/).
- Imagen individual de cada integrante:
  - `gonzalez-jesus-entrega2.png` (PENDIENTE)
  - `gamez-javier-entrega2.png` (PENDIENTE)

## Declaración de uso de asistentes de IA

<!-- PENDIENTE: la redacta Jesús. El agente no completa este texto. -->
