package app.elevuslabs.mvvm.rest

import app.elevuslabs.mvvm.models.Live
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://s3.amazonaws.com/api.ocanha.com/"

interface RetrofitService {

    @GET("lista-lives.json")
    fun getAllLives(): Call<List<Live>>

    companion object {

        private val retrofitService: RetrofitService by lazy {

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            retrofit.create(RetrofitService::class.java)
        }

        fun getInstance(): RetrofitService {
            return retrofitService
        }
    }

}