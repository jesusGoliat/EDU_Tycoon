# Entorno local — laptop Windows de Gabriel

Todo lo de aquí está verificado en esta máquina el 15 de agosto de 2026. Si
trabajas en otra PC, nada de esto aplica tal cual.

## Máquina

| | |
|---|---|
| SO | Windows 11 Pro 10.0.26200 |
| Usuario | `gabri` |
| Shell disponible | PowerShell (principal) y Git Bash |

## Rutas del proyecto

```
C:\Users\gabri\AndroidStudioProjects\EDU_Tycoon
```

### ⚠️ Restricción crítica: rutas sin caracteres no ASCII

El Android Gradle Plugin **rechaza rutas con acentos o caracteres no ASCII**. Por
eso este repo NO vive en la carpeta de GitHub Desktop, que es:

```
C:\Users\gabri\OneDrive\Imágenes\Documentos\GitHubDesktop\    ← tiene acento, no sirve
```

Si alguna vez mueves el proyecto, tiene que ser a una ruta ASCII pura. Los demás
proyectos Android de Gabriel viven por la misma razón en
`C:\Users\gabri\AndroidStudioProjects\`.

## Herramientas instaladas

### JDK

```
JAVA_HOME = C:\Program Files\Amazon Corretto\jdk21.0.4_7
java      = C:\Program Files\Amazon Corretto\jdk21.0.4_7\bin\java.exe
versión   = OpenJDK 21.0.4 LTS (Corretto 21.0.4.7.1)
```

**No hace falta JDK 17.** El proyecto compila bytecode Java 17
(`sourceCompatibility 17`, `jvmTarget JVM_17`), pero eso es independiente del JDK
que ejecuta Gradle. Corretto 21 funciona.

### Android SDK

```
C:\Users\gabri\AppData\Local\Android\Sdk
```

Declarado en `local.properties` (que está en `.gitignore`, no lo commitees).

| Componente | Versiones instaladas |
|---|---|
| Platforms | android-33, android-34, android-35, **android-36**, android-36.1 |
| Build-tools | 30.0.3, 34.0.0, 35.0.0, **36.0.0** |
| NDK | 25.1.8937393, 26.3.11579264, 27.0.12077973 |
| CMake | 3.22.1 |
| platform-tools | `...\Sdk\platform-tools\adb.exe` |

El NDK está instalado pero **este proyecto no lo usa**: libGDX trae sus `.so` ya
compiladas y no hay `externalNativeBuild`. Ver
[03-migracion-android-16.md](03-migracion-android-16.md) para por qué eso importa.

### Android Studio

```
C:\Program Files\Android\Android Studio
```

Se usa para dos cosas que no se hacen por línea de comandos: correr la app en un
dispositivo y **generar el bundle firmado** (Build → Generate Signed App Bundle).

### Python

```
Python 3.7.9   (disponible como `python` en el PATH)
```

Los scripts de `tools/` están escritos para 3.7, sin dependencias externas.

### Gradle

No hay Gradle instalado globalmente; se usa el wrapper del proyecto
(`./gradlew` en Git Bash, `.\gradlew.bat` en PowerShell). El caché vive en
`C:\Users\gabri\.gradle\`.

No hay scripts de init en `~/.gradle/init.d/` ni un `~/.gradle/gradle.properties`
global — se verificó, no existen. Todo lo que afecta el build está en el repo.

## Material de la Play Store y firma

Todo lo importante del proyecto que **no** vive en el repositorio está aquí:

```
C:\Users\gabri\Downloads\EduTycoon PlayStore\
```

Contiene la llave de subida (`Edu_Tycoon`, sin extensión) y material sensible
adicional. Nada de esa carpeta debe copiarse al repo ni a un artefacto ni a un
chat.

La llave es un almacén **PKCS12** (empieza con `30 82`), 2628 bytes, alias `KEY0`.
Es la llave de subida real de la app publicada — verificado comparando su huella
con la del AAB que ya estaba en Play. Detalles en
[04-publicacion-play.md](04-publicacion-play.md).

**La contraseña no está documentada en ningún archivo de este repo, y así debe
seguir. No la pidas, no la leas, no la escribas.**

### Riesgos abiertos de esta carpeta

1. **La llave y su contraseña están juntas.** Cualquiera con acceso al disco
   obtiene las dos y puede firmar actualizaciones como si fuera el desarrollador.
   Conviene separarlas.
2. **Está en `Downloads`**, que es donde escribe el navegador y la primera carpeta
   que la gente vacía sin mirar. Un borrado accidental sin respaldo significa que
   la app **nunca más se puede actualizar**: Play rechaza cualquier AAB firmado
   con otra llave.
3. **La llave no tiene extensión**, así que los patrones `*.jks` del `.gitignore`
   no la atrapan si alguna vez acaba dentro del repo.

Respaldo recomendado: una copia cifrada fuera de este equipo.

## Otros proyectos de Gabriel en esta máquina

Útiles como referencia, no toques nada de ahí sin pedirlo:

```
C:\Users\gabri\AndroidStudioProjects\BattleNaval_V2         ← BitBattles
C:\Users\gabri\AndroidStudioProjects\PolitecnicoOpenWorld
```

`BattleNaval_V2\docs\ANDROID-16.md` documenta la misma migración a API 36 hecha
en una app de layouts XML. Sirve de contraste: ahí los problemas fueron
predictive back, edge-to-edge y orientación en pantallas grandes. En EDU Tycoon
**ninguno de los tres aplicó**, por ser libGDX y estar declarado como juego.
