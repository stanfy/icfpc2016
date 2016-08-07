package icfp16.solver

import icfp16.data.Edge
import icfp16.data.Polygon
import icfp16.data.Problem
import icfp16.data.Vertex
import icfp16.folder.Line
import icfp16.state.ComplexState
import icfp16.state.IState
import icfp16.state.solution
import icfp16.visualizer.Visualizer
import java.util.*

fun wrappingEdges(envelop: Polygon, target: Polygon): List<Edge> {
  return target.edges()
      // Check if the edge is inside.
      .filter { envelop.convexIn(it.a) && envelop.convexIn(it.b) }
      // Remove edges that match our boundaries.
      .filter { e -> !envelop.edges().any { it.hasPoint(e.a) && it.hasPoint(e.b) } }
      // Sort by length.
      .sortedByDescending { it.comparativeValue() }
}

class Wrapper(private val debug: Boolean = false): Solver {

  private fun retState(problem: Problem, state: IState, depth: Int): IState {
    if (!debug) {
      return state
    }
    Visualizer().visualizedAndSaveImage(problem, state, 1, "wrapper-$depth.png")
    return state
  }

  fun solveWithWrapping(problem: Problem, startState: IState, depth: Int): IState {
    val we = startState.poligons().flatMap { wrappingEdges(it, problem.poligons[0]) }
    if (debug) {
      println("$depth ------------------------")
      println("Candidates: $we")
    }
    if (we.isEmpty()) {
      // Nothing to do! We should be done.
      return retState(problem, startState, depth)
    }

    val state = startState as ComplexState

    // fold currently requires edge points to cross poly edges...
    val foldingLine = Line(we.first())
    val vertexes = state.poligons()
        .flatMap {
          it.edges()
              .map { it.to(Line(it).interection(foldingLine)) }
              .filter { it.second != null && it.first.hasPoint(it.second!!) }
              .map { it.second!! }
        }
        .sortedWith(boundaryComparator)
    if (vertexes.size < 2) {
      throw IllegalArgumentException("TODO: Looks like we cannot make it... Approximate?")
    }
    val foldingEdge = Edge(vertexes.first(), vertexes.last())

    if (debug) {
      println("folding edge: $foldingEdge")
    }
    // Now we have a correct edge to do the fold. Do it.
    val newState = startState.fold(foldingEdge) as ComplexState
    if (newState.polys.size > state.polys.size) {
      // Fold successful. Let's continue our adventure.
      if (debug) {
        println("continue")
        println(newState.solution())
      }
      return retState(problem, solveWithWrapping(problem, newState, depth + 1), depth)
    } else {
      // We failed, return last valid state.
      if (debug) {
        println("failure")
      }
      return retState(problem, startState, depth)
    }
  }

  private val boundaryComparator: Comparator<Vertex> = object : Comparator<Vertex> {
    override fun compare(o1: Vertex, o2: Vertex): Int {
      val v = o1.relativeVector(o2)
      if (v.a > 0) {
        return 1
      } else if (v.a < 0) {
        return -1
      } else {
        return if (v.b > 0) {
          1
        } else if (v.b < 0) {
          -1
        } else {
          0
        }
      }
    }
  }

  override fun solve(problem: Problem, problemId: String, thresholdResemblance: Double): IState? {
    // Identify the initial state. If something is on the edge - wrap it.
    // If not, translate first.
    //

    // TODO

    // Wrap.
    return solveWithWrapping(problem, ComplexState(), 0)
  }

}
