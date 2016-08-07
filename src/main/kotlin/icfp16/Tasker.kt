package icfp16

import icfp16.api.SolutionSpec
import icfp16.api.createApi
import icfp16.data.*
import icfp16.state.*
import icfp16.submitter.Submitter
import icfp16.visualizer.Visualizer
import okhttp3.logging.HttpLoggingInterceptor
import okio.Okio
import java.io.File
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*
import java.util.concurrent.TimeUnit

val tasks = arrayOf(
    Pair(
        Problem(emptyList(), emptyList()),
        ComplexState()
            .fold(Edge(
                Vertex(Fraction(1,2), Fraction(1, 1)),
                Vertex(Fraction(1,2), Fraction(0, 1))
            ))
            .fold(Edge(
                Vertex(Fraction(1,4), Fraction(1, 1)),
                Vertex(Fraction(1,4), Fraction(0, 1))
            ))
            .fold(Edge(
                Vertex(Fraction(1,8), Fraction(1, 1)),
                Vertex(Fraction(1,8), Fraction(0, 1))
            ))

            .fold(Edge(
                Vertex(Fraction(1,8), Fraction(2, 8)),
                Vertex(Fraction(0,8), Fraction(1, 8))
            ))

            .fold(Edge(
                Vertex(Fraction(0,8), Fraction(4, 8)),
                Vertex(Fraction(1,8), Fraction(3, 8))
            ))
            .fold(Edge(
                Vertex(Fraction(-1,4), Fraction(3, 8)),
                Vertex(Fraction(-1,8), Fraction(1, 2))
            ))
            .appendName("Okolobubl")
    ),
    Pair(
        Problem( emptyList(), emptyList()),
        ComplexState()
            .fold(Edge(
                Vertex(Fraction(0), Fraction(1, 3)),
                Vertex(Fraction(1), Fraction(2, 3))
            ))
            .fold(Edge(
                Vertex(Fraction(0), Fraction(1, 4)),
                Vertex(Fraction(1), Fraction(2, 4))
            ))
            .fold(Edge(
                Vertex(Fraction(0), Fraction(1, 5)),
                Vertex(Fraction(1), Fraction(7, 10))
            ))
            .fold(Edge(
                Vertex(Fraction(1,4), Fraction(0)),
                Vertex(Fraction(1,5), Fraction(1))
            ))
    ),
    Pair(
        Problem( emptyList(), emptyList()),
        ComplexState()
            .fold(Edge(
                Vertex(Fraction(0), Fraction(14, 3)),
                Vertex(Fraction(1), Fraction(2, 3))
            ))
            .fold(Edge(
                Vertex(Fraction(0), Fraction(1, 4)),
                Vertex(Fraction(5,100), Fraction(2, 4))
            ))
            .fold(Edge(
                Vertex(Fraction(0), Fraction(1, 5)),
                Vertex(Fraction(12,79), Fraction(3, 5))
            ))
            .fold(Edge(
                Vertex(Fraction(5,17), Fraction(99,100)),
                Vertex(Fraction(1,5), Fraction(17,33))
            ))
            .fold(Edge(
                Vertex(Fraction(3,4), Fraction(1)),
                Vertex(Fraction(5,5), Fraction(0))
            ))
    ),
    Pair(
        Problem( emptyList(), emptyList()),
        ComplexState()
            .fold(Edge(
                Vertex(Fraction(8,9), Fraction(1)),
                Vertex(Fraction(5, 7), Fraction(0))
            ))
            .fold(Edge(
                Vertex(Fraction(1,2), Fraction(0)),
                Vertex(Fraction(29,56), Fraction(1))
            ))
            .fold(Edge(
                Vertex(Fraction(137,1140), Fraction(1)),
                Vertex(Fraction(111,115), Fraction(0))
            ))
            .fold(Edge(
                Vertex(Fraction(21,47), Fraction(1)),
                Vertex(Fraction(22,47), Fraction(0))
            ))
            .fold(Edge(
                Vertex(Fraction(23,47), Fraction(1)),
                Vertex(Fraction(24,47), Fraction(0))
            ))
    )


)

var submit = false

fun main(args: Array<String>) {


  if (args.size > 0) {
    submit = true
  }

  val dir = File("our_problems")
  dir.mkdirs()
  val START_TIME = Instant.parse("2016-08-07T05:00:00Z")
  var ts = Instant.from(START_TIME)
  val api = createApi(HttpLoggingInterceptor.Level.NONE)

  val log = StringBuilder()
  val idDump = StringBuilder()
  val logDir = File("submitted_problems_logs")
  logDir.mkdirs()

  var i = 0

  while (ts.isBefore(Instant.parse("2016-08-07T22:00:00Z"))) {
    val data = tasks[i % tasks.size]
    if (!submit && i >= tasks.size) {
      break
    }

    val problem = data.first
    val random = Random()
    val pivot = Vertex(random.nextInt(), Math.abs(random.nextInt() + 1))
    val pythagoreanTriple = PYTHAGOREAN_TRIPLES[random.nextInt(PYTHAGOREAN_TRIPLES.size)]
    val translation = Vertex(
        Fraction(random.nextLong(), Math.abs(random.nextLong())),
        Fraction(random.nextLong(), Math.abs(random.nextLong()))
    )
    val solution = data.second
        .rotate(pivot, pythagoreanTriple)
        //.translate(translation) Gets too big size.

    val visualizer = Visualizer()
    visualizer.visualizedAndSaveImage(problem, solution, 1, "our_problems/task$i.png")
    visualizer.visualizedAndSaveFolds(solution, 1, "our_problems/task${i}folds.png")
    val text = solution.solution()
    File(dir, "task$i.txt").writeText(text)
    println("Have task $i for $ts. Size: ${text.replace("\\s+".toRegex(), "").length}")

    var badSolution = false

    if (submit) {
      val request = api.submitProblem(SolutionSpec(text), TimeUnit.MILLISECONDS.toSeconds(ts.toEpochMilli())).execute()
      val logStr = "Problem submission #$i $ts is successful: ${request.isSuccessful.toString().toUpperCase()} " +
          "id: ${request.body()?.problem_id}"
      println(logStr)
      if (request.body()?.problem_id != null) {
        idDump.append(request.body()?.problem_id).append("\n")
      }

      log.append(logStr)
      log.append("\n")

      if (!request.isSuccessful) {
        val errMsg = "Error: ${request.raw().code()} ${request.message()} ${request.body()}"
        println(errMsg)
        if (request.code() != 403 && request.code() != 400) {
          println("Retry")
          Thread.sleep(TimeUnit.SECONDS.toMillis(2))
          continue
        } else if (request.code() == 400) {
          badSolution = true
        }
      }

//      log.append("Pivot: $pivot\n")
//      log.append("Pythagorean triple: $pythagoreanTriple\n")
//      log.append("Translation: $translation\n\n")

      Thread.sleep(TimeUnit.SECONDS.toMillis(2))
    }

    if (!badSolution) {
      ts = ts.plus(1, ChronoUnit.HOURS)
    }
    i++
  }

  val logFile = File(logDir, "task${Instant.now()}.txt")
  logFile.writeText(log.toString())
  val idFile = File(logDir, "ids")
  idFile.appendText(idDump.toString())
}
