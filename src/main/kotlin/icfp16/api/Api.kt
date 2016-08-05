package icfp16.api

import com.google.gson.Gson
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
import retrofit2.http.*
import java.lang.reflect.Type
import java.util.*

val API_KEY = "17-04d569ebce709bf6e5482fea22c8acf0"

// Retrofit? WTF?! http://square.github.io/retrofit/
interface Api {
  @GET("hello")
  fun helloWorld(): Call<Hello>

  @GET("snapshot/list")
  fun listSnapshots(): Call<Snapshots>

  @GET("blob/{hash}")
  fun getProblemSpec(@Path("hash") hash: String): Call<Problem>

  @GET("blob/{hash}")
  fun getContestStatus(@Path("hash") hash: String): Call<ContestStatus>

  @FormUrlEncoded
  @POST("problem/submit")
  fun submitProblem(@Field("solution_spec") problemSolution: SolutionSpec, @Field("publish_time") publishTime: Long): Call<ProblemSubmission>

  @FormUrlEncoded
  @POST("solution/submit")
  fun submitSolution(@Field("problem_id") problemId: String, @Field("solution_spec") problemSolution: SolutionSpec): Call<SolutionSubmission>
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

data class SolutionSpec(val s: String) {
  override fun toString(): String {
    return s
  }
}

data class ProblemSubmission(
    val ok: Boolean,
    val problem_id: String,
    val publish_time: Long,
    val solution_spec_hash: String,
    val solution_size: Int,
    val problem_spec_hash: String,
    val problem_size: Int
)

data class SolutionSubmission(
    val ok: Boolean,
    val problem_id: String,
    val resemblance: Double,
    val solution_spec_hash: String,
    val solution_size: Int
)

data class Rank(
    val resemblance: Double,
    val solution_size: Int
)

data class ProblemStatus(
    val ranking: List<Rank>,
    val publish_time: Long,
    val solution_size: Int,
    val problem_id: String,
    val owner: String,
    val problem_size: Int,
    val problem_spec_hash: String
)

data class UserScore(
    val username: String,
    val score: Double
)

data class User(
    val username: String,
    val display_name: String
)

data class ContestStatus(
    val problems: List<ProblemStatus>,
    val snapshot_time: Long,
    val leaderboard: List<UserScore>,
    val users: List<User>
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
      .client(okHttpClient)
      .addConverterFactory(object: Converter.Factory() {
        override fun responseBodyConverter(type: Type?, annotations: Array<out Annotation>?, retrofit: Retrofit?): Converter<ResponseBody, *>? {
          if (type == Problem::class.java) {
            return Converter<okhttp3.ResponseBody, Problem> { value ->
              parseProblem(value!!.string())
            }
          }

          return null
        }
      })
      .addConverterFactory(GsonConverterFactory.create())
      .build()

  val api = retrofit.create(Api::class.java)
  return api
}