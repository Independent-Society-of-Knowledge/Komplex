package org.isk.komplex

import org.isk.komplex.Complex
import org.isk.komplex.toComplex
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

fun Sequence<Complex>.fft(): List<Complex> {
    val signal: List<Complex> = this.toList()
    val n = signal.size
    if (n == 1) return signal


    val evenSignal = List(n / 2) { signal[2 * it] }
    val oddSignal = List(n / 2) { signal[2 * it + 1] }

    val evenFFT = evenSignal.asSequence().fft()
    val oddFFT = oddSignal.asSequence().fft()

    val result = List(n) { Complex(0.0, 0.0) }.toMutableList()
    for (k in 0 until n / 2) {
        val t = oddFFT[k] * Complex(cos(2 * Math.PI * k / n), -sin(2 * Math.PI * k / n))
        result[k] = evenFFT[k] + t
        result[k + n / 2] = evenFFT[k] - t
    }

    return result
}

fun main(){
    fun sampleMathFunction(
        start: Double,
        end: Double,
        step: Double,
        mathFunction: (Double) -> Double,
    ): List<Double> {
        val sampledValues = mutableListOf<Double>()

        var x = start
        while (x <= end) {
            val y = mathFunction(x)
            sampledValues.add(y)

            x += step
        }

        return sampledValues
    }

    val myFFT =
        listOf<Double>(0.0, 1.0, 0.0)
        .asSequence()
        .map { it.toComplex() }
        .fft()
    println(myFFT)




}
