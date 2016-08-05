package icfp16.solver

import icfp16.data.*
import icfp16.estimate.CompoundEstimator
import icfp16.state.PublicStates
import icfp16.state.State

interface Solver {
  fun solve(problem: Problem): State
}

class StupidSolver: Solver {
  override fun solve(problem: Problem): State {
    return State.initialSquare()
  }
}

class TranslatorSolver: Solver {
  override fun solve(problem: Problem): State {
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
  override fun solve(problem: Problem): State {
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
  override fun solve(problem: Problem): State {

    val vertexes = problem.poligons.flatMap { it.vertices }
    val problemCentroid = centroid(vertexes)

    val bestState = PublicStates.states
      .map { s ->
        // translate S many times
        val stateCentroid = centroid(s.finalPositions.asList())
        val translation = problemCentroid.sub(stateCentroid)
        val translatedState = s.translate(translation)
        translatedState
      }
      .flatMap { s ->
        // list of all transformations of state
        val stateCentroid = massCentroid(s.finalPositions.asList())
        listOf<State>(
            s
//            s.rotate(stateCentroid, Math.PI/2 * 15),
//            s.rotate(stateCentroid, Math.PI/2 * 30),
//            s.rotate(stateCentroid, Math.PI/2 * 45),
//            s.rotate(stateCentroid, Math.PI/2 * 60),
//            s.rotate(stateCentroid, Math.PI/2 * 75),
//            s.rotate(stateCentroid, Math.PI/2 * 90)
            )
      }
      .map { it.to(CompoundEstimator().resemblanceOf(problem, it, quality = 4)) }
      .sortedBy { it.second }
      .last().first

    return bestState
  }
}

class BestSolverEver: Solver {

  override fun solve(problem: Problem): State {
    val solvers = arrayOf<Solver>(StupidSolver(), TranslatorSolver(), BetterTranslatorSolver(), SequenceSolver())
    val states =  solvers
        .map { it.solve(problem) }
        .map { it.to(CompoundEstimator().resemblanceOf(problem, it, quality = 4)) }
    val sorted = states.sortedBy { it.second }
    return sorted.last().first
  }
}