package com.task.yogahaz.domain.usecases.home


import com.task.yogahaz.data.dto.home.response.toPopularSellersResponse
import com.task.yogahaz.domain.models.home.PopularSellersResponse
import com.task.yogahaz.domain.repository.home.HomeRepository
import com.task.yogahaz.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetPopularUseCase @Inject constructor(
    private val repository: HomeRepository
) {

    operator fun invoke(): Flow<Result<PopularSellersResponse>> = flow {
        try {
            emit(Result.Loading())
            val response = repository.getPopularSellers().toPopularSellersResponse()
            if (response.success == true)
                emit(Result.Success(response))
            else{
                emit(Result.Error(response.message ?: "An unexpected error occurred"))
            }
        } catch(e: HttpException) {
            emit(Result.Error(e.localizedMessage ?: "An unexpected error occurred"))
        } catch(e: IOException) {
            emit(Result.Error("Couldn't reach server. Check your internet connection."))
        }catch(e: Exception) {
            emit(Result.Error("An unexpected error occurred: ${e.localizedMessage}"))
        }

    }
}