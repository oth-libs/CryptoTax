package io.cryptotax

import android.util.Log
import android.widget.EditText

fun EditText.stringText() = text.toString()
fun EditText.bigDecimalText() = text.toString().toBigDecimal()

fun <T> MutableList<T>.toStringS() = StringBuilder().apply {
    append("size: ${size}\n\n")
    forEachIndexed { index: Int, c: T ->
        append("$index -- ${c.toString()}\n\n")
    }
}.toString()

fun String.log() {
    Log.e("CryptoTaxTag", this)
}
