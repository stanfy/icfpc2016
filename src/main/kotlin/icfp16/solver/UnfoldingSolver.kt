package icfp16.solver

import kotlin.collections.*
import icfp16.data.Edge
import icfp16.data.Polygon
import icfp16.data.Problem
import icfp16.data.Vertex
import icfp16.state.IState
import icfp16.state.State

class UnfoldingSolver : Solver {
  override fun solve(problem: Problem, problemId: String, thresholdResemblance: Double): IState? {
    val initialSquare = State.initialSquare()

    if (problem.skeleton.count() != 7) {
      return null
    }

    return initialSquare
  }

  fun polygonsFromProblem(problem: Problem): List<Polygon> {
    if (problem.skeleton.count() < 3) {
      return emptyList()
    }

    val initialEdge = problem.skeleton.first()
    val polygons = visitEdge(initialEdge, arrayOf(initialEdge), problem.skeleton)

    val uniquePolygons: MutableSet<Polygon> = mutableSetOf()
    return polygons.filter { uniquePolygons.add(it) }
  }

  fun findAdjoinedEdges(edge: Edge, visitedEdges: Array<Edge>, potentialEdges: List<Edge>): List<Edge> {
    return potentialEdges.filter { it != edge && (it.hasPoint(edge.a) || it.hasPoint(edge.b)) && !visitedEdges.reversed().contains(it) }
  }

  fun visitEdge(edge: Edge, visitedEdges: Array<Edge>, availableEdges: List<Edge>): List<Polygon> {
    val adjoinedEdges = findAdjoinedEdges(edge, visitedEdges, availableEdges)

    return when (adjoinedEdges.count()) {
      0 -> {
        if (visitedEdges.count() > 3) {
          // TODO: normalize edges in counterclockwise direction
          val uniqueVertices: MutableSet<Vertex> = mutableSetOf()
          val polygonVertices: MutableList<Vertex> = mutableListOf()

          visitedEdges.forEach {
            if (uniqueVertices.add(it.a)) {
              polygonVertices.add(it.a)
            }

            if (uniqueVertices.add(it.b)) {
              polygonVertices.add(it.b)
            }
          }

          listOf(Polygon(polygonVertices))
        } else {
          emptyList()
        }
      }

      1 -> {
        val nextEdge = adjoinedEdges.first()
        visitEdge(nextEdge, visitedEdges.plus(nextEdge), availableEdges)
      }

      else -> {
        val output: MutableList<Polygon> = mutableListOf()

        for (nextEdge in adjoinedEdges) {
          val polygons = visitEdge(nextEdge, visitedEdges.plus(nextEdge), availableEdges)
          output.plus(polygons)
        }

        output
      }
    }
  }

}