package ko.alex.androidmonetisationtutorial.model

import android.os.Parcel
import android.os.Parcelable
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
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(countryName)
        parcel.writeString(capital)
        parcel.writeString(flag)
        parcel.writeString(population)
        parcel.writeString(area)
        parcel.writeString(region)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Country> {
        override fun createFromParcel(parcel: Parcel): Country {
            return Country(parcel)
        }

        override fun newArray(size: Int): Array<Country?> {
            return arrayOfNulls(size)
        }
    }

}