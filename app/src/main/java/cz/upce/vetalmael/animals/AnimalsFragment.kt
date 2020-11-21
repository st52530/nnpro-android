package cz.upce.vetalmael.animals

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import cz.upce.vetalmael.R
import cz.upce.vetalmael.core.view.recyclerview.DiffUtilAdapter
import cz.upce.vetalmael.core.view.recyclerview.IdentifiableDiffUtilAdapter
import cz.upce.vetalmael.data.source.animal.AnimalRepository
import cz.upce.vetalmael.extensions.setVisibleOrGone
import kotlinx.android.synthetic.main.fragment_animals.*
import kotlinx.android.synthetic.main.include_empty_state.*

class AnimalsFragment(
    private val animalsRepository: AnimalRepository
) : Fragment(R.layout.fragment_animals) {

    private val adapter: DiffUtilAdapter<AnimalViewData> by lazy {
        IdentifiableDiffUtilAdapter(
            animalDelegate(::onAnimalClicked)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.adapter = adapter

        emptyStateTextView.setText(R.string.empty_animal_list)

        contentLoadinglayout.setTryAgainListener {
            loadAnimals(true)
        }

        loadAnimals()
    }

    private fun loadAnimals(force: Boolean = false) {
        lifecycleScope.launchWhenCreated {
            contentLoadinglayout.showLoading()
            try {
                val animals = animalsRepository.getAnimals(force).map {
                    AnimalViewData(
                        it.idAnimal.toString(),
                        it.name
                    )
                }
                adapter.items = animals
                emptyStateLayout.setVisibleOrGone(animals.isEmpty())
                contentLoadinglayout.showData()
            } catch (exception: Exception) {
                contentLoadinglayout.showError()
            }
        }
    }

    private fun onAnimalClicked(animal: AnimalViewData) {
        // TODO: Handle animal click.
    }
}