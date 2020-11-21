package cz.upce.vetalmael.data.source.clinic

import cz.upce.vetalmael.base.repository.ErasableRepository
import cz.upce.vetalmael.data.model.Clinic
import cz.upce.vetalmael.data.model.Reservation
import java.util.*

interface ClinicRepository : ErasableRepository {

    suspend fun getClinics(force: Boolean): List<Clinic>
}