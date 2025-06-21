package com.zebpay.task

import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp

@Composable
fun KListDemo() {
    val coins = remember {
        listOf(
            Coin("Bitcoin", 67755.12),
            Coin("Ethereum", 3523.45),
            Coin("Solana", 140.30)
        )
    }
    val stocks = remember {
        listOf(
            Stock("AAPL", 4.2),
            Stock("GOOG", -1.3),
            Stock("TSLA", 6.7)
        )
    }
    val snackbarHostState = remember { SnackbarHostState() }
    var showSnackbar by remember { mutableStateOf(false) }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        // Single section style:
        KList.create<Coin>()
            .padding(16.dp)
            .header("Top Gainers")
            .items(coins) { KListItem(it) }
            .dividers(true)
            .clickable {
                showSnackbar = true
            }
            .render()

        // Or, multiple section style:
        /*
        KList.create<Any>()
            .padding(16.dp)
            .dividers(true)
            .section("Top Gainers", coins) { KListItem(it as Coin) }
            .section("Stocks", stocks) { StockListItem(it as Stock) }
            .render()
         */


        // Trigger the snackbar using side-effect
        if (showSnackbar) {
            LaunchedEffect(showSnackbar) {
                snackbarHostState.showSnackbar("KList clicked!")
                showSnackbar = false
            }
        }
    }
}