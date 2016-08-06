package icfp16

import icfp16.data.Fraction
import icfp16.data.Polygon
import icfp16.data.Problem
import icfp16.data.Vertex
import icfp16.farm.Farm
import icfp16.io.FileUtils
import icfp16.io.ProblemContainersGrabber
import java.io.File
import java.math.BigInteger
import java.time.Instant
import java.util.*
import java.util.concurrent.TimeUnit

var problem = Problem(
    arrayListOf(Polygon(
        arrayListOf(
            Vertex(Fraction(BigInteger("0")), Fraction(BigInteger("0"))),
            Vertex(Fraction(BigInteger("1")), Fraction(BigInteger("0"))),
            Vertex(Fraction(BigInteger("1"), BigInteger("2")), Fraction(BigInteger("1"), BigInteger("2"))),
            Vertex(Fraction(BigInteger("0")), Fraction(BigInteger("1"), BigInteger("2")))
        )
    )),
    arrayListOf(),
        ""
)

fun main(args: Array<String>) {
  println("ICFP 2016")

  if (args.size > 0 && "grab".equals(args[0])) {
    println("Grabbing data")
    ProblemContainersGrabber().grabProblemsAndSaveToFiles()
    return
  }

  // I did not succeed in setting up a periodic task using Macos means (Automator+iCalendar, launcherd, cron).
  if (args.size > 0 && "automate".equals(args[0])) {
    val t = Thread({
      while (true) {
        val shouldSolve = args.size > 1 && args[1] == "doit"
        val script = if (shouldSolve) "./get-and-solve-new-problems.sh" else "./get-new-problems.sh"

        val p = Runtime.getRuntime().exec(script)
        println("${Instant.now()} ${p.waitFor()}")

        val delay = if (shouldSolve) 40 else 15
        Thread.sleep(TimeUnit.MINUTES.toMillis(delay))
      }
    }, "grab automator")
    t.start()
    t.join()
    return
  }

  println("Starting the farm")
  if (args.size == 0) {
    Farm().startSearchingBestSolutions()
  } else if ("doit".equals(args[0])) {
    Farm().startSearchingBestSolutions(true)
  } else {
    val n = Integer.parseInt(args[0])
    val allFiles = File(FileUtils().getDefaultProblemFileFolder()).list()
    val batchSize = allFiles.size / n
    var index = 0
    val threads = ArrayList<Thread>()
    while (index < allFiles.size) {
      val problems = allFiles.toList().subList(index, Math.min(index + batchSize, allFiles.size))
      threads.add(Thread({
        println("Start thread for $problems")
        Farm().startSearchingBestSolutions(false, problems)
      }))
      index += batchSize
    }
    threads.forEach { it.start() }
    threads.forEach { it.join() }
  }
}

