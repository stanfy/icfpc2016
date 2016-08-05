package icfp16.visualizer

import icfp16.data.Problem
import icfp16.state.State
import java.awt.Color
import java.awt.Image
import java.awt.Polygon
import java.awt.image.BufferedImage

class Visualizer {
  val BITMAP_STEP = 1000
  fun visualizationOf(task: Problem, state: State, quality: Int = 1): Image {
    val BITMAP_SIZE = BITMAP_STEP * quality
    val image = BufferedImage(BITMAP_SIZE, BITMAP_SIZE, BufferedImage.TYPE_INT_ARGB)
    val graphics = image.createGraphics()

    val originalItemColor = Color.YELLOW
    val solutionColor = Color(0, 0, 255, 128)

    graphics.color = originalItemColor
    task.poligons.map {
      val xPoints = it.vertices.map { (BITMAP_SIZE / 4 + BITMAP_SIZE / 2 * it.x.toDouble()).toInt() }.toIntArray()
      val yPoints = it.vertices.map { (BITMAP_SIZE / 4 + BITMAP_SIZE / 2 * it.y.toDouble()).toInt() }.toIntArray()
      val poly = Polygon(xPoints, yPoints, xPoints.size)
      graphics.fillPolygon(poly)
    }

    graphics.color = solutionColor
    state.poligons().map {
      val xPoints = it.vertices.map { (BITMAP_SIZE / 4 + BITMAP_SIZE / 2 * it.x.toDouble()).toInt() }.toIntArray()
      val yPoints = it.vertices.map { (BITMAP_SIZE / 4 + BITMAP_SIZE / 2 * it.y.toDouble()).toInt() }.toIntArray()
      val poly = Polygon(xPoints, yPoints, xPoints.size)
      graphics.fillPolygon(poly)
    }
    return image
  }
}