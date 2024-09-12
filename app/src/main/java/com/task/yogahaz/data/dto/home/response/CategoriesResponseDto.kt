import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.task.yogahaz.domain.models.auth.LoginResponse
import com.task.yogahaz.domain.models.home.CategoriesResponse
import com.task.yogahaz.domain.models.home.CategoryData
import kotlinx.parcelize.Parcelize

data class CategoriesResponseDto(
    @SerializedName("success")
    val success: Boolean?,

    @SerializedName("response_code")
    val responseCode: Int?,

    @SerializedName("message")
    val message: String?,

    @SerializedName("data")
    val data: List<CategoryDataDto>?
)

@Parcelize
data class CategoryDataDto(
    @SerializedName("id")
    val id: Int?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("image")
    val image: String?,

    @SerializedName("active")
    val active: Int?,

    @SerializedName("name_ar")
    val nameAr: String?,

    @SerializedName("name_en")
    val nameEn: String?
) : Parcelable
fun CategoriesResponseDto.toCategoriesResponse(): CategoriesResponse {
    return CategoriesResponse(
        success = success ,
        responseCode = responseCode,
        message = message,
        data = data?.map { it.toCategoryData() }
    )
}

fun CategoryDataDto.toCategoryData(): CategoryData {
    return CategoryData(
        name = name,
        image = image,
        nameEn = nameEn
    )
}