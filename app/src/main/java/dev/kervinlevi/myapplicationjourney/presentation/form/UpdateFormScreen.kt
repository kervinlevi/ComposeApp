package dev.kervinlevi.myapplicationjourney.presentation.form

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.kervinlevi.myapplicationjourney.presentation.form.UpdateFieldAction.ShowDatePicker
import dev.kervinlevi.myapplicationjourney.presentation.form.UpdateFieldAction.UpdateCompanyName
import dev.kervinlevi.myapplicationjourney.presentation.form.UpdateFieldAction.UpdateCountry
import dev.kervinlevi.myapplicationjourney.presentation.form.UpdateFieldAction.UpdateDate
import dev.kervinlevi.myapplicationjourney.presentation.form.UpdateFieldAction.UpdateExtraInfo
import dev.kervinlevi.myapplicationjourney.presentation.form.UpdateFieldAction.UpdateStatus
import dev.kervinlevi.myapplicationjourney.presentation.form.UpdateFieldAction.UpdateStatusDescription
import org.koin.androidx.compose.koinViewModel


@Composable
fun UpdateFormScreen(modifier: Modifier = Modifier, viewModel: UpdateFormScreenViewModel = koinViewModel()) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        val screenPadding = PaddingValues(
            top = innerPadding.calculateTopPadding() + 24.dp,
            bottom = innerPadding.calculateBottomPadding()
        )
        UpdateFormContent(
            uiState = uiState.value,
            onAction = { updateAction ->
                viewModel.onUpdateField(updateAction)
            },
            onSubmitClicked = {
                viewModel.saveJobEntry()
            },
            modifier = modifier.padding(screenPadding)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun UpdateFormContent(
    uiState: UpdateFormScreenUIState,
    onAction: (UpdateFieldAction) -> Unit,
    onSubmitClicked: () -> Unit,
    modifier: Modifier
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .imePadding()
            .verticalScroll(rememberScrollState())
    ) {
        val startGuide = createGuidelineFromStart(16.dp)
        val endGuide = createGuidelineFromEnd(16.dp)

        val (dateRef, companyNameRef, countryRef, statusLabelRef, statusFlowRef) = createRefs()
        val (statusDescriptionRef, extraInfoRef, logButtonRef) = createRefs()
        OutlinedTextField(value = uiState.date,
            onValueChange = {},
            label = { Text("Date") },
            enabled = false,
            modifier = Modifier
                .clickable(true, onClick = {
                    onAction(ShowDatePicker(true))
                })
                .constrainAs(dateRef) {
                    top.linkTo(parent.top, margin = 20.dp)
                    width = Dimension.percent(0.5f)
                    linkTo(startGuide, endGuide, bias = 0f)
                },
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                disabledLabelColor = MaterialTheme.colorScheme.onSurface,
                disabledBorderColor = MaterialTheme.colorScheme.outline
            )
        )

        OutlinedTextField(
            value = uiState.companyName,
            onValueChange = { onAction(UpdateCompanyName(it)) },
            label = { Text("Company Name") },
            modifier = Modifier.constrainAs(companyNameRef) {
                top.linkTo(dateRef.bottom, margin = 20.dp)
                width = Dimension.fillToConstraints
                linkTo(startGuide, endGuide)
            },
        )

        OutlinedTextField(
            value = uiState.country,
            onValueChange = { onAction(UpdateCountry(it)) },
            label = { Text("Country") },
            modifier = Modifier.constrainAs(countryRef) {
                top.linkTo(companyNameRef.bottom, margin = 20.dp)
                width = Dimension.fillToConstraints
                linkTo(startGuide, endGuide)
            },
        )

        Text(text = "Status", modifier = Modifier.constrainAs(statusLabelRef) {
            top.linkTo(countryRef.bottom, margin = 32.dp)
            width = Dimension.fillToConstraints
            linkTo(startGuide, endGuide)
        })

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.constrainAs(statusFlowRef) {
                top.linkTo(statusLabelRef.bottom, margin = 4.dp)
                width = Dimension.fillToConstraints
                linkTo(startGuide, endGuide)
            },
        ) {
            for (option in JobStatus.entries) {
                FilterChip(
                    selected = uiState.status == option,
                    onClick = { onAction(UpdateStatus(option)) },
                    label = { Text(text = option.statusText) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedLabelColor = Color.Black,
                        selectedContainerColor = option.color.parseHexColor(),
                    ),
                    border = FilterChipDefaults.filterChipBorder(
                        borderColor = option.color.parseHexColor(),
                        borderWidth = 2.dp
                    ),
                    shape = CircleShape
                )
            }
        }

        OutlinedTextField(
            value = uiState.statusDescription,
            onValueChange = { onAction(UpdateStatusDescription(it)) },
            label = { Text("Description") },
            modifier = Modifier.constrainAs(statusDescriptionRef) {
                top.linkTo(statusFlowRef.bottom)
                width = Dimension.fillToConstraints
                linkTo(startGuide, endGuide)
            },
        )

        OutlinedTextField(
            value = uiState.extraInfo,
            onValueChange = { onAction(UpdateExtraInfo(it)) },
            label = { Text("Extra information") },
            modifier = Modifier.constrainAs(extraInfoRef) {
                top.linkTo(statusDescriptionRef.bottom, margin = 20.dp)
                width = Dimension.fillToConstraints
                linkTo(startGuide, endGuide)
            },
        )

        Button(onClick = { onSubmitClicked() },
            enabled = !uiState.isSubmitting,
            shape = RectangleShape,
            modifier = Modifier.constrainAs(logButtonRef) {
                width = Dimension.fillToConstraints
                linkTo(parent.start, parent.end)
                linkTo(extraInfoRef.bottom, parent.bottom, topMargin = 20.dp, bias = 1f)
            }) {
            Text(text = "Log entry")
        }

        if (uiState.showDatePicker) {
            DatePicketModalContent({
                onAction(UpdateDate(it ?: System.currentTimeMillis()))
            }, {
                onAction(ShowDatePicker(false))
            })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePicketModalContent(onDateSelected: (Long?) -> Unit, onDismiss: () -> Unit) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis(),
        initialDisplayMode = DisplayMode.Picker
    )
    DatePickerDialog(onDismissRequest = onDismiss, confirmButton = {
        TextButton(onClick = {
            onDateSelected(datePickerState.selectedDateMillis)
            onDismiss()
        }) {
            Text(text = "Select")
        }
    }) {
        DatePicker(state = datePickerState)
    }
}

fun String.parseHexColor(): Color {
    return Color(android.graphics.Color.parseColor(this))
}
