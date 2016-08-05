package icfp16.farm

import icfp16.data.SolutionContainer
import icfp16.estimate.EstimatorFactory
import icfp16.solver.StupidSolver
import icfp16.submitter.Submitter


class Farm {
  val submitter = Submitter()
  val estimatorQuality = 4

  fun startSearchingBestSolutions(problemId: String) {
    println("start searching best solution for problem: $problemId")
    val solutionContainer = submitSolutionFor(problemId)
    println("solutionContainer \n$solutionContainer")
  }

  fun submitSolutionFor(problemId: String): SolutionContainer? {
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

    return SolutionContainer(problemContainer, solution, resemblance, estimatedResemblance)
  }


  fun submitSolution(problemId: String, solution: String): Double {
    val submittedResponse = submitter.submitSolution(problemId = "1", solutionString = solution) ?: return 0.0
    return submittedResponse.resemblance
  }
}