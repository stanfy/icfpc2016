package icfp16.farm

import icfp16.data.SolutionContainer
import icfp16.estimate.EstimatorFactory
import icfp16.solver.BestSolverEver
import icfp16.solver.StupidSolver
import icfp16.submitter.Submitter
import icfp16.visualizer.Visualizer


class Farm {
  val submitter = Submitter()
  val estimatorQuality = 4

  val startingId = 1
  val count = 10

  fun startSearchingBestSolutions() {

    for (problemId in startingId..(startingId + count)) {
      println("---------------------------------")
      println("start searching best solution for problem: $problemId")
      val solutionContainer = solveAndSubmitSolutionFor(problemId.toString())
      println("$solutionContainer")

      // save image
      if (solutionContainer != null) {
        val filePath = ParsedProblemsFileUtils().getFullPathForSolutionImage(problemId.toString())

        println("generating image: $filePath")
        Visualizer().visualizedAndSaveImage(solutionContainer.problemContainer.problem,
          solutionContainer.state, estimatorQuality, filePath)
      }

    }
  }


  fun solveAndSubmitSolutionFor(problemId: String): SolutionContainer? {
    val problemContainer = ProblemContainersParser().generateProblemContainerForProblemId(problemId) ?: return null

    val solver = BestSolverEver()
    val state = solver.solve(problem = problemContainer.problem)
    val solution = state.solution()
    println("--------- submitting solution ----\n$solution")

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
}