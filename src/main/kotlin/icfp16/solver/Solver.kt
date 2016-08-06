package icfp16.solver

import icfp16.data.*
import icfp16.estimate.BitmapEstimator
import icfp16.estimate.CompoundEstimator
import icfp16.state.IState
import icfp16.state.PublicStates
import icfp16.state.State

interface Solver {
  fun solve(problem: Problem): IState
}

class StupidSolver: Solver {
  override fun solve(problem: Problem): State {
    return State.initialSquare()
  }
}

class TranslatorSolver: Solver {
  override fun solve(problem: Problem): IState {
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
  override fun solve(problem: Problem): IState {
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
  override fun solve(problem: Problem): IState {

    val vertexes = problem.poligons.flatMap { it.vertices }
    val problemCentroid = centroid(vertexes)

    val bestState = PublicStates.states
      .map { s ->
        // sub S many times
        val stateCentroid = centroid(s.finalPositions().asList())
        val translation = problemCentroid.sub(stateCentroid)
        val translatedState = s.translate(translation)
        translatedState
      }
      .flatMap { s ->
        // list of all transformations of state
        val stateCentroid = centroid(s.finalPositions().asList())
        listOf<IState>(
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
      .map { it.to(BitmapEstimator().resemblanceOf(problem, it, quality = 2)) }
      .sortedBy { it.second }
      .last().first

    return bestState
  }
}

class BestSolverEver: Solver {

  override fun solve(problem: Problem): IState {
    val solvers = arrayOf<Solver>(StupidSolver(), TranslatorSolver(), BetterTranslatorSolver(), SequenceSolver(), Wrapper())
    val states =  solvers
        .map { it.solve(problem) }
        .map { it.to(BitmapEstimator().resemblanceOf(problem, it, quality = 2)) }
    val sorted = states.sortedBy { it.second }
    return sorted.last().first
  }
}