package io.moviles.IPN_Tycoon

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ReglaCompraTest {

    @Before
    fun setUp() {
        GameState.reset(presupuestoInicial = 100_000L)
    }

    private fun propiedad(
        precio: Long = 100_000L,
        mejoraMax: Int = 3,
        nivel: Int = 0,
        comprada: Boolean = false
    ) = Propiedad(
        id = "test",
        nombre = "Test",
        precio = precio,
        descripcion = "test",
        capacidad = 1,
        baseAlumnos = 1,
        mejoraMax = mejoraMax,
        nivel = nivel,
        comprada = comprada
    )

    @Test
    fun `aplicar buys the property when balance equals the price`() {
        val escuela = propiedad(precio = 100_000L)

        assertEquals(ResultadoCompra.Exitosa, ReglaCompra.aplicar(escuela))
        assertEquals(0L, GameState.dinero)
        assertTrue(escuela.comprada)
        assertEquals(1, escuela.nivel)
    }

    @Test
    fun `aplicar rejects a price one above balance and keeps the money`() {
        val escuela = propiedad(precio = 100_001L)

        assertEquals(ResultadoCompra.SaldoInsuficiente(1L), ReglaCompra.aplicar(escuela))
        assertEquals(100_000L, GameState.dinero)
        assertFalse(escuela.comprada)
        assertEquals(0, escuela.nivel)
    }

    @Test
    fun `aplicar reports the missing amount for the acceptance scenario`() {
        val escuela = propiedad(precio = 120_000L)

        assertEquals(ResultadoCompra.SaldoInsuficiente(20_000L), ReglaCompra.aplicar(escuela))
        assertEquals(100_000L, GameState.dinero)
    }

    @Test
    fun `aplicar upgrades using costoMejora`() {
        val escuela = propiedad(precio = 30_000L, nivel = 2, comprada = true)

        assertEquals(60_000L, ReglaCompra.costo(escuela))
        assertEquals(ResultadoCompra.Exitosa, ReglaCompra.aplicar(escuela))
        assertEquals(40_000L, GameState.dinero)
        assertEquals(3, escuela.nivel)
    }

    @Test
    fun `aplicar rejects an upgrade above balance`() {
        val escuela = propiedad(precio = 60_000L, nivel = 2, comprada = true)

        assertEquals(ResultadoCompra.SaldoInsuficiente(20_000L), ReglaCompra.aplicar(escuela))
        assertEquals(100_000L, GameState.dinero)
        assertEquals(2, escuela.nivel)
    }

    @Test
    fun `aplicar rejects upgrades at max level`() {
        val escuela = propiedad(precio = 1_000L, mejoraMax = 2, nivel = 2, comprada = true)

        assertEquals(ResultadoCompra.NivelMaximo, ReglaCompra.aplicar(escuela))
        assertEquals(100_000L, GameState.dinero)
        assertEquals(2, escuela.nivel)
    }

    @Test
    fun `aplicar rejects zero and negative prices`() {
        assertEquals(ResultadoCompra.CostoInvalido, ReglaCompra.aplicar(propiedad(precio = 0L)))
        assertEquals(ResultadoCompra.CostoInvalido, ReglaCompra.aplicar(propiedad(precio = -5_000L)))
        assertEquals(100_000L, GameState.dinero)
    }

    @Test
    fun `evaluar never changes balance or property`() {
        val escuela = propiedad(precio = 50_000L)

        assertEquals(ResultadoCompra.Exitosa, ReglaCompra.evaluar(escuela))
        assertEquals(100_000L, GameState.dinero)
        assertFalse(escuela.comprada)
        assertEquals(0, escuela.nivel)
    }

    @Test
    fun `evaluar has no side effects when balance is insufficient`() {
        val escuela = propiedad(precio = 120_000L)

        assertEquals(ResultadoCompra.SaldoInsuficiente(20_000L), ReglaCompra.evaluar(escuela))
        assertEquals(100_000L, GameState.dinero)
        assertFalse(escuela.comprada)
        assertEquals(0, escuela.nivel)
    }

    @Test
    fun `evaluar has no side effects at max level`() {
        val escuela = propiedad(precio = 1_000L, mejoraMax = 2, nivel = 2, comprada = true)

        assertEquals(ResultadoCompra.NivelMaximo, ReglaCompra.evaluar(escuela))
        assertEquals(100_000L, GameState.dinero)
        assertTrue(escuela.comprada)
        assertEquals(2, escuela.nivel)
    }
}
