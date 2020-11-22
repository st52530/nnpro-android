package cz.upce.vetalmael.data.source.animal

import cz.upce.vetalmael.api.VetAlmaelApi
import cz.upce.vetalmael.data.model.Animal
import cz.upce.vetalmael.data.model.Message
import cz.upce.vetalmael.data.model.Report
import cz.upce.vetalmael.data.model.dto.AddAnimalRequest
import cz.upce.vetalmael.data.model.dto.SendMessageRequest
import cz.upce.vetalmael.data.source.application.ApplicationRepository
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.lang.IllegalStateException

class AnimalRepositoryImpl(
    private val api: VetAlmaelApi
) : AnimalRepository, KoinComponent {

    // Use setter injection to prevent weird dependency circles.
    private val applicationRepository: ApplicationRepository by inject()

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

    override suspend fun addAnimal(name: String) {
        val userId = applicationRepository.getCurrentUser()?.idUser!!
        val body = AddAnimalRequest(name)
        val animal = api.addAnimal(userId, body)

        val newCache = animalsCache.toMutableList()
        newCache.add(animal)
        animalsCache = newCache.toList()
    }

    override suspend fun deleteAnimal(id: Int) {
        if (!api.deleteAnimal(id).isSuccessful) {
            throw IllegalStateException("API Error!")
        }

        val newCache = animalsCache.toMutableList()
        newCache.removeAll { it.idAnimal == id }
        animalsCache = newCache.toList()
    }

    override suspend fun getMessages(animalId: Int): List<Message> {
        return api.getAnimalMessages(animalId)
    }

    override suspend fun sendMessage(animalId: Int, message: String): Message {
        val body = SendMessageRequest(message)
        return api.sendMessage(animalId, body)
    }

    override suspend fun getReports(animalId: Int): List<Report> {
        return api.getAnimalReports(animalId)
    }

    override fun erase() {
        animalsCache = emptyList()
    }
}