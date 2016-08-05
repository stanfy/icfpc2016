package icfp16

import icfp16.api.createApi

fun main(args: Array<String>) {
  println("ICFP 2016")

  val api = createApi()

  println(api.blob("f4b1a8567108144bae331340a57c68b85df487e0").execute().body())
}


