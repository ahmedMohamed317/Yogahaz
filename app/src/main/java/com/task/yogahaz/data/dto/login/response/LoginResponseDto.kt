import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.task.yogahaz.domain.models.auth.Address
import com.task.yogahaz.domain.models.auth.LoginResponse
import com.task.yogahaz.domain.models.auth.UserData
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginResponseDto(
    @SerializedName("success") val success: Boolean?,
    @SerializedName("response_code") val responseCode: Int?,
    @SerializedName("message") val message: String?,
    @SerializedName("data") val data: UserDataDto?
) : Parcelable

@Parcelize
data class UserDataDto(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("phone") val phone: String?,
    @SerializedName("image") val image: String?,
    @SerializedName("type") val type: Int?,
    @SerializedName("status") val status: Int?,
    @SerializedName("balance") val balance: String?,
    @SerializedName("addresses") val addresses: List<AddressDto>?,
    @SerializedName("token") val token: String?
) : Parcelable

@Parcelize
data class AddressDto(
    @SerializedName("id") val id: Int?,
    @SerializedName("lat") val lat: String?,
    @SerializedName("lng") val lng: String?,
    @SerializedName("address") val address: String?,
    @SerializedName("street") val street: String?,
    @SerializedName("building") val building: String?,
    @SerializedName("apartment") val apartment: String?,
    @SerializedName("floor") val floor: String?,
    @SerializedName("name") val name: String?
) : Parcelable

fun LoginResponseDto.toLoginResponse(): LoginResponse {
    return LoginResponse(
        success = success ,
        responseCode = responseCode,
        message = message,
        data = data?.toUserData()
    )
}
fun UserDataDto.toUserData(): UserData {
    return UserData(
        id= id,
        name =  name,
        email = email,
        phone = phone,
        image = image,
        token = token,
        addresses = addresses?.map { it.toAddress() }
    )
}
fun AddressDto.toAddress(): Address {
    return Address(
        id = id,
        address = address,
        street = street,
        building = building,
        apartment = apartment,
        floor = floor,
        name = name,
    )
}