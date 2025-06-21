package com.zebpay.task

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class KListSection<T>(
    val header: String? = null,
    val items: List<T> = emptyList(),
    val itemContent: (@Composable (T) -> Unit)? = null
)

class KList<T> private constructor(
    private val padding: Dp = 0.dp,
    private val onClick: (() -> Unit)? = null,
    private val dividers: Boolean = false,
    private val sections: List<KListSection<T>> = emptyList(),
) {
    // Chainable modifiers
    fun padding(padding: Dp) = KList(
        padding = padding,
        onClick = onClick,
        dividers = dividers,
        sections = sections
    )

    fun clickable(onClick: () -> Unit) = KList(
        padding = padding,
        onClick = onClick,
        dividers = dividers,
        sections = sections
    )

    fun dividers(enabled: Boolean = true) = KList(
        padding = padding,
        onClick = onClick,
        dividers = enabled,
        sections = sections
    )

    fun header(title: String): KList<T> {
        // Only add header if last section is empty or doesn't exist
        val newSections = if (sections.isNotEmpty() && sections.last().items.isEmpty()) {
            sections.dropLast(1) + KListSection<T>(header = title)
        } else {
            sections + KListSection<T>(header = title)
        }
        return KList(
            padding = padding,
            onClick = onClick,
            dividers = dividers,
            sections = newSections
        )
    }

    fun items(items: List<T>, itemContent: @Composable (T) -> Unit): KList<T> {
        // If last section has a header but no items, attach items to it
        val newSections = if (sections.isNotEmpty() && sections.last().items.isEmpty() && sections.last().header != null) {
            sections.dropLast(1) + KListSection(
                header = sections.last().header,
                items = items,
                itemContent = itemContent
            )
        } else {
            sections + KListSection(
                header = null,
                items = items,
                itemContent = itemContent
            )
        }
        return KList(
            padding = padding,
            onClick = onClick,
            dividers = dividers,
            sections = newSections
        )
    }

    fun section(header: String? = null, items: List<T>, itemContent: @Composable (T) -> Unit): KList<T> {
        return KList(
            padding = padding,
            onClick = onClick,
            dividers = dividers,
            sections = sections + KListSection(header, items, itemContent)
        )
    }

    @Composable
    fun render() {
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .then(if (onClick != null) Modifier.clickable { onClick.invoke() } else Modifier)
        ) {
            sections.forEach { section ->
                section.header?.let { header ->
                    item {
                        Box(
                            Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.primaryContainer)
                                .padding(vertical = 8.dp)
                        ) {
                            Text(
                                text = header,
                                style = MaterialTheme.typography.headlineSmall,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                        }
                    }
                }
                if (section.items.isNotEmpty() && section.itemContent != null) {
                    itemsIndexed(section.items) { idx, item ->
                        Box(Modifier.animateContentSize()) {
                            section.itemContent.invoke(item)
                        }
                        if (dividers && idx < section.items.lastIndex) {
                            Divider(color = Color.LightGray, thickness = 1.dp)
                        }
                    }
                }
            }
        }
    }

    companion object {
        fun <T> create(): KList<T> = KList()
        // For legacy style: KList.Instance
        val Instance: KList<Any?> = KList()
    }
}