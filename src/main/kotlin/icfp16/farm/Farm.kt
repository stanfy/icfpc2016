package icfp16.farm

import icfp16.data.ProblemContainer
import icfp16.estimate.Estimator
import icfp16.solver.StupidSolver


class Farm {

  fun startSearchingBestSolutions(problemId: String) {
    println("start searching best solution for problem: $problemId")
    solveProblemId(problemId)
  }

  fun solveProblemId(problemId: String) {
    val problemContainer = ProblemContainersParser().generateProblemContainerForProblemId(problemId)

    if (problemContainer != null) {
      // TODO: solve and remember result

      val solver = StupidSolver()
      val solution = solver.solve(problem = problemContainer.problem).solution()
      println("---------solution ----\n$solution")

    }
  }
}