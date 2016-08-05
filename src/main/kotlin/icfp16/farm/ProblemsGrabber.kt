package icfp16.farm

import icfp16.Problem
import icfp16.api.Snapshot
import icfp16.api.createApi
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.util.*

class ProblemsGrabber {

    fun grabProblemsAndSaveToFiles(): List<Problem> {
        return this.grabProblemsAndSaveToFiles("parsed_problems")
    }

    fun grabProblemsAndSaveToFiles(fileFolder: String): List<Problem> {
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

        val result = ArrayList<Problem>()

        problemSpecs.map { p ->
            Thread.sleep(1000)
            val problem = api.getProblemSpec(hash = p.problem_spec_hash).execute().body()
            problem.problemHash = p.problem_spec_hash
            problem.problemId = p.problem_id

            result.add(problem)

            println("problem spec" + p)
            println("full problem " + problem)

            val fileName = "problem_" + p.problem_id + ".txt"
            val filePath = fileFolder + "/" + fileName
            File(filePath).bufferedWriter().use { out ->
                out.write(problem.rawString)
            }
        }

        println("result " + result)
        return result
    }
}