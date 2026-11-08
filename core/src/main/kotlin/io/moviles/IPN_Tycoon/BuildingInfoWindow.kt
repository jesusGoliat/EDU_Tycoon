package io.moviles.IPN_Tycoon

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Stage
import com.kotcrab.vis.ui.widget.VisWindow
import io.moviles.IPN_Tycoon.rival.MercadoAlumnos
import io.moviles.IPN_Tycoon.rival.RivalState
import ktx.actors.onChange
import ktx.scene2d.*

class BuildingInfoWindow(
    private val data: Propiedad,
    private val onBuildingChanged: () -> Unit
) : VisWindow("Gestión del Plantel") {

    init {
        addCloseButton()
        closeOnEscape()
        isModal = false

        // La regla decide antes de tocar el botón; la ventana sólo la refleja.
        val resultado = ReglaCompra.evaluar(data)
        val costo     = ReglaCompra.costo(data)

        // Si el rival ya compró el edificio, la ventana sólo lo informa.
        val nivelRival = if (data.comprada) 0 else (RivalState.edificios[data.id] ?: 0)

        val btnTexto = when {
            nivelRival > 0 -> ""
            resultado == ResultadoCompra.NivelMaximo -> "NIVEL MÁXIMO"
            !data.comprada -> "COMPRAR  \$${fmt(costo)}"
            else           -> "MEJORAR LVL ${data.nivel + 1}  \$${fmt(costo)}"
        }

        add(scene2d.table {

            // ── Nombre ────────────────────────────────────────────────
            label(data.nombre) {
                color = Color.GOLD
            }.cell(padBottom = 6f)
            row()

            // ── Descripción ───────────────────────────────────────────
            label(data.descripcion) {
                setWrap(true)
                color = Color.LIGHT_GRAY
            }.cell(width = 280f, padBottom = 10f)
            row()

            // ── Stats ─────────────────────────────────────────────────
            label("Capacidad: ${data.capacidad} alumnos"); row()

            if (nivelRival > 0) {
                label("Propiedad del Instituto Rival (Nivel $nivelRival)") {
                    color = Color.SALMON
                }.cell(padTop = 6f)
                row()
            } else if (data.comprada) {
                label("Nivel actual: ${data.nivel} / ${data.mejoraMax}") {
                    color = Color.CYAN
                }.cell(padBottom = 2f)
                row()

                val ingresoCiclo = data.baseAlumnos * data.nivel * MercadoAlumnos.INGRESO_POR_ALUMNO
                label("Ingreso/ciclo: \$${fmt(ingresoCiclo)}") {
                    color = Color.GREEN
                }
                row()

                if (data.nivel < data.mejoraMax) {
                    val ingresoSiguiente = data.baseAlumnos * (data.nivel + 1) * MercadoAlumnos.INGRESO_POR_ALUMNO
                    label("Nivel ${data.nivel + 1}: \$${fmt(ingresoSiguiente)}/ciclo") {
                        color = Color.LIGHT_GRAY
                    }
                    row()
                }
            } else {
                val ingresoNivel1 = data.baseAlumnos * 1 * MercadoAlumnos.INGRESO_POR_ALUMNO
                label("Ingreso al comprar: \$${fmt(ingresoNivel1)}/ciclo") {
                    color = Color.GREEN
                }
                row()
            }

            // ── Saldo ─────────────────────────────────────────────────
            label("Tu saldo: \$${fmt(GameState.dinero)}") {
                color = Color.LIGHT_GRAY
            }.cell(padTop = 6f, padBottom = 4f)
            row()

            // ── Aviso (saldo insuficiente / costo inválido) ───────────
            val aviso = label(mensaje(resultado).orEmpty()) {
                color = Color.RED
                isVisible = text.isNotEmpty()
            }
            row()

            // ── Botón acción ──────────────────────────────────────────
            if (nivelRival == 0) textButton(btnTexto) {
                isDisabled = resultado != ResultadoCompra.Exitosa

                onChange {
                    if (isDisabled) return@onChange
                    // El rival pudo comprarlo mientras esta ventana estaba abierta.
                    if (!data.comprada && RivalState.posee(data.id)) {
                        setText("Propiedad del rival")
                        isDisabled = true
                        return@onChange
                    }

                    // El saldo pudo cambiar con la ventana abierta: aplicar vuelve a evaluar.
                    val final = ReglaCompra.aplicar(data)
                    if (final != ResultadoCompra.Exitosa) {
                        aviso.setText(mensaje(final).orEmpty())
                        aviso.isVisible = true
                        isDisabled = true
                        return@onChange
                    }

                    onBuildingChanged()
                    this@BuildingInfoWindow.remove()
                }
            }.cell(padTop = 14f, expandX = true, fillX = true)
        }).pad(16f)

        pack()
        centerWindow()
    }

    fun show(stage: Stage) { stage.addActor(this) }

    private fun mensaje(resultado: ResultadoCompra): String? = when (resultado) {
        is ResultadoCompra.SaldoInsuficiente -> "Te faltan \$${fmt(resultado.faltante)}"
        ResultadoCompra.CostoInvalido        -> "Costo inválido: no se puede comprar"
        ResultadoCompra.NivelMaximo,
        ResultadoCompra.Exitosa              -> null
    }

    private fun fmt(v: Long) = when {
        v >= 1_000_000L -> "${"%.2f".format(v / 1_000_000.0)}M"
        v >= 1_000L     -> "${"%.1f".format(v / 1_000.0)}K"
        else            -> v.toString()
    }
}
