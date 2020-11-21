package cz.upce.vetalmael.data.source.animal

import cz.upce.vetalmael.base.repository.ErasableRepository
import cz.upce.vetalmael.data.model.Animal
import cz.upce.vetalmael.data.model.Message

interface AnimalRepository : ErasableRepository {

    suspend fun getAnimals(force: Boolean): List<Animal>

    suspend fun getAnimal(id: Int): Animal

    suspend fun addAnimal(name: String)

    suspend fun deleteAnimal(id: Int)

    suspend fun getMessages(animalId: Int): List<Message>

    suspend fun sendMessage(animalId: Int, message: String): Message
}