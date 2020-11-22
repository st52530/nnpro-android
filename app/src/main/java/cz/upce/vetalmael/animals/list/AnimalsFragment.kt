package cz.upce.vetalmael.animals.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import cz.upce.vetalmael.R
import cz.upce.vetalmael.core.view.recyclerview.DiffUtilAdapter
import cz.upce.vetalmael.core.view.recyclerview.IdentifiableDiffUtilAdapter
import cz.upce.vetalmael.data.source.animal.AnimalRepository
import cz.upce.vetalmael.extensions.setVisibleOrGone
import kotlinx.android.synthetic.main.fragment_animals.*
import kotlinx.android.synthetic.main.include_empty_state.*
import timber.log.Timber

class AnimalsFragment(
    private val animalsRepository: AnimalRepository
) : Fragment(R.layout.fragment_animals) {

    private val adapter: DiffUtilAdapter<AnimalViewData> by lazy {
        IdentifiableDiffUtilAdapter(
            animalDelegate(::onMessagesClicked, ::onReportsClicked, ::onDeleteClicked)
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.adapter = adapter

        emptyStateTextView.setText(R.string.empty_animal_list)

        contentLoadinglayout.setTryAgainListener {
            loadAnimals(true)
        }

        addButton.setOnClickListener {
            findNavController().navigate(AnimalsFragmentDirections.actionAnimalsToAddAnimal())
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

    private fun onReportsClicked(animal: AnimalViewData) {
        findNavController().navigate(AnimalsFragmentDirections.actionAnimalsToAnimalCard(animal.id.toInt()))
    }

    private fun onMessagesClicked(animal: AnimalViewData) {
        findNavController().navigate(AnimalsFragmentDirections.actionAnimalsToMessages(animal.id.toInt()))
    }

    private fun onDeleteClicked(animal: AnimalViewData) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Smazat zvíře")
            .setMessage("Opravdu chcete smazat toto zvíře?\nJméno: ${animal.name}")
            .setPositiveButton("Smazat") { _, _ ->
                lifecycleScope.launchWhenCreated {
                    try {
                        animalsRepository.deleteAnimal(animal.id.toInt())
                        adapter.items = adapter.items.toMutableList().apply {
                            remove(animal)
                            emptyStateLayout.setVisibleOrGone(isEmpty())
                        }
                    } catch (exception: Exception) {
                        Timber.e(exception)
                    }
                }
            }
            .setNegativeButton("Ne") { _, _ ->
                // Do nothing.
            }
            .show()
    }
}