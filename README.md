# ZebPayTask
# KList DSL – Optimized Jetpack Compose List Builder

A modular, type-safe, and fully chainable list DSL for Jetpack Compose!

## Features

- **Chainable modifiers**: `.padding()`, `.clickable {}`, `.header()`, `.items()`, `.section()`, `.dividers()`
- **Single or multiple sections** with different types
- **Animated item placement**
- **Dividers** between items
- **Idiomatic usage** for both simple and complex lists

## Usage Example

```kotlin
@Composable
fun HomeScreen() {
    // Single section, chainable
    KList.create<Coin>()
        .padding(16.dp)
        .header("Coins")
        .items(coinList) { KListItem(it) }
        .dividers(true)
        .clickable { /* handle click */ }
        .render()

    // Multiple sections (mixed types, if needed)
    KList.create<Any>()
        .padding(16.dp)
        .dividers(true)
        .section("Coins", coinList) { KListItem(it as Coin) }
        .section("Stocks", stockList) { StockListItem(it as Stock) }
        .render()
}
```

## Files

- `KList.kt` – Core KList DSL implementation
- `KListItem.kt` – Sample item composables
- `KListDemo.kt` – Usage demo

---
