package icfp16.io

import icfp16.api.Snapshot
import icfp16.api.createApi
import icfp16.data.ProblemContainer
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.util.*

class ProblemContainersGrabber {

  fun grabProblemsAndSaveToFiles(): List<ProblemContainer> {
    val api = createApi(HttpLoggingInterceptor.Level.NONE)
    val snapshots: List<Snapshot> = api.listSnapshots().execute().body().snapshots
    println("getting list of shapshots...")

    val latest = snapshots.reduce { a: Snapshot, b: Snapshot -> if (a.time > b.time) a else b }
    println("latest snapshot " + latest)

    // api limitations
    Thread.sleep(1000)

    val hash = latest.snapshot_hash
    val problemSpecs = api.getContestStatus(hash = hash).execute().body().problems
    println("received problem specs count = ${problemSpecs.count()}")

    val result = ArrayList<ProblemContainer>()

    problemSpecs.map { p ->
      // api limitations
      Thread.sleep(1000)
      val problem = api.getProblemSpec(hash = p.problem_spec_hash).execute().body()

      val problemContainer = ProblemContainer(problem, p.problem_id, p.problem_spec_hash)
      result.add(problemContainer)

      val filePath = FileUtils().getFullPathForProblemId(p.problem_id)

      println("...saving problem to file $filePath")
      File(filePath).bufferedWriter().use { out ->
        out.write(problem.rawString)
      }
    }
    return result
  }
}