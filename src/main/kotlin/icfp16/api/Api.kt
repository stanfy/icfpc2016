package icfp16.api

import com.google.gson.annotations.SerializedName
import icfp16.*
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Call
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.lang.reflect.Type
import java.util.*

val API_KEY = "17-04d569ebce709bf6e5482fea22c8acf0"

interface Api {
  @GET("hello")
  fun helloWorld(): Call<Hello>

  @GET("snapshot/list")
  fun listSnapchots(): Call<Snapshots>

  @GET("blob/{hash}")
  fun blob(@Path("hash") hash: String): Call<String>
}

data class Hello(
    val ok: Boolean,
    val greeting: String
)

data class Snapshot(
    @SerializedName("snapshot_time")
    val time: String,
    @SerializedName("snapshot_hash")
    val hash: String
)

data class Snapshots(
    val ok: Boolean,
    val snapshots: List<Snapshot>
)

fun parseProblem(str: String): Problem {
  val s = Scanner(str)

  val polygons = LinkedList<Polygon>()

  val polygonsCount = s.nextInt()
  for (i in 0..polygonsCount - 1) {
    val vertices = LinkedList<Vertex>()
    val polygonSize = s.nextInt()
    s.nextLine()
    for (j in 0..polygonSize - 1) {
      val s1 = s.nextLine()
      vertices.add(parseVertex(s1))
    }
    polygons.add(Polygon(vertices))
  }

  val edges = LinkedList<Edge>()

  val edgesCount = s.nextInt()
  s.nextLine()
  for (i in 0..edgesCount - 1) {
    edges.add(Edge(parseVertex(s.next()), parseVertex(s.next())))
  }

  return Problem(polygons, edges)
}

internal fun parseVertex(s: String): Vertex {
  val (a, b) = s.split(",")
  return Vertex(parseFraction(a), parseFraction(b))
}

internal fun parseFraction(s: String): Fraction {
  if (s.contains("/")) {
    val (a, b) = s.split("/")
    return Fraction(Integer.parseInt(a), Integer.parseInt(b))
  } else {
    return Fraction(Integer.parseInt(s))
  }
}

fun createApi(logLevel: Level = Level.BASIC): Api {
  val logInterceptor = HttpLoggingInterceptor()
  logInterceptor.level = logLevel

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
      .addConverterFactory(object: Converter.Factory() {
        override fun responseBodyConverter(type: Type?, annotations: Array<out Annotation>?, retrofit: Retrofit?): Converter<ResponseBody, *>? {
          return Converter<okhttp3.ResponseBody, Problem> { value ->
            parseProblem(value!!.string())
          }
        }
      })
      .addConverterFactory(GsonConverterFactory.create())
      .build()

  val api = retrofit.create(Api::class.java)
  return api
}