package cz.upce.vetalmael.data.source.animal

import cz.upce.vetalmael.api.VetAlmaelApi
import cz.upce.vetalmael.data.model.Animal

class AnimalRepositoryImpl(
    private val api: VetAlmaelApi
) : AnimalRepository {

    private var animalsCache: List<Animal> = emptyList()

    override suspend fun getAnimals(): List<Animal> {
        if (animalsCache.isNotEmpty()) {
            return animalsCache
        }

        val animals = api.getAnimals()
        animalsCache = animals

        return animals.toList()
    }

    override suspend fun getAnimal(id: Int): Animal {
        val predicate: (Animal) -> Boolean = { it.idAnimal == id }
        return animalsCache.find(predicate) ?: api.getAnimals().find(predicate)!!
    }

    override fun erase() {
        animalsCache = emptyList()
    }
}