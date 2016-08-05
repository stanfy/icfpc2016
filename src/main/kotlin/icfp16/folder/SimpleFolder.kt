package icfp16.folder

import icfp16.data.*
import java.math.BigInteger

fun Edge.fold(by: Edge): Edge {
  return by
}

fun Edge.middle(): Vertex {
  val midx = a.x.sub(b.x).div(2).abs()
  val midy = a.y.sub(b.y).div(2).abs()
  return Vertex(midx, midy)
}

fun Edge.crossPoint(that: Edge): Vertex {

//        if (a.x.equals(that.a.x))   // вертикаль
//        {
//            val y = a.y + ((that.a.Y - p1.Y) * (p3.X - p1.X)) / (p2.X - p1.X);
//
//            if (y > Math.Max(p3.Y, p4.Y) || y < Math.Min(p3.Y, p4.Y) || y > Math.Max(p1.Y, p2.Y) || y < Math.Min(p1.Y, p2.Y))   // если за пределами отрезков
//                return new Point(0, 0);
//            else
//            return new Point(p3.X, (int)y);
//        }
//        else            // горизонталь
//        {
//            double x = p1.X + ((p2.X - p1.X) * (p3.Y - p1.Y)) / (p2.Y - p1.Y);
//            if (x > Math.Max(p3.X, p4.X) || x < Math.Min(p3.X, p4.X) || x > Math.Max(p1.X, p2.X) || x < Math.Min(p1.X, p2.X))   // если за пределами отрезков
//                return new Point(0, 0);
//            else
//            return new Point((int)x, p3.Y);
//        }
  return that.a
}


data class Line(val edge: Edge) {

  fun interection(line: Line): Vertex? {
    // https://www.topcoder.com/community/data-science/data-science-tutorials/geometry-concepts-line-intersection-and-its-applications/
    val A1 = edge.a.y.sub(edge.b.y)
    val B1 = edge.b.x.sub(edge.a.x)
    val C1 = A1.mul(edge.a.x).add(B1.mul(edge.a.y))

    val A2 = line.edge.a.y.sub(line.edge.b.y)
    val B2 = line.edge.b.x.sub(line.edge.a.x)
    val C2 = A2.mul(line.edge.a.x).add(B2.mul(line.edge.a.y))

    val det = A1.mul(B2).sub(A2.mul(B1))
    if (det.a.equals(BigInteger.ZERO)) {
      return null
    }
    val x = (B2.mul(C1).sub(B1.mul(C2))).divFrac(det)
    val y = (A1.mul(C2).sub(A2.mul(C1))).divFrac(det)
    return Vertex(x, y)
  }

//  // Fraction(edge.b.y.sub(edge.a.y), edge.b.x.sub(edge.a.x))
//  var b: Fraction = null!!// = edge.b.x //Fraction(edge.a.y.sub(k.mul(edge.a.x)))
//
//  fun perp(): Line {
//
//    val m = edge.a
//
//    return this
//  }

}