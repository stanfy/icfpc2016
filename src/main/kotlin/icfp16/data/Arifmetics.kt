package icfp16.data

fun Fraction.add(f: Fraction): Fraction {
  val topPart = f.a.multiply(b) +  f.b.multiply(a)
  val bottomPart = f.b.multiply(b)
  val gcd = topPart.gcd(bottomPart)

  val finalTopPart = topPart.div(gcd)
  val finalBottomPart = bottomPart.div(gcd)
  return Fraction(finalTopPart, finalBottomPart)
}


fun Vertex.add(v: Vertex): Vertex {
  return Vertex(this.x.add(v.x), this.y.add(v.y))
}
