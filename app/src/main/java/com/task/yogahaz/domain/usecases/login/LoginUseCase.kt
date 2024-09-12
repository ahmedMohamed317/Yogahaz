package com.task.yogahaz.domain.usecases.login

import com.task.yogahaz.data.dto.login.request.LoginBody
import com.task.yogahaz.domain.models.auth.LoginResponse
import com.task.yogahaz.domain.repository.login.LoginRepository
import com.task.yogahaz.utils.network.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import toLoginResponse
import java.io.IOException
import javax.inject.Inject

class LoginUseCase @Inject constructor(

    private val repository: LoginRepository
) {

    operator fun invoke(body: LoginBody): Flow<Result<LoginResponse>> = flow {
        try {
            emit(Result.Loading())
            val response = repository.login(body).toLoginResponse()
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