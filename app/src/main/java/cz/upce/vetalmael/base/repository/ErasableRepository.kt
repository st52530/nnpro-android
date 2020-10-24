package cz.upce.vetalmael.base.repository

/**
 * Don't forget to bind the erasable repository interface to DI to make sure that it gets erased!
 */
interface ErasableRepository {

    fun erase()
}