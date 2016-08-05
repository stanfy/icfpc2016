package icfp16.data

import java.math.BigInteger


fun Fraction.add(that: Fraction): Fraction {
  val topPart = that.a.multiply(b) +  that.b.multiply(a)
  val bottomPart = that.b.multiply(b)
  val gcd = topPart.gcd(bottomPart)

  val finalTopPart = topPart.div(gcd)
  val finalBottomPart = bottomPart.div(gcd)
  return Fraction(finalTopPart, finalBottomPart)
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
  val gcd = topPart.gcd(bottomPart)

  val finalTopPart = topPart.div(gcd)
  val finalBottomPart = bottomPart.div(gcd)
  return Fraction(finalTopPart, finalBottomPart)
}

fun Fraction.inverse():Fraction{
  return Fraction(b,a)
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

