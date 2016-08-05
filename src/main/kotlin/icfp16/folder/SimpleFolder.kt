package icfp16.folder
import icfp16.data.Edge
import icfp16.data.Fraction

class SimpleFolder{
    fun perp(e: Edge): Edge {

        return e
    }

}

data class Line (val edge: Edge){

    val k: Fraction = edge.a.x
    // Fraction(edge.b.y.sub(edge.a.y), edge.b.x.sub(edge.a.x))
    val b: Fraction = edge.b.x //Fraction(edge.a.y.sub(k.mul(edge.a.x)))
    fun perp(): Line {

        val m = edge.a

        return this
    }

}