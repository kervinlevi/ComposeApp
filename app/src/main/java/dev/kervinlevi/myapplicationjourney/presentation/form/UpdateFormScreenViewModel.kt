package dev.kervinlevi.myapplicationjourney.presentation.form

import android.icu.util.Calendar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.kervinlevi.myapplicationjourney.data.JobEntry
import dev.kervinlevi.myapplicationjourney.data.JobRepository
import dev.kervinlevi.myapplicationjourney.presentation.form.UpdateFieldAction.ShowDatePicker
import dev.kervinlevi.myapplicationjourney.presentation.form.UpdateFieldAction.UpdateCompanyName
import dev.kervinlevi.myapplicationjourney.presentation.form.UpdateFieldAction.UpdateCountry
import dev.kervinlevi.myapplicationjourney.presentation.form.UpdateFieldAction.UpdateDate
import dev.kervinlevi.myapplicationjourney.presentation.form.UpdateFieldAction.UpdateExtraInfo
import dev.kervinlevi.myapplicationjourney.presentation.form.UpdateFieldAction.UpdateStatus
import dev.kervinlevi.myapplicationjourney.presentation.form.UpdateFieldAction.UpdateStatusDescription
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class UpdateFormScreenViewModel(private val repository: JobRepository) : ViewModel() {
    private val _uiState: MutableStateFlow<UpdateFormScreenUIState> =
        MutableStateFlow(UpdateFormScreenUIState())
    val uiState: StateFlow<UpdateFormScreenUIState> get() = _uiState

    private val dateFormatter by lazy { SimpleDateFormat("MMMM dd", Locale.getDefault()) }

    init {
        val date = Date(Calendar.getInstance().timeInMillis)
        _uiState.value = _uiState.value.copy(date = dateFormatter.format(date))
    }

    fun saveJobEntry() = viewModelScope.launch {
        _uiState.value = _uiState.value.copy(isSubmitting = true)
        if (areInputsValid()) {
            _uiState.value.run {
                val jobEntry = JobEntry(
                    date, companyName, country, status.color, statusDescription, extraInfo
                )
                val result = repository.saveJobEntry(jobEntry)
                _uiState.value = _uiState.value.copy(isSubmitting = false)
            }
        }
    }

    private fun areInputsValid(): Boolean {
        return true
    }

    fun onUpdateField(action: UpdateFieldAction) {
        when (action) {
            is UpdateCompanyName -> {
                _uiState.value = _uiState.value.copy(companyName = action.companyName)
            }

            is UpdateCountry -> {
                _uiState.value = _uiState.value.copy(country = action.country)
            }

            is UpdateStatus -> {
                _uiState.value = _uiState.value.copy(status = action.status)
            }

            is UpdateStatusDescription -> {
                _uiState.value = _uiState.value.copy(statusDescription = action.statusDescription)
            }

            is UpdateExtraInfo -> {
                _uiState.value = _uiState.value.copy(extraInfo = action.extraInfo)
            }

            is ShowDatePicker -> {
                _uiState.value = _uiState.value.copy(showDatePicker = action.show)
            }

            is UpdateDate -> {
                _uiState.value =
                    _uiState.value.copy(date = dateFormatter.format(Date(action.millis)))
            }
        }
    }
}
