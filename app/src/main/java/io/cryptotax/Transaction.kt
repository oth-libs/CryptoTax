package io.cryptotax

import java.math.BigDecimal

data class Transaction(
    val transactionType: TransactionType,
    val date: String,
    val from: String,
    val fromQuantity: BigDecimal,
    val fromValue: BigDecimal,
    val to: String,
    var toQuantity: BigDecimal,
    val toValue: BigDecimal,
    val fee: BigDecimal
) {
    var remainingToQuantity: BigDecimal = BigDecimal(toQuantity.toString())
}

enum class TransactionType {
    BUY,
    SELL,
    TRADE,
    TRANSFER
}