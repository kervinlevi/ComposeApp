package dev.kervinlevi.myapplicationjourney.presentation.form

data class UpdateFormScreenUIState(
    val date: String = "",
    val companyName: String = "",
    val country: String = "",
    val status: JobStatus = JobStatus.APPLIED,
    val statusDescription: String = "",
    val extraInfo: String = "",
    val validationState: UpdateFormValidationState = UpdateFormValidationState(),
    val isSubmitting: Boolean = false,
    val showDatePicker: Boolean = false,
)

data class UpdateFormValidationState(
    val companyNameError: String? = null,
    val countryError: String? = null,
    val statusDescriptionError: String? = null
)

enum class JobStatus(val color: String, val statusText: String) {
    APPLIED("#e7e6e6", "Applied"),
    INTERVIEWED("#ffe598", "Interviewed"),
    PASSED("#b6d7a8", "Passed"),
    FAILED("#fbe4d5", "Failed")
}

sealed interface UpdateFieldAction {
    data class UpdateCompanyName(val companyName: String): UpdateFieldAction
    data class UpdateCountry(val country: String): UpdateFieldAction
    data class UpdateStatus(val status: JobStatus): UpdateFieldAction
    data class UpdateStatusDescription(val statusDescription: String): UpdateFieldAction
    data class UpdateExtraInfo(val extraInfo: String): UpdateFieldAction
    data class UpdateDate(val millis: Long): UpdateFieldAction
    data class ShowDatePicker(val show: Boolean): UpdateFieldAction
}
