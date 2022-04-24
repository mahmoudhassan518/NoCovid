package com.ksa.unticovid.base

import com.google.gson.Gson
import com.ksa.unticovid.core.exception.RemoteException
import com.ksa.unticovid.modules.core.data.model.BaseResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

import okhttp3.ResponseBody
import retrofit2.HttpException
import timber.log.Timber
import java.lang.Exception


abstract class BaseRepository(
    private val ioDispatcher: CoroutineDispatcher
) {

    fun <T> requestHandler(fetch: suspend () -> T) = flow {
        try {
            emit(fetch.invoke())
        } catch (throwable: Throwable) {
            Timber.e("error happen in requestHandler $throwable")
            if (throwable is HttpException) {
                throw throwable.getHttpException()

            } else
                throw throwable
        }
    }.flowOn(ioDispatcher)

    private fun Throwable.getHttpException(): Throwable {
        val body: ResponseBody? = (this as HttpException).response()!!.errorBody()
        return try {
            val errorsResponse =
                Gson().fromJson(body!!.string(), BaseResponse::class.java)
            RemoteException(errorsResponse.message)
        } catch (e: Exception) {
            this
        }
    }


}
