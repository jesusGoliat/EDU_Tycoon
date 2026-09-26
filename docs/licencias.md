# Licencias y procedencia de recursos

Documento de la Entrega 1. Recoge la licencia del proyecto, el origen de los recursos que trae el
repositorio y las licencias de las dependencias. Solo se afirma lo que se pudo comprobar; lo que no
está documentado se indica de forma explícita.

---

## 1. Licencia del proyecto

- **Licencia:** MIT (archivo `LICENSE` en la raíz del repositorio).
- **Titular y año que declara el archivo:** `Copyright (c) 2026 Emmanuel Juarez Palma`.
- **Qué permite:** usar, copiar, modificar, publicar y distribuir el software, siempre que se conserve
  el aviso de copyright y el texto de la licencia. El software se entrega "tal cual", sin garantía.

Este repositorio del equipo es un *fork* de `gabrielhuav/EDU_Tycoon`. El archivo `LICENSE` tiene un único
commit en su historial (el inicial, de Emmanuel Juarez Palma): es el del proyecto original y el equipo
no lo modificó.

---

## 2. Procedencia de los recursos (`assets/`)

`assets/` contiene 282 archivos versionados. Se agrupan así:

| Recurso | Cantidad / archivos | Origen | Licencia |
|---|---|---|---|
| Fuente `font.ttf` | 1 | **Documentado dentro del propio archivo:** es *Press Start 2P*, de Cody "CodeMan38" Boisclair (© 2011). | SIL Open Font License 1.1 (OFL), según el metadato de la fuente. |
| Imágenes `.png` (edificios, terreno, sprites, fondos, íconos) | 273 | **No documentado en el repositorio.** | **No documentada.** |
| Mapa de Tiled (`Mapa_General.tmx`) y tilesets (`.tsx`) | 1 + 5 | **No documentado en el repositorio.** | **No documentada.** |
| Archivos de proyecto de Tiled (`Mundo.tiled-project`, `Mundo.tiled-session`) | 2 | Configuración del editor de mapas. | No aplica. |
| `assets.txt` | 1 (local) | Lo genera el build (`generateAssetList`); está en `.gitignore`. | No aplica. |

**Sobre las imágenes y el mapa, lo que sí se sabe y lo que no.**
- El repositorio **no incluye** ningún archivo de créditos, licencia ni `README` dentro de `assets/`, ni
  menciones de autoría en los tilesets ni en la documentación existente.
- El historial de git muestra quién **subió** esos archivos (tres cuentas: `Oziel Gomez`,
  `KZarcoSosa` y `EmmanuelJuarez14`). Eso **no demuestra** quién los creó ni con qué licencia.
- Por lo tanto, el origen y la licencia de las imágenes y del mapa se declaran como
  **«origen no documentado en el repositorio original»**. No se afirma que sean obra propia del equipo
  original, ni que provengan de un banco de recursos, ni si se usaron herramientas de generación de
  imágenes.
- **Pendiente para el equipo.** Confirmar con los autores del proyecto original (`gabrielhuav/EDU_Tycoon`)
  la procedencia de estas imágenes y, cuando se sepa, completar esta tabla.

**Otros recursos fuera de `assets/`.**
- Íconos de la app (`android/res/drawable-*`, `android/ic_launcher-web.png`, `android/res/drawable/logo_app.png`)
  y logo de la tienda (`PS/logo.png`): origen no documentado en el repositorio.
- Los bosquejos de `docs/diseno/` fueron elaborados por Jesús Ángel González Arellano con apoyo de IA
  (Claude), según el propio `docs/diseno/README.md`. Los diagramas de `docs/arquitectura.md` fueron
  elaborados por Javier Gamez con apoyo de IA (Claude), como indica su pie.

---

## 3. Licencias de las dependencias

Consultadas el 25/09/2026 en los repositorios oficiales o en el POM publicado. Las versiones son las
de `gradle.properties`. Esta tabla indica la licencia del **proyecto de código**; no se revisaron una
por una las licencias de sus dependencias transitivas.

| Dependencia | Versión | Licencia | Fuente consultada |
|---|---|---|---|
| libGDX (`gdx`, `gdx-freetype`, `gdx-box2d`, `gdx-backend-android`) | 1.14.0 | Apache-2.0 | github.com/libgdx/libgdx |
| libKTX (`ktx-*`) | 1.13.1-rc1 | **CC0-1.0** (dominio público) | github.com/libktx/ktx |
| VisUI (`vis-ui`) | 1.5.5 | Apache-2.0 | github.com/kotcrab/vis-ui |
| Ashley | 1.7.4 | Apache-2.0 | github.com/libgdx/ashley |
| gdx-ai | 1.8.2 | Apache-2.0 | github.com/libgdx/gdx-ai |
| artemis-odb | 2.3.0 | BSD-2-Clause (su README indica `BSD-2-Clause AND Apache-2.0` por código tomado de libGDX) | github.com/junkdog/artemis-odb |
| Room (`room-runtime`, `room-ktx`, `room-compiler`, `room-common`) | 2.7.0-alpha13 | Apache-2.0 | POM `room-runtime-2.7.0-alpha13.pom` en el repositorio Maven de Google |
| Kotlin (`kotlin-stdlib`) | 2.2.10 | Apache-2.0 | github.com/JetBrains/kotlin |
| kotlinx-coroutines | 1.10.2 | Apache-2.0 | github.com/Kotlin/kotlinx.coroutines |
| JUnit | 4.13.2 | EPL-1.0 (Eclipse Public License 1.0) | github.com/junit-team/junit4 |

**No verificado (se declara como pendiente):**
- `desugar_jdk_libs` (2.1.5), AndroidX Lifecycle (2.8.7) y AndroidX Test.
- Las bibliotecas nativas que empaqueta libGDX. En particular, `gdx-freetype` incluye la biblioteca
  FreeType, cuya licencia propia no se consultó.

**Nota.** Ashley, gdx-ai, artemis-odb, `gdx-box2d` y AndroidX Lifecycle están declaradas en los archivos de
construcción pero el código del proyecto no las importa (ver `docs/arquitectura.md`, sección 7).

---

## 4. Resumen

| Elemento | Licencia | ¿Documentada en el repo? |
|---|---|---|
| Código del proyecto | MIT | Sí (`LICENSE`) |
| Fuente `font.ttf` | SIL OFL 1.1 | Sí, dentro del archivo de la fuente |
| Imágenes y mapa de `assets/` | Desconocida | **No** |
| Íconos y logo de la app | Desconocida | **No** |
| Dependencias principales | Apache-2.0, CC0-1.0, BSD-2-Clause, EPL-1.0 | Verificadas para las principales (sección 3) |
