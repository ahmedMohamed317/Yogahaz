package com.task.yogahaz.domain.usecases.register

import com.task.yogahaz.data.dto.register.request.RegisterBody
import com.task.yogahaz.domain.models.auth.RegisterResponse
import com.task.yogahaz.domain.repository.register.RegisterRepository
import com.task.yogahaz.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import toRegisterResponse
import java.io.IOException
import javax.inject.Inject

class RegisterUseCase @Inject constructor(

    private val repository: RegisterRepository
) {

    operator fun invoke(body: RegisterBody): Flow<Result<RegisterResponse>> = flow {
        try {
            emit(Result.Loading())
            val response = repository.register(body).toRegisterResponse()
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