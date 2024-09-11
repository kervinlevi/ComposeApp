package dev.kervinlevi.myapplicationjourney.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class JobEntry(
    @SerializedName("date")
    val date: String = "",

    @SerializedName("companyName")
    val companyName: String = "",

    @SerializedName("country")
    val country: String = "",

    @SerializedName("rowColor")
    val color: String = "",

    @SerializedName("status")
    val description: String = "",

    @SerializedName("comment")
    val extraInfo: String? = null,
): Parcelable
