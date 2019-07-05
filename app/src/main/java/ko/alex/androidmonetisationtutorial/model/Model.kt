package ko.alex.androidmonetisationtutorial.model

import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName("name") //"name" is from the json file
    val countryName: String?, //? means countryName can be null
    @SerializedName("capital")
    val capital: String?,
    @SerializedName("flagPNG")
    val flag: String?,
    @SerializedName("population")
    val population: String?,
    @SerializedName("area")
    val area: String?,
    @SerializedName("region")
    val region: String?
)