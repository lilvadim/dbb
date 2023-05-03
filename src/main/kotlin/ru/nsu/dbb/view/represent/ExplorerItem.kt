package ru.nsu.dbb.view.represent

class ExplorerItem(
    val label: String,
    val reference: Any,
    val children: List<ExplorerItem> = listOf()
) {
    override fun toString(): String {
        return label
    }
}