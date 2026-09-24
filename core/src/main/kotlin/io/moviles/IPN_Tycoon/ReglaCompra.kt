package io.moviles.IPN_Tycoon

/** Resultado de intentar comprar o mejorar una propiedad. */
sealed class ResultadoCompra {
    data object Exitosa : ResultadoCompra()
    data class SaldoInsuficiente(val faltante: Long) : ResultadoCompra()
    data object NivelMaximo : ResultadoCompra()
    data object CostoInvalido : ResultadoCompra()
}

/**
 * Regla única de compra: nunca se gasta más saldo del disponible.
 * [evaluar] no modifica nada (la UI la usa para habilitar el botón);
 * [aplicar] cobra y sube de nivel sólo si la evaluación es exitosa.
 */
object ReglaCompra {

    /** Precio de compra si aún no es nuestra; costo de mejora si ya lo es. */
    fun costo(propiedad: Propiedad): Long =
        if (!propiedad.comprada) propiedad.precio
        else GameState.costoMejora(propiedad)

    fun evaluar(propiedad: Propiedad): ResultadoCompra {
        if (propiedad.comprada && propiedad.nivel >= propiedad.mejoraMax) {
            return ResultadoCompra.NivelMaximo
        }

        val costo = costo(propiedad)
        if (costo <= 0L) return ResultadoCompra.CostoInvalido

        if (!GameState.puedeComprar(costo)) {
            return ResultadoCompra.SaldoInsuficiente(costo - GameState.dinero)
        }

        return ResultadoCompra.Exitosa
    }

    fun aplicar(propiedad: Propiedad): ResultadoCompra {
        val resultado = evaluar(propiedad)
        if (resultado != ResultadoCompra.Exitosa) return resultado

        val costo = costo(propiedad)
        if (!GameState.gastar(costo)) {
            return ResultadoCompra.SaldoInsuficiente(costo - GameState.dinero)
        }

        if (!propiedad.comprada) {
            propiedad.comprada = true
            propiedad.nivel    = 1
        } else {
            propiedad.nivel++
        }

        return ResultadoCompra.Exitosa
    }
}
