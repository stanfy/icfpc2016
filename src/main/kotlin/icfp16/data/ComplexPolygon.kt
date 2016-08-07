package icfp16.data

data class ComplexPolygon(
    val transform: Transform = TRANSFORM_IDENTITY,
    val final: Polygon = Polygon(listOf(
        Vertex(Fraction(0), Fraction(0)),
        Vertex(Fraction(0), Fraction(1)),
        Vertex(Fraction(1), Fraction(1)),
        Vertex(Fraction(1), Fraction(0))
    ))
) {

  private val initial: Lazy<Polygon> = lazy {
    Polygon(final.vertices.map { transform.reverse(it) })
  }

  constructor(transform: Transform, final: List<Vertex>) : this(
      transform, Polygon(final)
  )

  fun initial(): Polygon = initial.value
}

abstract class Transform {
  abstract fun apply(point: Vertex): Vertex
  abstract fun reverse(point: Vertex): Vertex
  open fun compose(t: Transform): Transform = ComposedTransform(this, t)
}

private class ComposedTransform(val t1: Transform, val t2: Transform) : Transform() {
  override fun reverse(point: Vertex): Vertex = t1.reverse(t2.reverse(point))
  override fun apply(point: Vertex): Vertex = t2.apply(t1.apply(point))
}

val TRANSFORM_IDENTITY = object : Transform() {
  override fun apply(point: Vertex): Vertex = point
  override fun reverse(point: Vertex): Vertex = point
  override fun compose(t: Transform): Transform = t
}

val TRANSFORM_NEG = object : Transform() {
  override fun apply(point: Vertex): Vertex = Vertex(point.x.neg(), point.y.neg())
  override fun reverse(point: Vertex): Vertex = apply(point)
}

class ReflectTransform(val edge: Edge) : Transform() {
  override fun apply(point: Vertex): Vertex = point.reflect(edge)
  override fun reverse(point: Vertex): Vertex = point.reflect(edge)
}

class TranslateTransform(val t: Vertex) : Transform() {
  override fun apply(point: Vertex): Vertex = point.add(t)
  override fun reverse(point: Vertex): Vertex = point.sub(t)
}

class RotateTransform(val v: Vertex, val t: Triple<Int, Int, Int>) : Transform() {
  override fun apply(point: Vertex): Vertex = point.rotate(v, t)
  override fun reverse(point: Vertex): Vertex = throw UnsupportedOperationException("TODO")
}

class Rotate90Transform(val v: Vertex) : Transform() {
  override fun apply(point: Vertex): Vertex {
    val relativePoint = point.sub(v)
    val related = Vertex(relativePoint.y.neg(), relativePoint.x)
    return related.add(v)
  }
  override fun reverse(point: Vertex): Vertex {
    val relativePoint = point.sub(v)
    val related = Vertex(relativePoint.y, relativePoint.x.neg())
    return related.add(v)
  }
}

class ReverseTransform(val t: Transform) : Transform() {
  override fun apply(point: Vertex): Vertex = t.reverse(point)
  override fun reverse(point: Vertex): Vertex = t.apply(point)
}

