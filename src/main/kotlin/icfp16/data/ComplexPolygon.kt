package icfp16.data

data class ComlexPolygon(
    val initial: Polygon = Polygon(listOf(
        Vertex(Fraction(0), Fraction(0)),
        Vertex(Fraction(0), Fraction(1)),
        Vertex(Fraction(1), Fraction(1)),
        Vertex(Fraction(1), Fraction(0))
    )),
    val final: Polygon = initial
) {

  constructor(initial: List<Vertex>, final: List<Vertex>) : this(
      initial = Polygon(initial), final = Polygon(final)
  )
}



