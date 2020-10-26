package cz.upce.vetalmael.data.source.animal

import cz.upce.vetalmael.base.repository.ErasableRepository
import cz.upce.vetalmael.data.model.Animal

interface AnimalRepository : ErasableRepository {

    suspend fun getAnimals(): List<Animal>

    suspend fun getAnimal(id: Int): Animal
}