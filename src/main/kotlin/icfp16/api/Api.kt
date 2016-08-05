package icfp16.api

import retrofit2.Call
import retrofit2.http.GET

val API_KEY = "17-04d569ebce709bf6e5482fea22c8acf0"

interface Api {
  @GET("hello")
  fun helloWorld(): Call<Hello>
}

data class Hello(
    val ok: Boolean,
    val greeting: String
)
