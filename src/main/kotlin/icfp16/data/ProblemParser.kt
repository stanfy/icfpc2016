package icfp16.data

import icfp16.api.parseVertex
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
}
