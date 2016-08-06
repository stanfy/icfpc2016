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
        val p = Runtime.getRuntime().exec("./get-new-problems.sh")
        println("${Instant.now()} ${p.waitFor()}")
        Thread.sleep(TimeUnit.MINUTES.toMillis(15))
      }
    }, "grab automator")
    t.start()
    t.join()
    return
  }

  if (args.size > 0 && "automate-doit".equals(args[0])) {
    val t = Thread({
      while (true) {
        val p = Runtime.getRuntime().exec("./get-and-solve-new-problems.sh")
        println("${Instant.now()} ${p.waitFor()}")
        Thread.sleep(TimeUnit.MINUTES.toMillis(45))
      }
    }, "grab and solve automator")
    t.start()
    t.join()
    return
  }


  println("Starting the farm")
  var useFullList = true
  var solveUnsolved = true
  var threadsNum = 1

  // parse args
  for (arg in args) {
    if (arg.equals("doit")) {
      useFullList = true
      continue
    }
    if (arg.equals("solveUnsolved")) {
      solveUnsolved = true
      continue
    }

    try {
      threadsNum = Integer.parseInt(arg)
    } catch (e: Exception) {
      threadsNum = 1
    }
  }

  if (threadsNum == 1) {
    Farm().startSearchingBestSolutions(useFullList, solveUnsolved)
  } else {
    val allFiles = File(FileUtils().getDefaultProblemFileFolder()).list()
    val batchSize = allFiles.size / threadsNum
    var index = 0
    val threads = ArrayList<Thread>()
    while (index < allFiles.size) {
      val problems = allFiles.toList().subList(index, Math.min(index + batchSize, allFiles.size))
      threads.add(Thread({
        println("Start thread for $problems")
        Farm().startSearchingBestSolutions(false, solveUnsolved, problems)
      }))
      index += batchSize
    }
    threads.forEach { it.start() }
    threads.forEach { it.join() }
  }
}

