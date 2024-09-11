package dev.kervinlevi.myapplicationjourney.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EmptyResponse(
    val success: Boolean
): Parcelable