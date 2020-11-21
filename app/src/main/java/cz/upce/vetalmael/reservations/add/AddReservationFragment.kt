package cz.upce.vetalmael.reservations.add

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import cz.upce.vetalmael.R
import cz.upce.vetalmael.data.model.Clinic
import cz.upce.vetalmael.data.source.clinic.ClinicRepository
import cz.upce.vetalmael.data.source.reservation.ReservationRepository
import cz.upce.vetalmael.extensions.setVisibleOrInvisible
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.fragment_add_reservation.*
import kotlinx.android.synthetic.main.fragment_add_reservation.contentLoadinglayout
import kotlinx.android.synthetic.main.fragment_add_reservation.progressBar
import kotlinx.android.synthetic.main.fragment_add_reservation.saveButton
import kotlinx.android.synthetic.main.fragment_add_reservation.toolbar
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class AddReservationFragment(
    private val reservationRepository: ReservationRepository,
    private val clinicRepository: ClinicRepository
) : Fragment(R.layout.fragment_add_reservation) {

    private var clinics: List<Clinic> = emptyList()

    private var selectedClinic: Clinic? = null

    private val dateFormat = SimpleDateFormat("dd. MM. yyyy HH:mm")

    private val selectedCalendar: Calendar = Calendar.getInstance().apply {
        add(Calendar.MINUTE, 10)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUiListeners()

        loadData()
    }

    private fun loadData(force: Boolean = true) {
        lifecycleScope.launchWhenCreated {
            contentLoadinglayout.showLoading()
            try {
                val clinics = clinicRepository.getClinics(force)
                this@AddReservationFragment.clinics = clinics

                val items = clinics.map { it.name }
                val context = context ?: return@launchWhenCreated
                val adapter =
                    ArrayAdapter<String>(context, android.R.layout.simple_expandable_list_item_1)
                adapter.addAll(items)
                clinicAutoCompleteTextView.setAdapter(adapter)

                dateEditText.setText(dateFormat.format(selectedCalendar.time))
                validate()

                contentLoadinglayout.showData()
            } catch (exception: Exception) {
                contentLoadinglayout.showError()
            }
        }
    }

    private fun setupUiListeners() {
        toolbar.setNavigationOnClickListener { findNavController().navigateUp() }

        contentLoadinglayout.setTryAgainListener {
            loadData(true)
        }

        clinicAutoCompleteTextView.setOnItemClickListener { _, _, position, _ ->
            selectedClinic = clinics.getOrNull(position)
            validate()
        }

        selectDateButton.setOnClickListener {
            val builder = MaterialDatePicker.Builder.datePicker()
            builder.setSelection(selectedCalendar.timeInMillis)
            val now = Date()

            val bounds = CalendarConstraints.Builder()
                .setStart(now.time)
                .setOpenAt(now.time)
                .setValidator(DateSelectionValidator())
                .build()
            builder.setCalendarConstraints(bounds)
            val picker = builder.build()

            picker.addOnPositiveButtonClickListener { picked ->
                val calendar = Calendar.getInstance()
                calendar.timeInMillis = picked

                selectedCalendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR))
                selectedCalendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH))
                selectedCalendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH))
                dateEditText.setText(dateFormat.format(selectedCalendar.time))
                validate()
            }
            picker.show(childFragmentManager, "date-picker")
        }

        selectTimeButton.setOnClickListener {
            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build()

            timePicker.addOnPositiveButtonClickListener {
                val newHour: Int = timePicker.hour
                val newMinute: Int = timePicker.minute

                selectedCalendar.set(Calendar.HOUR_OF_DAY, newHour)
                selectedCalendar.set(Calendar.MINUTE, newMinute)

                dateEditText.setText(dateFormat.format(selectedCalendar.time))
                validate()
            }

            timePicker.show(childFragmentManager, "time-picker")
        }

        saveButton.setOnClickListener {
            saveClicked()
        }
    }

    private fun validate() {
        var isValid = true
        if (selectedClinic == null) {
            isValid = false
        }

        if (selectedCalendar.before(Calendar.getInstance())) {
            isValid = false
        }

        saveButton.isEnabled = isValid
    }

    private fun saveClicked() {
        validate()
        if (!saveButton.isEnabled) {
            return
        }
        clinicInputLayout.error = null
        dateTextLayout.error = null

        lifecycleScope.launchWhenCreated {
            try {
                val clinic = selectedClinic?.idClinic ?: return@launchWhenCreated
                showLoading(true)
                reservationRepository.addReservation(
                    clinicId = clinic,
                    date = selectedCalendar.time
                )

                findNavController().navigateUp()
            } catch (error: Throwable) {
                Timber.e(error)
                clinicInputLayout.error = getString(R.string.general_error)
                dateTextLayout.error = getString(R.string.space)
            } finally {
                showLoading(false)
            }
        }
    }

    private fun showLoading(show: Boolean) {
        saveButton.setVisibleOrInvisible(!show)
        progressBar.setVisibleOrInvisible(show)
    }

    @Parcelize
    class DateSelectionValidator : CalendarConstraints.DateValidator {

        @IgnoredOnParcel
        private val now: Long

        @IgnoredOnParcel
        private val comparator: Calendar

        init {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            now = calendar.timeInMillis
            comparator = Calendar.getInstance()
        }

        override fun isValid(date: Long): Boolean {
            comparator.timeInMillis = date
            comparator.set(Calendar.HOUR_OF_DAY, 0)
            comparator.set(Calendar.MINUTE, 0)
            comparator.set(Calendar.SECOND, 0)
            comparator.set(Calendar.MILLISECOND, 0)
            return now <= comparator.timeInMillis
        }
    }
}