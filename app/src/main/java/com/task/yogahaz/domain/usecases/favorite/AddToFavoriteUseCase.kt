package com.task.yogahaz.domain.usecases.favorite


import com.task.yogahaz.domain.models.home.AddToFavoriteBody
import com.task.yogahaz.domain.models.home.AddToFavoriteResponse
import com.task.yogahaz.domain.repository.home.HomeRepository
import com.task.yogahaz.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class AddToFavoriteUseCase @Inject constructor(

    private val repository: HomeRepository
) {

    operator fun invoke(body: AddToFavoriteBody): Flow<Result<AddToFavoriteResponse>> = flow {
        try {
            emit(Result.Loading())
            val response = repository.addToFavorite(body)
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