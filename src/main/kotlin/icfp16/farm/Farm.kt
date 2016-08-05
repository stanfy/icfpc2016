package icfp16.farm

import icfp16.solver.StupidSolver
import icfp16.submitter.Submitter


class Farm {
  val submitter = Submitter()

  fun startSearchingBestSolutions(problemId: String) {
    println("start searching best solution for problem: $problemId")
    val resemblance = solveSubmitAndGetResemblanceFor(problemId)
  }

  fun solveSubmitAndGetResemblanceFor(problemId: String): Double {
    val problemContainer = ProblemContainersParser().generateProblemContainerForProblemId(problemId) ?: return 0.0

    val solver = StupidSolver()
    val solution = solver.solve(problem = problemContainer.problem).solution()
    println("---------solution ----\n$solution")
    val resemblance = submitSolution(problemId, solution)
    println("---------resemblance $resemblance for id: $problemId")
    return resemblance
  }


  fun submitSolution(problemId: String, solution: String): Double {
    val submittedResponse = submitter.submitSolution(problemId = "1", solutionString = solution) ?: return 0.0
    return submittedResponse.resemblance
  }
}