"""Verifica un App Bundle (.aab) antes de subirlo a Google Play.

Comprueba tres cosas:
  1. El manifest: versionCode, versionName, targetSdk, appCategory.
  2. Que este firmado, y extrae el certificado para poder comparar la huella.
  3. Que las .so que lleva dentro cumplan la alineacion de 16 KB.

Uso:
    python checkaab.py android/release/android-release.aab

Despues, para ver la huella de la firma (no pide contrasena, el certificado es
publico y va dentro del propio bundle):

    keytool -printcert -file <el .RSA que deja en la carpeta temporal>

La huella debe coincidir con la documentada en 04-publicacion-play.md.

El manifest dentro de un AAB esta en formato protobuf, no en XML binario, asi
que aapt2 no sirve para leerlo. De ahi el decodificador de abajo.

Sin dependencias externas. Escrito para Python 3.7.
"""

import os
import sys
import tempfile
import zipfile

sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from elfalign import max_load_align  # noqa: E402


# ── Decodificador minimo de protobuf ──────────────────────────────────────
# Solo lo necesario para el esquema Resources.proto de aapt2:
#   XmlNode      { XmlElement element = 1 }
#   XmlElement   { string name = 3, repeated XmlAttribute attribute = 4,
#                  repeated XmlNode child = 5 }
#   XmlAttribute { string name = 2, string value = 3, Item compiled_item = 5 }

def _varint(buf, i):
    result = shift = 0
    while True:
        byte = buf[i]
        i += 1
        result |= (byte & 0x7F) << shift
        if not byte & 0x80:
            return result, i
        shift += 7


def _fields(buf):
    """Itera (numero_de_campo, wire_type, payload) sobre un mensaje protobuf."""
    i = 0
    while i < len(buf):
        key, i = _varint(buf, i)
        fnum, wtype = key >> 3, key & 7
        if wtype == 0:
            val, i = _varint(buf, i)
            yield fnum, wtype, val
        elif wtype == 2:
            length, i = _varint(buf, i)
            yield fnum, wtype, buf[i:i + length]
            i += length
        elif wtype == 5:
            yield fnum, wtype, buf[i:i + 4]
            i += 4
        elif wtype == 1:
            yield fnum, wtype, buf[i:i + 8]
            i += 8
        else:
            return


def _element(node):
    for fnum, wtype, val in _fields(node):
        if fnum == 1 and wtype == 2:
            return val
    return None


def _name(element):
    for fnum, wtype, val in _fields(element):
        if fnum == 3 and wtype == 2:
            return val.decode('utf-8', 'replace')
    return '?'


def _attrs(element):
    out = []
    for fnum, wtype, val in _fields(element):
        if fnum == 4 and wtype == 2:
            name = value = None
            for afn, awt, av in _fields(val):
                if afn == 2 and awt == 2:
                    name = av.decode('utf-8', 'replace')
                elif afn == 3 and awt == 2:
                    value = av.decode('utf-8', 'replace')
            out.append((name, value))
    return out


def _children(element):
    for fnum, wtype, val in _fields(element):
        if fnum == 5 and wtype == 2:
            yield val


# ── Comprobaciones ────────────────────────────────────────────────────────

def check_manifest(zf):
    root = _element(zf.read('base/manifest/AndroidManifest.xml'))
    wanted = ('package', 'versionCode', 'versionName')
    print('-- Manifest --')
    for name, value in _attrs(root):
        if name in wanted:
            print('   %-16s %s' % (name, value))
    for child in _children(root):
        el = _element(child)
        if el is None:
            continue
        tag = _name(el)
        if tag == 'uses-sdk':
            for name, value in _attrs(el):
                print('   %-16s %s' % (name, value))
        elif tag == 'application':
            for name, value in _attrs(el):
                if name in ('appCategory', 'isGame', 'extractNativeLibs'):
                    print('   %-16s %s' % (name, value))


def check_signature(zf, outdir):
    sigs = [n for n in zf.namelist()
            if n.startswith('META-INF/') and n.endswith(('.RSA', '.DSA', '.EC'))]
    print()
    print('-- Firma --')
    if not sigs:
        print('   *** SIN FIRMAR *** (bundleRelease no firma; hay que usar')
        print('       Android Studio -> Generate Signed App Bundle)')
        return
    for name in sigs:
        dest = os.path.join(outdir, os.path.basename(name))
        with open(dest, 'wb') as f:
            f.write(zf.read(name))
        print('   firmado con %s' % name)
        print('   certificado extraido en: %s' % dest)
        print('   ahora corre:  keytool -printcert -file "%s"' % dest)


def check_libs(zf, outdir):
    libs = [i for i in zf.infolist() if i.filename.endswith('.so')]
    print()
    print('-- Librerias nativas (paginas de 16 KB) --')
    if not libs:
        print('   el bundle no lleva codigo nativo')
        return
    bad = 0
    for info in libs:
        dest = os.path.join(outdir, os.path.basename(info.filename))
        with open(dest, 'wb') as f:
            f.write(zf.read(info.filename))
        align = max_load_align(dest)
        if align is None or align < 16384:
            print('   *** FALLA *** %s  p_align=%s' % (info.filename, align))
            bad += 1
    print('   %d de %d librerias cumplen 16 KB' % (len(libs) - bad, len(libs)))


def main(path):
    zf = zipfile.ZipFile(path)
    outdir = tempfile.mkdtemp(prefix='checkaab-')
    print('Bundle: %s  (%.1f MB)' % (path, os.path.getsize(path) / 1048576.0))
    print()
    check_manifest(zf)
    check_signature(zf, outdir)
    check_libs(zf, outdir)


if __name__ == '__main__':
    if len(sys.argv) != 2:
        print(__doc__)
        sys.exit(2)
    main(sys.argv[1])
