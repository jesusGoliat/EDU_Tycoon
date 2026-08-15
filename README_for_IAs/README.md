# Contexto para IAs — EDU Tycoon (IPN Tycoon)

Esta carpeta existe para que una IA que abra este repo por primera vez pueda
trabajar sin volver a descubrir todo desde cero. Léela antes de tocar nada.

Última actualización: **15 de agosto de 2026**.

## Qué es esto

Juego de simulación educativa hecho con **libGDX** en Kotlin, publicado en Google
Play como **EDU Tycoon** (paquete `io.moviles.IPN_Tycoon`). Proyecto escolar del
IPN. Actualmente en **prueba cerrada (Alpha)**, con solicitud de acceso a
producción enviada.

## Índice

| Archivo | Qué contiene |
|---|---|
| [01-entorno-local.md](01-entorno-local.md) | Rutas de esta laptop, herramientas instaladas, versiones, restricciones del entorno |
| [02-proyecto.md](02-proyecto.md) | Estructura de módulos, versiones del build, comandos útiles |
| [03-migracion-android-16.md](03-migracion-android-16.md) | Registro completo de la migración a API 36 (agosto 2026) |
| [04-publicacion-play.md](04-publicacion-play.md) | Llave de firma, historial de versiones, cómo se hace un release |
| [tools/](tools/) | Scripts de verificación de AAB y de alineación de páginas de 16 KB |

## Reglas de trabajo con este repo

1. **No hagas commits sin que Gabriel lo pida explícitamente.** Deja los cambios
   en el árbol de trabajo y descríbelos.
2. **Nunca escribas contraseñas en ningún archivo**, ni siquiera en los de esta
   carpeta. La contraseña de la llave de subida existe, la tiene Gabriel, y no
   está documentada en ningún sitio del repo a propósito.
3. **Android Studio está abierto sobre este proyecto y modifica archivos por su
   cuenta.** Durante la migración de agosto 2026, el Upgrade Assistant cambió
   `build.gradle`, `gradle.properties` y `gradle-wrapper.properties` a mitad de
   la sesión. Si ves cambios que no hiciste tú, probablemente vengan de ahí:
   revisa el diff antes de atribuirlos.
4. **Verifica, no supongas.** Casi todo lo que se dio por sentado al empezar la
   migración resultó falso (ver [03](03-migracion-android-16.md)). Las
   herramientas de `tools/` existen justo para eso.

## Estado actual en una línea

`targetSdk 36`, `versionCode 5`, `versionName 1.3.1`, publicada en Alpha el
14 ago 2026, cumpliendo el requisito de nivel de API de Play cuya fecha límite
era el 31 de agosto de 2026.
