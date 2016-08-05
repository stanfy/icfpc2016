package icfp16

import icfp16.api.API_KEY
import icfp16.api.Api
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun main(args: Array<String>) {
  println("ICFP 2016")

  val logInterceptor = HttpLoggingInterceptor()
  logInterceptor.level = HttpLoggingInterceptor.Level.BODY;

  val okHttpClient = OkHttpClient.Builder()
      .addInterceptor(logInterceptor)
      .addInterceptor({ chain ->
        val request = chain.request().newBuilder()
            .addHeader("X-API-Key", API_KEY)
            .build()

        chain.proceed(request)
      })
      .build()

  val retrofit = Retrofit.Builder()
      .baseUrl("http://2016sv.icfpcontest.org/api/")
      .client(
          okHttpClient
      )
      .addConverterFactory(GsonConverterFactory.create())
      .build()

  val api = retrofit.create(Api::class.java)

  println(api.helloWorld().execute().body())
}
