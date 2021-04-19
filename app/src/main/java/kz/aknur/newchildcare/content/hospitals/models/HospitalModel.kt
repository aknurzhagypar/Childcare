package kz.aknur.newchildcare.content.hospitals.models

import com.google.gson.annotations.SerializedName
import kz.aknur.newchildcare.signUp.secondPage.models.CityModel

data class HospitalModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("city")
    val city: CityModel,
    @SerializedName("info")
    val info: String,
    @SerializedName("icon")
    val icon: ByteArray?,
    @SerializedName("address")
    val address: String
)