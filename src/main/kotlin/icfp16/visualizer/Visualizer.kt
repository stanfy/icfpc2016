package icfp16.visualizer

import icfp16.data.*
import icfp16.state.State
import java.awt.Color
import java.awt.Graphics2D
import java.awt.Image
import java.awt.Polygon
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class Visualizer {
  val BITMAP_STEP = 1000
  companion object {
    val originalItemColor = Color.YELLOW
    val solutionColor = Color(0, 0, 255, 128)
  }

  fun visualizationOf(task: Problem, state: State, quality: Int = 1): BufferedImage {
    val BITMAP_SIZE = BITMAP_STEP * quality
    val image = BufferedImage(BITMAP_SIZE, BITMAP_SIZE, BufferedImage.TYPE_INT_ARGB)
    val graphics = image.createGraphics()


    graphics.color = originalItemColor

    val vertexes = task.poligons.flatMap { it.vertices }
    val centroid = centroid(vertexes).add(Vertex(Fraction(-1, 2), Fraction(-1, 2)))

    task.poligons
        .map { it.translate(centroid) }
        .map {
          val xPoints = it.vertices.map { (BITMAP_SIZE / 4 + BITMAP_SIZE / 2 * it.x.toDouble()).toInt() }.toIntArray()
          val yPoints = it.vertices.map { (BITMAP_SIZE / 4 + BITMAP_SIZE / 2 * it.y.toDouble()).toInt() }.toIntArray()
          val poly = Polygon(xPoints, yPoints, xPoints.size)
          graphics.fillPolygon(poly)
        }

    graphics.color = solutionColor
    state.poligons()
        .map { it.translate(centroid) }
        .map {
          val xPoints = it.vertices.map { (BITMAP_SIZE / 4 + BITMAP_SIZE / 2 * it.x.toDouble()).toInt() }.toIntArray()
          val yPoints = it.vertices.map { (BITMAP_SIZE / 4 + BITMAP_SIZE / 2 * it.y.toDouble()).toInt() }.toIntArray()
          val poly = Polygon(xPoints, yPoints, xPoints.size)
          graphics.fillPolygon(poly)
        }
    return image
  }

  fun visualizedAndSaveImage(task: Problem, state: State, quality: Int = 1, filePath: String) {
    val image = visualizationOf(task, state, quality)
    ImageIO.write(image,"png", File(filePath))
  }
}