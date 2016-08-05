package icfp16.api

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

val API_KEY = "17-04d569ebce709bf6e5482fea22c8acf0"

interface Api {
  @GET("hello")
  fun helloWorld(): Call<Hello>

  @GET("snapshot/list")
  fun listSnapchots(): Call<Snapshots>

  // TODO add custom converter for this request
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