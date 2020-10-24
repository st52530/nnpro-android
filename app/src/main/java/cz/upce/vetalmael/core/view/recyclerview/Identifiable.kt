package cz.upce.vetalmael.core.view.recyclerview

interface Identifiable {

    val id: String

    override fun equals(other: Any?): Boolean
}