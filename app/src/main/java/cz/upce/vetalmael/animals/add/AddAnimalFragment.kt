package cz.upce.vetalmael.animals.add

import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import cz.upce.vetalmael.R
import cz.upce.vetalmael.data.source.animal.AnimalRepository
import cz.upce.vetalmael.extensions.setVisibleOrInvisible
import kotlinx.android.synthetic.main.fragment_add_animal.*
import kotlinx.android.synthetic.main.fragment_add_animal.progressBar
import timber.log.Timber

class AddAnimalFragment(
    private val animalRepository: AnimalRepository
) : Fragment(R.layout.fragment_add_animal) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUiListeners()
    }

    private fun setupUiListeners() {
        toolbar.setNavigationOnClickListener { findNavController().navigateUp() }

        nameEditText.doOnTextChanged { text, _, _, _ ->
            saveButton.isEnabled = text?.isNotEmpty() == true
        }

        saveButton.setOnClickListener {
            saveClicked()
        }
    }

    private fun saveClicked() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            try {
                showLoading(true)
                animalRepository.addAnimal(
                    nameEditText.text.toString()
                )

                findNavController().navigateUp()
            } catch (error: Throwable) {
                Timber.e(error)
                nameTextLayout.error = getString(R.string.general_error)
            } finally {
                showLoading(false)
            }
        }
    }

    private fun showLoading(show: Boolean) {
        saveButton.setVisibleOrInvisible(!show)
        progressBar.setVisibleOrInvisible(show)
    }
}