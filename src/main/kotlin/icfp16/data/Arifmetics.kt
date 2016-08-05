package icfp16.data

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

fun Fraction.div(that: Fraction): Fraction {
  return mul(that.inverse())
}


fun Vertex.add(v: Vertex): Vertex {
  return Vertex(this.x.add(v.x), this.y.add(v.y))
}
