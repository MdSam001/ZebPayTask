package com.zebpay.task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

data class Coin(val name: String, val price: Double)
data class Stock(val symbol: String, val change: Double)

@Composable
fun KListItem(coin: Coin) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(text = coin.name, style = MaterialTheme.typography.bodyLarge)
            Spacer(Modifier.weight(1f))
            Text(
                text = "$${coin.price}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun StockListItem(stock: Stock) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(text = stock.symbol, style = MaterialTheme.typography.bodyLarge)
            Spacer(Modifier.weight(1f))
            Text(
                text = "${stock.change}%",
                style = MaterialTheme.typography.bodyMedium,
                color = if (stock.change >= 0) Color.Green else Color.Red
            )
        }
    }
}