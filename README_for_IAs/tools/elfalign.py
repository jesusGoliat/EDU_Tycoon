"""Comprueba la alineacion de pagina de librerias nativas (.so).

Requisito de Google Play: las apps que apuntan a API 35+ deben soportar
dispositivos con paginas de memoria de 16 KB. Se mide por el p_align de los
segmentos PT_LOAD de cada ELF, que debe ser >= 16384.

Uso:
    python elfalign.py <carpeta>

Ejemplo:
    python elfalign.py android/libs

Sin dependencias externas. Escrito para Python 3.7.
"""

import glob
import os
import struct
import sys

PT_LOAD = 1


def max_load_align(path):
    """Devuelve el mayor p_align entre los segmentos PT_LOAD, o None si no es ELF."""
    with open(path, 'rb') as f:
        data = f.read()
    if data[:4] != b'\x7fELF':
        return None
    is64 = data[4] == 2
    if is64:
        e_phoff = struct.unpack_from('<Q', data, 0x20)[0]
        e_phentsize = struct.unpack_from('<H', data, 0x36)[0]
        e_phnum = struct.unpack_from('<H', data, 0x38)[0]
    else:
        e_phoff = struct.unpack_from('<I', data, 0x1C)[0]
        e_phentsize = struct.unpack_from('<H', data, 0x2A)[0]
        e_phnum = struct.unpack_from('<H', data, 0x2C)[0]

    best = 0
    for i in range(e_phnum):
        off = e_phoff + i * e_phentsize
        if struct.unpack_from('<I', data, off)[0] != PT_LOAD:
            continue
        if is64:
            align = struct.unpack_from('<Q', data, off + 0x30)[0]
        else:
            align = struct.unpack_from('<I', data, off + 0x1C)[0]
        best = max(best, align)
    return best


def check_dir(root):
    """Revisa todos los .so bajo root. Devuelve el numero de librerias que fallan."""
    paths = sorted(glob.glob(os.path.join(root, '**', '*.so'), recursive=True))
    if not paths:
        print('No se encontro ninguna .so en %s' % root)
        return 0

    bad = 0
    for p in paths:
        align = max_load_align(p)
        rel = os.path.relpath(p, root)
        if align is None:
            print('%-52s NO ES ELF' % rel)
            bad += 1
        elif align >= 16384:
            print('%-52s p_align=%-8d OK 16KB' % (rel, align))
        else:
            print('%-52s p_align=%-8d *** FALLA ***' % (rel, align))
            bad += 1

    print()
    print('%d de %d librerias cumplen 16 KB' % (len(paths) - bad, len(paths)))
    return bad


if __name__ == '__main__':
    if len(sys.argv) != 2:
        print(__doc__)
        sys.exit(2)
    sys.exit(1 if check_dir(sys.argv[1]) else 0)
