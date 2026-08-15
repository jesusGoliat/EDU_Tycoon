"""Genera el zip de simbolos de depuracion nativos para subir a Play Console.

Play avisa en cada subida que el bundle lleva codigo nativo sin simbolos. En
este proyecto la opcion `debugSymbolLevel` del build NO sirve: AGP solo extrae
simbolos del codigo nativo que compila el propio proyecto con externalNativeBuild,
y las .so de libGDX llegan precompiladas por jniLibs. Ver
03-migracion-android-16.md.

La via manual es esta: empaquetar las .so con estructura <abi>/<lib>.so y subir
el zip en Play Console -> Explorador de bundles -> la version -> Descargas.

Uso (desde la raiz del repo):
    python README_for_IAs/tools/mksymbols.py

Deja el zip en android/release/native-debug-symbols-v<versionCode>.zip, que esta
ignorado por git.

Sin dependencias externas. Escrito para Python 3.7.
"""

import os
import re
import sys
import zipfile

ABIS = ['arm64-v8a', 'armeabi-v7a', 'x86', 'x86_64']


def version_code(repo):
    """Lee el versionCode de android/build.gradle."""
    path = os.path.join(repo, 'android', 'build.gradle')
    with open(path, encoding='utf-8') as f:
        match = re.search(r'^\s*versionCode\s+(\d+)', f.read(), re.M)
    return match.group(1) if match else 'unknown'


def main():
    here = os.path.dirname(os.path.abspath(__file__))
    repo = os.path.abspath(os.path.join(here, '..', '..'))
    libs = os.path.join(repo, 'android', 'libs')

    if not os.path.isdir(libs):
        print('No existe %s. Corre antes:  ./gradlew :android:bundleRelease' % libs)
        return 1

    outdir = os.path.join(repo, 'android', 'release')
    os.makedirs(outdir, exist_ok=True)
    out = os.path.join(outdir, 'native-debug-symbols-v%s.zip' % version_code(repo))

    count = 0
    with zipfile.ZipFile(out, 'w', zipfile.ZIP_DEFLATED) as zf:
        for abi in ABIS:
            d = os.path.join(libs, abi)
            if not os.path.isdir(d):
                continue
            for name in sorted(os.listdir(d)):
                if name.endswith('.so'):
                    zf.write(os.path.join(d, name), '%s/%s' % (abi, name))
                    count += 1

    if not count:
        os.remove(out)
        print('No se encontro ninguna .so en %s' % libs)
        return 1

    print('%s' % out)
    print('%d librerias, %.1f MB' % (count, os.path.getsize(out) / 1048576.0))
    print()
    print('Subelo en Play Console -> Explorador de bundles -> la version -> Descargas')
    return 0


if __name__ == '__main__':
    sys.exit(main())
