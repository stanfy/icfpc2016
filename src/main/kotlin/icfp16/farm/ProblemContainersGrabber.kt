package icfp16.farm

import icfp16.data.Problem
import icfp16.data.ProblemContainer
import icfp16.api.Snapshot
import icfp16.api.createApi
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.util.*

class ProblemContainersGrabber {

    fun grabProblemsAndSaveToFiles(): List<ProblemContainer> {
        return this.grabProblemsAndSaveToFiles(ParsedProblemsFileUtils().getDefaultProblemFileFolder())
    }

    fun grabProblemsAndSaveToFiles(fileFolder: String): List<ProblemContainer> {
        val api = createApi(HttpLoggingInterceptor.Level.NONE)
        val snapshots: List<Snapshot> = api.listSnapshots().execute().body().snapshots
        println(snapshots)

        val latest = snapshots.reduce { a: Snapshot, b: Snapshot -> if (a.time > b.time) a else b }
        println("latest snapshot " + latest)

        val hash = latest.snapshot_hash
        println("hash " + hash)

        Thread.sleep(1000)

        val problemSpecs = api.getContestStatus(hash = hash).execute().body().problems
        println("problemSpecs \n"+ problemSpecs)

        val result = ArrayList<ProblemContainer>()

        problemSpecs.map { p ->
            Thread.sleep(1000)
            val problem = api.getProblemSpec(hash = p.problem_spec_hash).execute().body()

            val problemContainer = ProblemContainer(problem, p.problem_id, p.problem_spec_hash)
            result.add(problemContainer)

            println("problem spec" + p)
            println("full problem " + problem)

            val filePath = ParsedProblemsFileUtils().getFullPathForProblemId(p.problem_id)
            File(filePath).bufferedWriter().use { out ->
                out.write(problem.rawString)
            }
        }

        println("result " + result)
        return result
    }
}