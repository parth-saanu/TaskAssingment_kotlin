package net.simplifiedcoding.mvvmsampleapp.data.network.responses
import android.os.Parcel
import android.os.Parcelable
data class MovieModel(
    var page: Int,
    var message: String,
    var results: MutableList<MovieResponse>?,
    var total_pages: Int,
    var total_results: Int
) {
    data class MovieResponse(
        var id: Int,
        var overview: String?,
        var release_date: String?,
        var title: String?,
        var vote_average: String?,
        var backdrop_path: String?,
        var poster_path: String?
    ) : Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readValue(Int::class.java.classLoader) as Int,
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()
        ) {
        }
        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeValue(id)
            parcel.writeString(overview)
            parcel.writeString(release_date)
            parcel.writeString(title)
            parcel.writeString(vote_average)
            parcel.writeString(backdrop_path)
            parcel.writeString(poster_path)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<MovieResponse> {
            override fun createFromParcel(parcel: Parcel): MovieResponse {
                return MovieResponse(parcel)
            }

            override fun newArray(size: Int): Array<MovieResponse?> {
                return arrayOfNulls(size)
            }
        }

    }
}