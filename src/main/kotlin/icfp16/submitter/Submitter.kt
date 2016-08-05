package icfp16.submitter

import icfp16.api.ProblemSubmission
import icfp16.api.SolutionSpec
import icfp16.api.SolutionSubmission
import icfp16.api.createApi
import icfp16.io.FileUtils
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class Submitter {
  val dateFormat = "yyyy-MM-dd HH:mm:ss ZZZ"
  val api = createApi(HttpLoggingInterceptor.Level.BODY)

  fun submitSolution(problemId: String, solutionString: String):
    SolutionSubmission? {

    val result: SolutionSubmission? = api.submitSolution(problemId = problemId,
      problemSolution = SolutionSpec(solutionString)).execute().body()
    return result
  }

  // like "2016-08-06 00:00:00 UTC"
  fun submitProblemOnExactTime(rawSolution: String, publishTimeString: String = "2016-08-06 00:00:00 UTC"): ProblemSubmission? {
    val spec = SolutionSpec(rawSolution)
    val submitTime = SimpleDateFormat(dateFormat, Locale.US).parse(publishTimeString).time / 1000

    val result: ProblemSubmission? = api.submitProblem(problemSolution = spec,
      publishTime = submitTime).execute().body()

    if (result != null) {
      saveCustomProblemToFile(rawSolution, result)
    }
    return result
  }

  fun saveCustomProblemToFile(rawSolution: String, problem: ProblemSubmission) {
    val filePath = FileUtils().getFullPathForSolutionFile(problem.problem_id)

    println("...saving custom problem to file $filePath")
    File(filePath).bufferedWriter().use { out ->
      out.write("-------------------------- raw solution --------------------\n")
      out.write(rawSolution)
      out.write("\n-------------------------- problem spec --------------------\n")
      out.write(problem.toString())
    }
  }
}
