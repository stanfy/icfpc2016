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

  fun filterPolygons(polygons: List<Polygon>): List<Polygon> {
    return when (polygons.count()) {
      0 -> emptyList()
      1 -> polygons
      else -> {
        val output = mutableListOf(polygons.first())
        for (index in 1..polygons.count() - 1) {
          for (lhs in output) {
            val rhs = polygons[index]
            if (!lhs.equalsToPolygon(rhs)) {
              output.add(rhs)
            }
          }
        }

        output
      }
    }
  }

  fun polygonsFromProblem(problem: Problem): List<Polygon> {
    if (problem.skeleton.count() < 3) {
      return emptyList()
    }

    val initialEdge = problem.skeleton.first()
    val polygons = visitVertex(initialEdge.a, emptyArray(), problem.skeleton)
    return filterPolygons(polygons)
  }

  fun findAdjoinedEdges(vertex: Vertex, visitedEdges: Array<Edge>, potentialEdges: List<Edge>): List<Edge> {
    return potentialEdges.filter { (it.hasPoint(vertex)) && !visitedEdges.contains(it) }
  }

  fun polygonFromEdges(edges: Array<Edge>): Polygon {
    val uniqueVertices: MutableSet<Vertex> = mutableSetOf()
    val polygonVertices: MutableList<Vertex> = mutableListOf()

    edges.forEach {
      if (uniqueVertices.add(it.a)) {
        polygonVertices.add(it.a)
      }

      if (uniqueVertices.add(it.b)) {
        polygonVertices.add(it.b)
      }
    }

    return Polygon(polygonVertices)
  }

  fun nearestClosingPolygon(initialVertex: Vertex, visitedEdges: Array<Edge>): Polygon {
    val polygonVertices: MutableList<Vertex> = mutableListOf(initialVertex)
    var previousVertex = initialVertex

    for (edge in visitedEdges.reversed()) {
      val currentVertex = if (edge.a == previousVertex) edge.b else edge.a

      if (currentVertex == initialVertex) {
        return Polygon(polygonVertices)
      }

      polygonVertices.add(currentVertex)
      previousVertex = currentVertex
    }

    // technically it shouldn't be reachable
    return Polygon(polygonVertices)
  }

  fun nextVertexForVertex(vertex: Vertex, edge: Edge): Vertex {
    return if (edge.a == vertex) edge.b else edge.a
  }

  fun visitVertex(vertex: Vertex, visitedEdges: Array<Edge>, availableEdges: List<Edge>): List<Polygon> {
    val adjoinedEdges = findAdjoinedEdges(vertex, visitedEdges, availableEdges)

    return when (adjoinedEdges.count()) {
      0 -> {
        if (visitedEdges.count() > 3) {
          val polygon = nearestClosingPolygon(vertex, visitedEdges)
          listOf(polygon)
        } else {
          emptyList()
        }
      }

      1 -> {
        val nextEdge = adjoinedEdges.first()
        val nextVertex = nextVertexForVertex(vertex, nextEdge)
        visitVertex(nextVertex, visitedEdges.plus(nextEdge), availableEdges)
      }

      else -> {
        val output: MutableList<Polygon> = mutableListOf()

        for (nextEdge in adjoinedEdges) {
          val nextVertex = nextVertexForVertex(vertex, nextEdge)
          val polygons = visitVertex(nextVertex, visitedEdges.plus(nextEdge), availableEdges)

          output.addAll(polygons)
        }

        output
      }
    }
  }

}