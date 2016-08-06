package icfp16.data

import icfp16.state.State
import java.math.BigInteger


fun Fraction.simple(): Fraction{
  val gcd = a.gcd(b)

  val finalTopPart = a.div(gcd)
  val finalBottomPart = b.div(gcd)

  // (-1,-1) == (1,1)
  if(finalBottomPart < BigInteger.ZERO)
  {
    return Fraction(finalTopPart.negate(), finalBottomPart.negate())
  }

  return Fraction(finalTopPart, finalBottomPart)
}

fun Fraction.add(that: Fraction): Fraction {
  val topPart = that.a.multiply(b) +  that.b.multiply(a)
  val bottomPart = that.b.multiply(b)

  return Fraction(topPart, bottomPart).simple()
}

fun Fraction.neg():Fraction{
  return Fraction(-a,b)
}

fun Fraction.sub(that: Fraction): Fraction {
  return add(that.neg())
}

fun Fraction.mul(that: Fraction): Fraction {
  val topPart = that.a.multiply(a)
  val bottomPart = that.b.multiply(b)

  return Fraction(topPart, bottomPart).simple()
}

fun Fraction.mul(dbl: Double): Fraction {
  val orig = (dbl * 1000000000000).toString()
  val str = orig.filterIndexed { i, c -> i < orig.indexOf(".") }
  val that = Fraction(BigInteger(str), BigInteger(1000000000000.toString()))
  val topPart = that.a.multiply(a)
  val bottomPart = that.b.multiply(b)
  return Fraction(topPart, bottomPart).simple()
}


fun Fraction.inverse():Fraction{
  return Fraction(b,a)
}

fun Fraction.equals(that:Fraction):Boolean{
  val simpleThis = this.simple()
  val simpleThat = that.simple()
  return simpleThis.a == simpleThat.a && simpleThis.b == simpleThat.b

}

fun Fraction.scaledA(x: BigInteger): BigInteger = this.a.multiply(x)

fun Fraction.leq(that:Fraction):Boolean = this.scaledA(that.b) <= that.scaledA(this.b)
fun Fraction.le(that:Fraction):Boolean = this.scaledA(that.b) < that.scaledA(this.b)
fun Fraction.geq(that:Fraction):Boolean = this.scaledA(that.b) >= that.scaledA(this.b)
fun Fraction.ge(that:Fraction):Boolean = this.scaledA(that.b) > that.scaledA(this.b)

fun Fraction.max(that:Fraction) : Fraction{
  if(this.geq(that))
    return this
  return that
}

fun Fraction.min(that:Fraction) : Fraction{
  if(this.geq(that))
    return that
  return this
}

fun Fraction.abs():Fraction{
  return Fraction(a.abs(),b)
}

fun Fraction.divFrac(that: Fraction): Fraction {
  return mul(that.inverse())
}

fun Fraction.div(v: Int): Fraction {
  return this.div(BigInteger("$v"))
}


fun Fraction.div(v:BigInteger): Fraction {
  val topPart = this.a
  val bottomPart = this.b.multiply(v)
  val gcd = topPart.gcd(bottomPart)

  val finalTopPart = topPart.div(gcd)
  val finalBottomPart = bottomPart.div(gcd)
  return Fraction(finalTopPart, finalBottomPart)
}


fun Vertex.add(v: Vertex): Vertex {
  return Vertex(this.x.add(v.x), this.y.add(v.y))
}

fun Vertex.sub(v: Vertex): Vertex {
  return Vertex(this.x.sub(v.x), this.y.sub(v.y))
}


fun Vertex.div(v: Int): Vertex {
  return Vertex(this.x.div(v), this.y.div(v))
}

fun Vertex.div(v: BigInteger): Vertex {
  return Vertex(this.x.div(v), this.y.div(v))
}

fun Vertex.reflect(edge : Edge) : Vertex {

//  double dx,dy,a,b;
//  long x2,y2;
//  Point p1; //reflected point to be returned
//
//
//
//  p1 = Point((int)x2,(int)y2);

  val x0 = edge.a.x
  val x1 = edge.b.x
  val y0 = edge.a.y
  val y1 = edge.b.y

//  dx  = (double) (x1 - x0);
//  dy  = (double (y1 - y0);

  val dx  = x1.sub(x0);
  val dy  = y1.sub(y0);

  val dxs = dx.mul(dx) // dx * dx
  val dys = dy.mul(dy) //dy * dy

  val dsum = dxs.add(dys) // (dx * dx + dy*dy)
  val ddiff = dxs.sub(dys) //(dx * dx - dy * dy)

//  a   = (dx * dx - dy * dy) / (dx * dx + dy*dy);
  val  a   = ddiff.divFrac(dsum);
//  b   = 2 * dx * dy / (dx*dx + dy*dy);
  val  b   = Fraction(2).mul(dx).mul(dy).divFrac(dsum);


//  x2  = a * (p.x - x0) + b*(p.y - y0) + x0;
//  y2  = b * (p.x - x0) - a*(p.y - y0) + y0;

  val pdx = x.sub(x0) // p.x - x0
  val pdy = y.sub(y0) // p.y - y0

  val  x2  = a.mul(pdx).add(b.mul(pdy)).add(x0);
  val  y2  = b.mul(pdx).sub(a.mul(pdy)).add(y0);

  return Vertex(x2, y2)
}

fun Vertex.rotate(around: Vertex, pihagorean: Triple<Int, Int, Int>): Vertex {
  val cos = Fraction(pihagorean.first, pihagorean.third)
  val sin = Fraction(pihagorean.second, pihagorean.third)
  val x =
      around.x
          .add(this.x.sub(around.x).mul(cos))
          .sub(this.y.sub(around.y).mul(sin))

  val y =
      around.y
          .add(this.x.sub(around.x).mul(sin))
          .add(this.y.sub(around.y).mul(cos))

  return Vertex(x, y)
}


/// THis is not correct actually
fun Polygon.sub(v: Vertex): Polygon {
  return Polygon(this.vertices.map { it.add(Vertex(v.x.neg(), v.y.neg())) })
}

/// THis is not correct actually
fun Polygon.add(v: Vertex): Polygon {
  return Polygon(this.vertices.map { it.add(Vertex(v.x, v.y)) })
}

fun centroid(vertices: List<Vertex>): Vertex {
  return vertices.reduce { original, next ->
    original.add(next)
  }.div(vertices.size)
}

fun Vertex.withinBoundary(edge:Edge): Boolean {
  val maxx = edge.a.x.max(edge.b.x)
  val maxy = edge.a.y.max(edge.b.y)
  val minx = edge.a.x.min(edge.b.x)
  val miny = edge.a.y.min(edge.b.y)


  return x.geq(minx) && x.leq(maxx) && y.geq(miny) && y.leq(maxy)
  //}
}

fun massCentroid(vertices: List<Vertex>): Vertex {

  // http://stackoverflow.com/questions/5271583/center-of-gravity-of-a-polygon
  var sum = Fraction(0, 1)
  var cx = Fraction(0, 1)
  var cy = Fraction(0, 1)
  vertices.forEachIndexed { index, vertex ->
    if (index != vertices.lastIndex) {
      val nextVertex = vertices[index + 1]
      sum = sum.add(vertex.x.mul(nextVertex.y).sub(nextVertex.x.mul(vertex.y)))
      cx = cx.add(
          (vertex.x.add(nextVertex.x))
              .mul(vertex.x.mul(nextVertex.y).sub(nextVertex.x.mul(vertex.y)))
      )
      cy = cy.add(
          (vertex.y.add(nextVertex.y))
              .mul(vertex.x.mul(nextVertex.y).sub(nextVertex.x.mul(vertex.y)))
      )
    }
  }
  var area = sum.div(2)

  cx = cx.div(6).divFrac(area)
  cy = cy.div(6).divFrac(area)


  return Vertex(cx, cy)
}