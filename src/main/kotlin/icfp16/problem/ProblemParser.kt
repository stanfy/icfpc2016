package icfp16.problem

import icfp16.api.parseVertex
import icfp16.data.*
import icfp16.state.State
import java.util.*

class ProblemParser {

  fun parseProblem(str: String): Problem {
    val s = Scanner(str)

    val polygons = LinkedList<Polygon>()

    val polygonsCount = s.nextInt()
    for (i in 0..polygonsCount - 1) {
      val vertices = LinkedList<Vertex>()
      val polygonSize = s.nextInt()
      s.nextLine()
      for (j in 0..polygonSize - 1) {
        val s1 = s.nextLine()
        vertices.add(parseVertex(s1))
      }
      polygons.add(Polygon(vertices))
    }

    val edges = LinkedList<Edge>()

    val edgesCount = s.nextInt()
    s.nextLine()
    for (i in 0..edgesCount - 1) {
      edges.add(Edge(parseVertex(s.next()), parseVertex(s.next())))
    }

    return Problem(polygons, edges, str)
  }

  fun parseState(str: String): State {
    val s = Scanner(str)

    val verticesCount = s.nextInt()
    val startVertices = Array(verticesCount) { Vertex(0, 0) }
    s.nextLine()
    for (i in 0..verticesCount - 1) {
      val s1 = s.nextLine()
      startVertices[i] = parseVertex(s1)
    }

    val facetsCount = s.nextInt()
    val facets = Array(facetsCount) { Facet(emptyList()) }
    for (i in 0..facetsCount - 1) {
      val ints = LinkedList<Int>()
      val a = s.nextInt()
      for (j in 0..a - 1) {
        ints.add(s.nextInt())
      }
      facets[i] = Facet(ints)
    }

    val finishVertices = Array(verticesCount) { Vertex(0, 0) }
    s.nextLine()
    for (i in 0..verticesCount - 1) {
      val s1 = s.nextLine()
      finishVertices[i] = parseVertex(s1)
    }

    return State(startVertices, facets, finishVertices)
  }

}
