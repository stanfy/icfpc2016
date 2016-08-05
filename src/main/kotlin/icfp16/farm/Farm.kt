package icfp16.farm

import icfp16.data.SolutionContainer
import icfp16.estimate.EstimatorFactory
import icfp16.io.FileUtils
import icfp16.io.ProblemContainersParser
import icfp16.solver.BestSolverEver
import icfp16.submitter.Submitter
import icfp16.visualizer.Visualizer
import java.io.File


class Farm {
  val submitter = Submitter()
  val estimatorQuality = 4
  val shouldShowInvalidPics = false

  val startingId = 1
  val count = 100

  fun startSearchingBestSolutions() {

    for (problemId in startingId..(startingId + count)) {
      //Thread.sleep(1000) // <--- api

      println("---------------------------------")
      println("start searching best solution for problem: $problemId")
      val solutionContainer = solveAndSubmitSolutionFor(problemId.toString())

      // save to files
      if (solutionContainer != null && (shouldShowInvalidPics || solutionContainer.realResemblance != -1.0)) {
        saveSolutionContainerToFile(solutionContainer)
        saveSolutionImageToFile(solutionContainer)
      }

    }
  }


  fun solveAndSubmitSolutionFor(problemId: String): SolutionContainer? {
    val problemContainer = ProblemContainersParser().generateProblemContainerForProblemId(problemId) ?: return null

    val solver = BestSolverEver()
    val state = solver.solve(problem = problemContainer.problem)
    val solution = state.solution()
    println("--------- submitted solution ----\n$solution")

    val resemblance = submitSolution(problemId, solution)
    println("--------- real resemblance $resemblance for id: $problemId")

    val estimator = EstimatorFactory().bestEstimatorEver()
    val estimatedResemblance = estimator.resemblanceOf(task = problemContainer.problem,
      state = state, quality = estimatorQuality)
    println("--------- estimatedResemblance $estimatedResemblance for id: $problemId")

    return SolutionContainer(problemContainer, state, resemblance, estimatedResemblance)
  }


  fun submitSolution(problemId: String, solution: String): Double {
    val submittedResponse = submitter.submitSolution(problemId = problemId, solutionString = solution) ?: return -1.0
    return submittedResponse.resemblance
  }

  fun saveSolutionContainerToFile(container: SolutionContainer) {
    val filePath = FileUtils().getFullPathForSolutionFile(container.problemContainer.problemId)

    println("...saving text solution container to file $filePath")
    File(filePath).bufferedWriter().use { out ->
      out.write("-------------------------- resemblance --------------------\n")
      out.write("real res =" + container.realResemblance.toString() + " estimated res =" + container.estimatedResemblance.toString())
      out.write("-------------------------- container --------------------\n")
      out.write(container.toString())
      out.write("\n-------------------------- solution --------------------\n")
      out.write(container.state.solution().toString())
    }

    // json saving & reading
//    val g = Gson()
//    val jsonString = g.toJson(container)
//    println("output = \n" + jsonString)
//
//    val outputObj = g.fromJson(jsonString, SolutionContainer::class.java)
//    assert(outputObj.equals(container))
  }

  fun saveSolutionImageToFile(container: SolutionContainer) {
    val filePath = FileUtils().getFullPathForSolutionImage(container.problemContainer.problemId)

    println("...generating image: $filePath")
    Visualizer().visualizedAndSaveImage(container.problemContainer.problem,
      container.state, 1, filePath)
  }
}