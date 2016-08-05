package icfp16.farm

import icfp16.data.SolutionContainer
import icfp16.estimate.EstimatorFactory
import icfp16.solver.StupidSolver
import icfp16.submitter.Submitter
import icfp16.visualizer.Visualizer


class Farm {
  val submitter = Submitter()
  val estimatorQuality = 4

  fun startSearchingBestSolutions() {

    for (problemId in 1..101) {
      println("start searching best solution for problem: $problemId")
      val solutionContainer = solveAndSubmitSolutionFor(problemId.toString())
      println("solutionContainer \n$solutionContainer")

      // save image
      if (solutionContainer != null) {
        val filePath = ParsedProblemsFileUtils().getFullPathForSolutionImage(problemId.toString())
        Visualizer().visualizedAndSaveImage(solutionContainer.problemContainer.problem,
          solutionContainer.state, estimatorQuality, filePath)
      }

    }
  }


  fun solveAndSubmitSolutionFor(problemId: String): SolutionContainer? {
    val problemContainer = ProblemContainersParser().generateProblemContainerForProblemId(problemId) ?: return null

    val solver = StupidSolver()
    val state = solver.solve(problem = problemContainer.problem)
    val solution = state.solution()
    println("--------- solution ----\n$solution")

    val resemblance = submitSolution(problemId, solution)
    println("--------- resemblance $resemblance for id: $problemId")

    val estimator = EstimatorFactory().bestEstimatorEver()
    val estimatedResemblance = estimator.resemblanceOf(task = problemContainer.problem,
      state = state, quality = estimatorQuality)

    return SolutionContainer(problemContainer, state, resemblance, estimatedResemblance)
  }


  fun submitSolution(problemId: String, solution: String): Double {
    val submittedResponse = submitter.submitSolution(problemId = problemId, solutionString = solution) ?: return 0.0
    return submittedResponse.resemblance
  }
}