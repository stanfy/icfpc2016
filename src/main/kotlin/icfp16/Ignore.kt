package icfp16

import icfp16.io.FileUtils
import okio.Okio
import java.io.File

fun main(args: Array<String>) {
  val src = Okio.buffer(Okio.source(File("submitted_problems_logs/ids")))
  src.readUtf8().split("\n").forEach {
    if (it.length > 0) {
      val ignore = File(FileUtils().getDefaultProblemFileFolder() + "/problem_$it.txt.ignore")
      println(ignore)
      if (!ignore.exists()) {
        ignore.createNewFile()
      }
    }
  }
  src.close()
}