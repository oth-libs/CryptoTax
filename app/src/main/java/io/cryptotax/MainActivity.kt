package io.cryptotax

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.math.BigDecimal

class MainActivity : AppCompatActivity() {

  val transactions = mutableListOf<Transaction>()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    transactions.addAll(
        Utils.toObject(
            """
[{"transactionType":"BUY","date":"31.01.2021-5:40:00","fee":5,"from":"EUR","fromQuantity":245,"fromValue":245,"to":"DOGE","toQuantity":9131.6,"toValue":245},{"transactionType":"BUY","date":"01.02.2021-03:26:00","fee":4,"from":"EUR","fromQuantity":196,"fromValue":196,"to":"DOGE","toQuantity":6227.1,"toValue":196},{"transactionType":"BUY","date":"01.02.2021-18:34:31","fee":5.4,"from":"EUR","fromQuantity":294.6,"fromValue":294.6,"to":"DOGE","toQuantity":8992.6,"toValue":294.6},{"transactionType":"BUY","date":"02.02.2021-12:53:30","fee":9.03,"from":"EUR","fromQuantity":490.97,"fromValue":490.97,"to":"ADA","toQuantity":1411.9,"toValue":490.97},{"transactionType":"TRADE","date":"02.02.2021-14:52:21","fee":0.1,"from":"DOGE","fromQuantity":4000,"fromValue":102.88,"to":"BTC","toQuantity":0.00375624,"toValue":102.78},{"transactionType":"TRADE","date":"02.02.2021-14:56:06","fee":0.11,"from":"BTC","fromQuantity":0.0037504,"fromValue":102.62,"to":"ADA","toQuantity":292.707,"toValue":102.51},{"transactionType":"TRANSFER","date":"02.02.2021-19:21:53","fee":0.11,"from":"ADA","fromQuantity":539.9,"fromValue":177.51,"to":"WALLET","toQuantity":0,"toValue":0},{"transactionType":"BUY","date":"03.02.2021-4:28:03","fee":3.57,"from":"EUR","fromQuantity":196.43,"fromValue":196.43,"to":"BTC","toQuantity":0.006541,"toValue":196.43},{"transactionType":"TRADE","date":"03.02.2021-4:30:57","fee":0.19,"from":"BTC","fromQuantity":0.00654717,"fromValue":190.3,"to":"STMX","toQuantity":31145.823,"toValue":190.11},{"transactionType":"TRADE","date":"03.02.2021-11:10:08","fee":0.2,"from":"STMX","fromQuantity":31145,"fromValue":199.15,"to":"BTC","toQuantity":0.00684505,"toValue":198.95},{"transactionType":"TRADE","date":"03.02.2021-11:30:35","fee":0.19,"from":"BTC","fromQuantity":0.00684504,"fromValue":198.95,"to":"STMX","toQuantity":28492.479,"toValue":198.76},{"transactionType":"TRADE","date":"03.02.2021-13:36:58","fee":0.28,"from":"STMX","fromQuantity":28493,"fromValue":281.57,"to":"BTC","toQuantity":0.00967793,"toValue":281.29},{"transactionType":"TRADE","date":"03.02.2021-13:40:30","fee":0.28,"from":"BTC","fromQuantity":0.00967785,"fromValue":281.29,"to":"STMX","toQuantity":24790.185,"toValue":281.01},{"transactionType":"TRADE","date":"03.02.2021-18:03:01","fee":0.2,"from":"STMX","fromQuantity":24790,"fromValue":208.95,"to":"BTC","toQuantity":0.00718191,"toValue":208.75},{"transactionType":"TRADE","date":"03.02.2021-18:48:44","fee":0.03,"from":"BTC","fromQuantity":0.00083498,"fromValue":24.27,"to":"DOT","toQuantity":1.5984,"toValue":24.24},{"transactionType":"TRADE","date":"03.02.2021-18:48:44","fee":0.15,"from":"BTC","fromQuantity":0.00547953,"fromValue":159.26,"to":"DOT","toQuantity":10.4895,"toValue":159.11},{"transactionType":"TRADE","date":"03.02.2021-18:48:58","fee":0.03,"from":"BTC","fromQuantity":0.00083498,"fromValue":24.27,"to":"DOT","toQuantity":1.5984,"toValue":24.24},{"transactionType":"BUY","date":"03.02.2021-13:26:55","fee":9,"from":"EUR","fromQuantity":491,"fromValue":491,"to":"BTC","toQuantity":0.015538,"toValue":491},{"transactionType":"TRADE","date":"04.02.2021-1:36:55","fee":0.26,"from":"BTC","fromQuantity":0.008296,"fromValue":254.78,"to":"LTC","toQuantity":1.998,"toValue":254.52},{"transactionType":"TRADE","date":"04.02.2021-1:43:50","fee":0.26,"from":"BTC","fromQuantity":0.00335821,"fromValue":103.13,"to":"ETH","toQuantity":0.075924,"toValue":103.03},{"transactionType":"TRADE","date":"04.02.2021-1:43:50","fee":0.12,"from":"BTC","fromQuantity":0.00384427,"fromValue":118.06,"to":"ETH","toQuantity":0.086913,"toValue":117.94},{"transactionType":"SELL","date":"04.02.2021-1:43:50","fee":0,"from":"DOGE","fromQuantity":20351.2,"fromValue":867.164632,"to":"EUR","toQuantity":867.164632,"toValue":867.164632},{"transactionType":"SELL","date":"04.02.2021-12:08:30","fee":0,"from":"EUR","fromQuantity":867.16364,"fromValue":867.16364,"to":"DOGE","toQuantity":19574.8,"toValue":867.16364}]
    """.trimIndent()))

    result.text = transactions.toStringS()

    add.setOnClickListener {
      transactions.add(
          Transaction(
              transactionType = if (from.stringText() == "EUR") TransactionType.BUY else if (to.stringText() == "EUR") TransactionType.SELL else TransactionType.TRADE,
              date = "Calendar.getInstance().time.time",
              from = from.stringText(),
              fromQuantity = fromQuantity.bigDecimalText(),
              fromValue = fromValue.bigDecimalText(),
              to = to.stringText(),
              toQuantity = toQuantity.bigDecimalText(),
              toValue = toValue.bigDecimalText(),
              fee = fee.bigDecimalText()))

      result.text = transactions.toStringS()
    }

    calculate.setOnClickListener {

      gainz.text = "gainz/losses per transaction \n"

      val tempTransactions = transactions.map { it.copy() }.toMutableList()
      //      tempTransactions.reverse()

      var gainLoss = BigDecimal.ZERO

      transactions.forEachIndexed { index, transaction ->
        when (transaction.transactionType) {

          TransactionType.BUY -> {
            gainz.text = "${gainz.text}transaction #$index: buy fee: ${-transaction.fee}\n"
            gainLoss += -transaction.fee
          }

          TransactionType.TRADE, TransactionType.SELL -> {
            var amountToSell = transaction.fromQuantity

            while (amountToSell > BigDecimal.ZERO) {
              tempTransactions.firstOrNull { it.to == transaction.from && it.remainingToQuantity > BigDecimal.ZERO }?.let { boughtCoin ->

                val microTradeAmount = (if (boughtCoin.remainingToQuantity >= amountToSell) amountToSell else boughtCoin.remainingToQuantity)

                val costBase = microTradeAmount * boughtCoin.toValue / boughtCoin.toQuantity

                gainz.text =
                  "${gainz.text}transaction #$index: ${((microTradeAmount * transaction.fromValue) / transaction.fromQuantity) - costBase} - inc. fee: ${(((microTradeAmount * transaction.fromValue) / transaction.fromQuantity) - costBase - ((microTradeAmount * transaction.fee) / transaction.fromQuantity))}\n"
                gainLoss += (((microTradeAmount * transaction.fromValue) / transaction.fromQuantity) - costBase - ((microTradeAmount * transaction.fee) / transaction.fromQuantity))

                boughtCoin.remainingToQuantity -= microTradeAmount
                amountToSell -= microTradeAmount

              } ?: run {
                amountToSell = BigDecimal.ZERO // missing amounts from binance report - just skip
              }
            }
          }

          //          TransactionType.SELL -> {
          //            var amountToSell = transaction.fromQuantity
          //
          //            while (amountToSell > BigDecimal.ZERO) {
          //              tempTransactions.firstOrNull { it.to == transaction.from && it.remainingToQuantity > BigDecimal.ZERO }?.let { boughtCoin ->
          //
          //                val microTradeAmount = (if (boughtCoin.remainingToQuantity >= amountToSell) amountToSell else boughtCoin.remainingToQuantity)
          //
          //                val costBase = microTradeAmount * boughtCoin.toValue / boughtCoin.toQuantity
          //
          //                gainz.text =
          //                  "${gainz.text}transaction #$index: ${((microTradeAmount * transaction.fromValue) / transaction.fromQuantity) - costBase} - inc. fee: ${(((microTradeAmount * transaction.fromValue) / transaction.fromQuantity) - costBase - ((microTradeAmount * transaction.fee) / transaction.fromQuantity))}\n"
          //                gainLoss += (((microTradeAmount * transaction.fromValue) / transaction.fromQuantity) - costBase - ((microTradeAmount * transaction.fee) / transaction.fromQuantity))
          //
          //                boughtCoin.remainingToQuantity -= microTradeAmount
          //                amountToSell -= microTradeAmount
          //
          //              } ?: run {
          //                amountToSell = BigDecimal.ZERO // missing amounts from binance report - just skip
          //              }
          //            }
          //          }

          TransactionType.TRANSFER -> {
            var amountToTransfer = transaction.fromQuantity

            while (amountToTransfer > BigDecimal.ZERO) {
              tempTransactions.first { it.to == transaction.from }.let { boughtCoin ->

                if (boughtCoin.remainingToQuantity >= amountToTransfer) { // one source needed
                  boughtCoin.remainingToQuantity -= amountToTransfer
                  amountToTransfer = BigDecimal.ZERO
                } else {
                  amountToTransfer -= boughtCoin.remainingToQuantity
                  boughtCoin.toQuantity = BigDecimal.ZERO
                }

              }
            }
          }
        }
      }

      gainz.text = "${gainz.text}total: $gainLoss\n\n"

      tempTransactions.toStringS().log()

      val remainingBalances = mutableMapOf<String, BigDecimal>()
      tempTransactions.forEach {
        remainingBalances[it.to] = (remainingBalances[it.to] ?: BigDecimal.ZERO) + it.remainingToQuantity
      }

      "remaining balances: $remainingBalances".log()
    }
  }
}