package icfp16.solver

import icfp16.data.*
import icfp16.folder.commonEdge
import icfp16.folder.join
import icfp16.state.ComplexState
import icfp16.state.IState
import icfp16.state.State
import icfp16.utils.PolygonComposer
import icfp16.visualizer.Visualizer


data class PossibleJoin (val p1: Polygon, val p2: Polygon, val edge :Edge){

}

class UglySolver: Solver {
  override fun solve(problem: Problem, problemId: String, thresholdResemblance: Double): IState? {
    // simple centroid  as  sum of all polygon coords
    val vertexes = problem.poligons.flatMap { it.vertices }
    val centroid = centroid(vertexes)
    val p = PolygonComposer()
    val polys : MutableList<Polygon> =  p.polygonsFromProblem(problem).toMutableList()

    val polys2 = joinPolymax(polys)
    val polys3 = joinPolymax(polys2)

    // find possible folds
    Visualizer().visualizedAndSaveImage(problem, ComplexState(arrayOf(ComplexPolygon())), 1, "test.png")

    val translation = centroid.add(Vertex(Fraction(-1, 2), Fraction(-1, 2)))
    val initialSquare = State.initialSquare()
    val translatedSolve = initialSquare.translate(translation)
    return translatedSolve
  }

  fun joinPolymax(polys : MutableList<Polygon>) : MutableList<Polygon>{

    if(polys.count() <= 1)
    {
      return polys
    }

    val possibleJoins : MutableList<PossibleJoin> = arrayListOf()


    for(p1 in polys){
      for(p2 in polys)
      {
        if(!p1.equalsToPolygon(p2)){
          val commonEdge = p1.commonEdge(p2)
          if(commonEdge!= null)
          {
            // might have different results so whynot
            possibleJoins.add(PossibleJoin(p1, p2, commonEdge))
            possibleJoins.add(PossibleJoin(p1, p2, Edge(commonEdge.a, commonEdge.b)))
          }
        }
      }
    }

    // fold all possible foldResults
    val foldResults = (possibleJoins.map { it.p1.join(it.p2, it.edge) }).filter { it != null }

    val joins = foldResults.map { it!!.area() }

    var maxPos = 0
    var maxArea = 10
    joins.forEachIndexed { i, d ->
      if(d > maxArea)
      {
        maxPos = i
      }
    }
    val maxFold = foldResults[maxPos]
    val maxRes = possibleJoins[maxPos]

    polys.remove(maxRes.p1)
    polys.remove(maxRes.p2)
    polys.add(maxFold!!)
    return polys
  }

}