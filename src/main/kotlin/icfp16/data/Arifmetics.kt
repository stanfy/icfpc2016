package icfp16.data

import java.math.BigInteger


fun Fraction.simple(): Fraction{
  val gcd = a.gcd(b)

  val finalTopPart = a.div(gcd)
  val finalBottomPart = b.div(gcd)
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

fun Fraction.inverse():Fraction{
  return Fraction(b,a)
}

fun Fraction.equals(that:Fraction):Boolean{
  val simpleThis = this.simple()
  val simpleThat = that.simple()
  return simpleThis.a == simpleThat.a && simpleThis.b == simpleThat.b

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


fun Vertex.div(v: Int): Vertex {
  return Vertex(this.x.div(v), this.y.div(v))
}

fun Vertex.div(v: BigInteger): Vertex {
  return Vertex(this.x.div(v), this.y.div(v))
}

/// THis is not correct actually
fun Polygon.translate(v: Vertex): Polygon {
  return Polygon(this.vertices.map { it.add(Vertex(v.x.neg(), v.y.neg())) })
}

fun centroid(vertices: List<Vertex>): Vertex {
  return vertices.reduce { original, next ->
    original.add(next)
  }.div(vertices.size)
}