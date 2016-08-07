package icfp16.solver

import icfp16.data.*
import icfp16.estimate.BitmapEstimator
import icfp16.state.IState
import icfp16.state.PublicStates
import icfp16.state.State
import icfp16.state.solution

interface Solver {
  fun solve(problem: Problem, problemId: String = "", thresholdResemblance: Double = 0.0): IState?

  fun solvedSolutionNamesCache(): SolvedSolutionNamesCacher {
    return SolvedSolutionNamesCacher()
  }
}

class StupidSolver: Solver {
  override fun solve(problem: Problem, problemId: String, thresholdResemblance: Double): IState? {
    return State.initialSquare()
  }
}

class TranslatorSolver: Solver {
  override fun solve(problem: Problem, problemId: String, thresholdResemblance: Double): IState? {
    // simple centroid  as  sum of all polygon coords
    val vertexes = problem.poligons.flatMap { it.vertices }
    val centroid = centroid(vertexes)

    val translation = centroid.add(Vertex(Fraction(-1, 2), Fraction(-1, 2)))
    val initialSquare = State.initialSquare()
    val translatedSolve = initialSquare.translate(translation)
    return translatedSolve
  }
}


class BetterTranslatorSolver : Solver {
  override fun solve(problem: Problem, problemId: String, thresholdResemblance: Double): IState? {
    // simple centroid  as  sum of all polygon coords
    val vertexes = problem.poligons.flatMap { it.vertices }
    val centroid = massCentroid(vertexes)

    val stateCentroid = Vertex(Fraction(-1, 2), Fraction(-1, 2))
    val translation = centroid.add(stateCentroid)
    val initialSquare = State.initialSquare()
    val translatedSolve = initialSquare.translate(translation)
    return translatedSolve
  }
}


class SequenceSolver: Solver {

  override fun solve(problem: Problem, problemId: String, thresholdResemblance: Double): IState? {

    val vertexes = problem.poligons.flatMap { it.vertices }
    val problemCentroid = centroid(vertexes)
    val cache = this.solvedSolutionNamesCache()
    val cachedNames = cache.readCachedSolutionNamesFor(problemId = problemId)

    var bestState: IState? = null
    var bestResemblance: Double = thresholdResemblance

    var names = mutableListOf<String>()
    val allStates = PublicStates.states
    for (state in allStates) {
//      println("Working on state ${allStates.indexOf(state)} of ${allStates.size}")

      val bestPossibleResemblance = BitmapEstimator().bestPossibleResemblanceOf(problem, state, quality = 2)
      if (bestResemblance != 0.0 && bestResemblance > bestPossibleResemblance) {
//        println(" ... Skipping bestPossibleResemblance $bestPossibleResemblance < $bestResemblance current Resemblance")
        continue
      }

      // sub S many times
      val stateCentroid = centroid(state.finalPositions().asList())
      val translation = problemCentroid.sub(stateCentroid)
      val translatedState = state.translate(translation)

      for (s in listOf(translatedState, state)) {

        // take N  check if we're food enough, take more
        val translatedCentroid = centroid(s.finalPositions().asList())
        for (rotated in rotationsList(s, translatedCentroid)) {

          // Make sure we're saving state
          names.add(rotated.name)

          // filters
          if (cachedNames.contains(rotated.name)) continue
          if (rotated.solution().length > 5000) continue

          val resemblance = BitmapEstimator().resemblanceOf(problem, rotated, quality = 2)
          if (resemblance > bestResemblance) {
//            println("Updating resebmblance $bestResemblance  >  $resemblance")

            bestResemblance = resemblance
            bestState = rotated
          }
        }
      }
    }
    if (!names.isEmpty()) {
      cache.cacheSolutionNameToFile(problemId, names)
    }
    return bestState
  }

  private fun rotationsList(s: IState, stateCentroid: Vertex): List<IState> {
     // can we fix it more?
    return listOf<IState>(
        s,
        s.rotate90(stateCentroid),
        s.rotate180(stateCentroid),
        s.rotate270(stateCentroid),
        s.rotate(stateCentroid, Triple(3, 4, 5)),
        s.rotate(stateCentroid, Triple(4, 3, 5)),
        s.rotate(stateCentroid, Triple(5, 12, 13)),
        s.rotate(stateCentroid, Triple(12, 5, 13)),
        s.rotate(stateCentroid, Triple(8, 15, 17)),
        s.rotate(stateCentroid, Triple(15, 8, 17)),
        s.rotate(stateCentroid, Triple(7, 24, 25)),
        s.rotate(stateCentroid, Triple(24, 7, 25)),
        s.rotate(stateCentroid, Triple(20, 21, 29)),
        s.rotate(stateCentroid, Triple(21, 20, 29)),
        s.rotate(stateCentroid, Triple(28, 45, 53)),
        s.rotate(stateCentroid, Triple(45, 28, 53)),
        s.rotate(stateCentroid, Triple(11, 60, 61)),
        s.rotate(stateCentroid, Triple(60, 11, 61)),
        s.rotate(stateCentroid, Triple(16, 63, 65)),
        s.rotate(stateCentroid, Triple(63, 16, 65)),
        s.rotate(stateCentroid, Triple(33, 56, 65)),
        s.rotate(stateCentroid, Triple(56, 33, 65)),
        s.rotate(stateCentroid, Triple(48, 55, 73)),
        s.rotate(stateCentroid, Triple(55, 48, 73)),
        s.rotate(stateCentroid, Triple(13, 84, 85)),
        s.rotate(stateCentroid, Triple(84, 13, 85)),
        s.rotate(stateCentroid, Triple(36, 77, 85)),
        s.rotate(stateCentroid, Triple(77, 36, 85)),
        s.rotate(stateCentroid, Triple(39, 80, 89)),
        s.rotate(stateCentroid, Triple(80, 39, 89)),
        s.rotate(stateCentroid, Triple(65, 72, 97)),
        s.rotate(stateCentroid, Triple(72, 65, 97))
    )
  }


}

class BestSolverEver: Solver {

  override fun solve(problem: Problem, problemId: String, thresholdResemblance: Double): IState? {

    //val solvers = arrayOf<Solver>(StupidSolver(), TranslatorSolver(), BetterTranslatorSolver(), SequenceSolver())
    val solvers = arrayOf<Solver>(/*Wrapper(),*/ SequenceSolver())

    var bestResemblance = thresholdResemblance
    val states = mutableListOf<Pair<IState?, Double>>()

    for (solver in solvers) {

      var solution: IState? = null
      try {
        val startTime = System.currentTimeMillis()
        solution = solver.solve(problem, problemId, bestResemblance)
        println("Solution by $solver in ${System.currentTimeMillis() - startTime}")
      } catch (e:Exception) {
        println("I was trying to kill myself")
      }

      if (solution != null) {
        val solvedRes = BitmapEstimator().resemblanceOf(problem, solution, quality = 2)
        if (solvedRes > bestResemblance)  {
          bestResemblance = solvedRes
          states.add(Pair(solution, bestResemblance))
        }
      }
    }

    if (states.isEmpty()) {
      return null
    }

    val sorted = states.sortedBy { it.second }
    return sorted.last().first
  }
}
