package cz.upce.vetalmael.data.source.animal

import cz.upce.vetalmael.api.VetAlmaelApi
import cz.upce.vetalmael.data.model.Animal

class AnimalRepositoryImpl(
    private val api: VetAlmaelApi
) : AnimalRepository {

    private var animalsCache: List<Animal> = emptyList()

    override suspend fun getAnimals(force: Boolean): List<Animal> {
        if (!force && animalsCache.isNotEmpty()) {
            return animalsCache
        }

        val animals = api.getAnimals()
        animalsCache = animals

        return animals.toList()
    }

    override suspend fun getAnimal(id: Int): Animal {
        val predicate: (Animal) -> Boolean = { it.idAnimal == id }
        return getAnimals(false).find(predicate) ?: api.getAnimal(id)
    }

    override fun erase() {
        animalsCache = emptyList()
    }
}