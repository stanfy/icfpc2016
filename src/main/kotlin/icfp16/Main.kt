package icfp16

import icfp16.data.Fraction
import icfp16.data.Polygon
import icfp16.data.Problem
import icfp16.data.Vertex
import icfp16.farm.*
import icfp16.io.ProblemContainersGrabber
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

  // NEW FARM ==============================================
  if (args.size > 0 && "updateFirebase".equals(args[0])) {
    updateTasksDb()
    return
  }

  if (args.size > 0 && "startNewFarm".equals(args[0])) {
    startSolving()
    return
  }

  if (args.size > 0 && "printNewFarmTasks".equals(args[0])) {
    printCurrentFirebaseTasks()
    return
  }

  if (args.size > 0 && "importFromOldFarm".equals(args[0])) {
    importSolutionsFromLocalToFirebase()
    return
  }

  // to run FB farm with all problems
  if (args.size > 0 && "recalculateAll".equals(args[0])) {
    startSolving(emptyList(), recalculateAll = true)
    return
  }

  if (args.size > 0 && "monitoring".equals(args[0])) {
    newFarmMonitoring()
    return
  }

  //=========================================================

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
  var useFullList = false
  var solveUnsolved = false
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

  println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")
  println("Starting with params: full=$useFullList solveUnsolved=$solveUnsolved threads=$threadsNum")
  println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~")

  if (threadsNum == 1) {
    Farm().startSearchingBestSolutions(useFullList, solveUnsolved)

  } else {
    val problemsToSolve = Farm().listOfProblemFileNamesToSolve(solveUnsolved)
    val batchSize = problemsToSolve.size / threadsNum
    var index = 0
    val threads = ArrayList<Thread>()
    while (index < problemsToSolve.size) {
      val problems = problemsToSolve.toList().subList(index, Math.min(index + batchSize, problemsToSolve.size))
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

