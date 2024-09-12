import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.task.yogahaz.domain.models.auth.RegisterResponse
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegisterResponseDto(
    @SerializedName("success") val success: Boolean?,
    @SerializedName("response_code") val responseCode: Int?,
    @SerializedName("message") val message: String?,
    @SerializedName("data") val data: UserDataDto?
) : Parcelable
fun RegisterResponseDto.toRegisterResponse(): RegisterResponse {
    return RegisterResponse(
        success = success ,
        responseCode = responseCode,
        message = message,
        data = data?.toUserData()
    )
}
