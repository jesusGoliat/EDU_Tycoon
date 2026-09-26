# EduTycoon: Campus en ruta

Juego de gestión (tycoon) ambientado en el IPN, hecho en **Kotlin + libGDX**. Solo corre en **Android**.
El jugador funda una escuela, compra y mejora edificios en un mapa isométrico y cobra ingresos cada
ciclo de juego.

Este repositorio es el proyecto del equipo para la **Entrega 1** de la materia. Es un *fork* de
[`gabrielhuav/EDU_Tycoon`](https://github.com/gabrielhuav/EDU_Tycoon).

| | |
|---|---|
| **Materia** | Desarrollo de aplicaciones móviles nativas |
| **Escuela** | IPN — ESCOM |
| **Profesor** | Gabriel Hurtado Avilés |
| **Grupo** | 7CV4 |
| **Entrega** | Entrega 1: idea, proyecto elegido y primer *pull request* |
| **Fecha de entrega** | 05/10/2026 |

**Integrantes**

| Nombre | Boleta | GitHub |
|---|---|---|
| Jesús Ángel González Arellano | 2022630690 | [`jesusGoliat`](https://github.com/jesusGoliat) |
| Javier Gamez | 2022630007 | [`Javier-Gamez`](https://github.com/Javier-Gamez) |

**Característica de la entrega:** regla de compra que impide gastar más saldo del disponible (issue #1,
integrada con el PR #2). Ver [`docs/idea.md`](docs/idea.md).

---

## Estructura del repositorio

```text
EDU_Tycoon/
├── core/         lógica del juego, pantallas y ventanas (Kotlin, libGDX) + pruebas unitarias
├── android/      arranque en Android (AndroidLauncher), base de datos Room y manifiesto
├── assets/       mapa Tiled, sprites, fondos y la fuente del juego
├── gradle/       wrapper de Gradle y JDK del daemon (21)
├── docs/         documentación de la entrega (idea, diseño, arquitectura, pruebas, licencias)
├── .github/      CI: pruebas de core + compilación del APK de depuración
└── README_for_IAs/  notas de entorno y publicación en Play Store
```

Detalle de cada carpeta, punto de entrada, diagrama de arquitectura y dependencias en
[`docs/arquitectura.md`](docs/arquitectura.md).

---

## Entorno probado

| | Javier (Windows) | Jesús (Ubuntu) |
|---|---|---|
| Sistema operativo | Windows 11 Home (10.0.26200) | Ubuntu (versión no registrada en este documento) |
| IDE | Android Studio (compilación 261.26222.65) | No registrado en este documento |
| JDK del sistema | Oracle JDK 24.0.2 | JDK 21 |
| JDK que usó Gradle | JetBrains JDK 21.0.11 (`C:\Users\JAVIER\.jdks\jbr-21.0.11`) | JDK 21 |
| Dispositivo | Samsung SM-G998U, por USB | Xiaomi POCO 2207117BPG, Android 13 (API 33), por USB |

Versiones que fija el proyecto (iguales para todos):

| Herramienta | Versión | Dónde se declara |
|---|---|---|
| Gradle | 9.5.0 | `gradle/wrapper/gradle-wrapper.properties` |
| Android Gradle Plugin (AGP) | 9.3.1 | `build.gradle` |
| Kotlin | 2.2.10 | `gradle.properties` |
| libGDX | 1.14.0 | `gradle.properties` |
| JDK del daemon de Gradle | 21 | `gradle/gradle-daemon-jvm.properties` |
| `compileSdk` / `targetSdk` / `minSdk` | 36 / 36 / 21 | `android/build.gradle` |

En el equipo de Javier el Android SDK tiene instaladas las plataformas 34, 35, 36 y 37.0, y las
Build-Tools 35.0.0 y 36.0.0. El CI usa `ubuntu-latest` con JDK 21 (Temurin).

> Las compilaciones de este documento se hicieron con `./gradlew` desde la terminal (Git Bash).
> Android Studio también sirve para abrir el proyecto; si tu versión no soporta AGP 9.3.1, usa la terminal.

---

## Pasos desde un clon limpio

Estos pasos se **probaron el 25/09/2026** en un clon nuevo de la rama `docs/entrega-1` (commit `7271c45`),
en Windows 11 con Git Bash. Con las dependencias ya descargadas, `:core:test` tardó 1 min 10 s y
`:android:assembleDebug` 1 min 38 s; la primera vez en una máquina nueva tardará más (no se midió).

**Requisitos:** Git, un JDK 21 (Gradle puede encontrar uno instalado o descargarlo), el Android SDK con
la plataforma 36 y un celular Android con depuración USB, o un emulador.

1. **Clonar el repositorio.**

   ```bash
   git clone https://github.com/jesusGoliat/EDU_Tycoon.git
   cd EDU_Tycoon
   ```

2. **Indicar dónde está el Android SDK.** Crea el archivo `local.properties` en la raíz (ya está en
   `.gitignore`, no se sube):

   ```properties
   # Windows (los dos puntos de la unidad van escapados con \)
   sdk.dir=C\:/Users/TU_USUARIO/AppData/Local/Android/Sdk

   # Linux
   sdk.dir=/home/TU_USUARIO/Android/Sdk
   ```

   La ruta exacta depende de dónde instalaste el SDK (en Android Studio: *Settings → Languages &
   Frameworks → Android SDK*).

3. **Ejecutar las pruebas unitarias.**

   ```bash
   ./gradlew :core:test
   ```

   Resultado esperado: termina con código de salida 0. En esta rama son **16 pruebas y 0 fallos**
   (3 de `GameStateTest`, 10 de `ReglaCompraTest`, 3 de `GameCycleEngineTest`). El reporte queda en
   `core/build/reports/tests/test/index.html`.

   > **Ojo:** Gradle **no imprime nada** cuando todo sale bien, porque `gradle.properties` fija
   > `org.gradle.logging.level=quiet`. Es normal; comprueba el código de salida o el reporte.

4. **Compilar el APK de depuración.**

   ```bash
   ./gradlew :android:assembleDebug
   ```

   El archivo queda en `android/build/outputs/apk/debug/android-debug.apk` (unos 169 MB).

5. **Activar la depuración USB en el celular.** *Ajustes → Acerca del teléfono*, toca siete veces el
   número de compilación para habilitar las **Opciones de desarrollador** y ahí activa **Depuración USB**.
   Los nombres exactos del menú cambian según el fabricante. Conecta el celular y acepta el aviso de
   autorización en la pantalla.

6. **Instalar y abrir la app.** Con `adb` (viene en `platform-tools` del Android SDK):

   ```bash
   adb devices
   adb install -r android/build/outputs/apk/debug/android-debug.apk
   ```

   `adb devices` debe listar tu celular como `device`. Luego abre **EduTycoon** desde el cajón de apps.

7. **Comprobar la regla de compra.** Crea una partida nueva (saldo inicial: $500.0K). Al iniciar, el mapa
   se ve muy ampliado; **pellizca con dos dedos** para alejar la cámara. Toca **Biológicas** ($1.25M):
   el botón `COMPRAR $1.25M` debe salir **deshabilitado** y debe aparecer en rojo «Te faltan $750.0K».

Al terminar, `git status` debe salir limpio: `assets/assets.txt`, que el build regenera, y
`local.properties` están en `.gitignore`.

---

## Problemas encontrados y cómo se resolvieron

| # | Quién / dónde | Qué pasó | Causa | Solución |
|---|---|---|---|---|
| 1 | Javier, Windows 11 | El JDK del sistema es el 24 (`java version "24.0.2"`), pero el proyecto exige el 21. | Gradle no usa el JDK del sistema: `gradle/gradle-daemon-jvm.properties` fija `toolchainVersion=21`. | No falló nada. Gradle encontró el JetBrains JDK 21.0.11 ya instalado en `C:\Users\JAVIER\.jdks\` y lo usó. Se comprobó con `./gradlew javaToolchains`. |
| 2 | Javier, Windows 11 | Los comandos `.\gradlew.bat :core:test` (PowerShell) y `./gradlew :core:test` (Git Bash) terminaban **sin imprimir nada**, lo que parecía un fallo. | `gradle.properties` tiene `org.gradle.logging.level=quiet`. | Se comprobó el resultado con el código de salida (0) y con los reportes de `core/build/test-results/test/` (14 pruebas en ese momento, 0 fallos). |
| 3 | Javier, Windows 11 | `cmd /c "gradlew.bat ..."` lanzado desde PowerShell respondió: *«"gradlew.bat" no se reconoce como un comando interno o externo, programa o archivo por lotes ejecutable»*. | El archivo `gradlew.bat` sí existe en la raíz del repositorio. La causa exacta **no se investigó**. | Se ejecutó `./gradlew` desde Git Bash, que funcionó a la primera. |
| 4 | Javier, celular Samsung SM-G998U | `adb install` falló: *`INSTALL_FAILED_UPDATE_INCOMPATIBLE: Existing package io.moviles.IPN_Tycoon signatures do not match newer version; ignoring!`* | El celular ya tenía instalada una app con el mismo `applicationId` (`io.moviles.IPN_Tycoon`) firmada con **otra clave**. Nuestro APK de depuración lleva la firma de depuración de esta computadora. | Se **desinstaló** la app existente (esto borra sus datos locales, como las partidas guardadas) y se volvió a ejecutar `adb install -r`. Instaló con `Success`. |
| 5 | Jesús, laptop Ubuntu | Gradle necesitó las Android SDK **Build-Tools 36**, que no estaban instaladas. | El proyecto compila con `compileSdk 36`. | Gradle las descargó e instaló solo. |
| 6 | Equipo, GitHub Actions | La primera corrida del CI **falló**. | El paso `android-actions/setup-android@v3` intentaba instalar el paquete antiguo `tools`, que `sdkmanager` ya no ofrece. | Se quitó ese paso (commit `38eb79e`): `ubuntu-latest` ya trae el Android SDK y AGP descarga las herramientas que falten. |
| 7 | Jesús, celular Xiaomi | `INSTALL_FAILED_USER_RESTRICTED` al instalar por USB. | MIUI restringe la instalación por USB hasta que se activa esa opción en el teléfono. | Activar *Opciones de desarrollador → Instalar vía USB* y aceptar el aviso en el teléfono. |
| 8 | Todo el equipo | El build regenera `assets/assets.txt`, que podría colarse en un commit ajeno. | La tarea `generateAssetList` lo escribe en cada compilación. | Está en `.gitignore` (`/assets/assets.txt`), así que no aparece en `git status`. Aun así, se revisa `git status` antes de cada commit. |

Los problemas 5 y 7 los reportó Jesús; los problemas 1 a 4 ocurrieron en el equipo de Javier durante esta
documentación. El detalle de las pruebas manuales y de los defectos conocidos del juego (#3, #4 y #5) está
en [`docs/pruebas.md`](docs/pruebas.md).

---

## Documentación

| Documento | Contenido |
|---|---|
| [`docs/idea.md`](docs/idea.md) | Ficha de la idea, historia de usuario y criterio de aceptación |
| [`docs/diseno/`](docs/diseno/) | Bosquejos de pantallas, estados alternos y recorrido del usuario |
| [`docs/arquitectura.md`](docs/arquitectura.md) | Estructura del repo, arquitectura, dependencias y qué corre en el dispositivo |
| [`docs/recorrido-codigo.md`](docs/recorrido-codigo.md) | La compra de un edificio de principio a fin, con fragmentos de código |
| [`docs/pruebas.md`](docs/pruebas.md) | Matriz de pruebas y defectos registrados |
| [`docs/licencias.md`](docs/licencias.md) | Licencia del proyecto, procedencia de recursos y licencias de dependencias |
| [`LICENSE`](LICENSE) | Licencia MIT |

---

## Tareas de Gradle útiles

El wrapper permite ejecutar Gradle sin instalarlo: `./gradlew` (Linux, macOS, Git Bash) o `gradlew.bat`
(Windows).

- `:core:test`: pruebas unitarias del módulo `core`.
- `:android:assembleDebug`: compila el APK de depuración.
- `clean`: borra las carpetas `build`.
- `android:lint`: valida el proyecto Android.
- `--offline`: usa las dependencias ya descargadas.
- `--refresh-dependencies`: vuelve a validar todas las dependencias.

Las tareas que no son de un módulo concreto se pueden ejecutar con el prefijo `nombre:`; por ejemplo,
`core:clean` borra solo la carpeta `build` de `core`.
