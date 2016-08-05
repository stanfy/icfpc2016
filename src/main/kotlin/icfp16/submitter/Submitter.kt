package icfp16.submitter

import icfp16.api.SolutionSpec
import icfp16.api.SolutionSubmission
import icfp16.api.createApi
import okhttp3.logging.HttpLoggingInterceptor


class Submitter {


  fun submitSolution(problemId: String, solutionString: String):
    SolutionSubmission? {

    val api = createApi(HttpLoggingInterceptor.Level.NONE)
    val result: SolutionSubmission? = api.submitSolution(problemId = problemId,
      problemSolution = SolutionSpec(solutionString)).execute().body()
    return result
  }

}
