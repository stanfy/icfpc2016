package icfp16.visualizer

import icfp16.data.*
import icfp16.state.IState
import icfp16.state.State
import java.awt.BasicStroke
import java.awt.Color
import java.awt.Polygon
import java.awt.RenderingHints
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class Visualizer {
  val BITMAP_STEP = 1000
  companion object {
    val originalItemColor = Color.RED
    val solutionColor = Color(0, 0, 255, 32)
  }

  fun visualizationOf(task: Problem, state: IState? = null, quality: Int = 1, resemblance : Double? = null): BufferedImage {
    val BITMAP_SIZE = BITMAP_STEP * quality
    val image = BufferedImage(BITMAP_SIZE, BITMAP_SIZE, BufferedImage.TYPE_INT_ARGB)
    val graphics = image.createGraphics()


    graphics.color = originalItemColor

    val vertexes = task.poligons.flatMap { it.vertices }
    val centroid = if (task.poligons.isEmpty()) {
      centroid(state!!.finalPositions().toList())
    } else {
      centroid(vertexes)
    }
        .add(Vertex(Fraction(-1, 2), Fraction(-1, 2)))

    task.poligons
        .map { it.sub(centroid) }
        .forEach {
          val xPoints = it.vertices.map { (BITMAP_SIZE / 4 + BITMAP_SIZE / 2 * it.x.toDouble()).toInt() }.toIntArray()
          val yPoints = it.vertices.map { BITMAP_SIZE - (BITMAP_SIZE / 4 + BITMAP_SIZE / 2 * it.y.toDouble()).toInt() }.toIntArray()
          val poly = Polygon(xPoints, yPoints, xPoints.size)
          graphics.fillPolygon(poly)
        }

    if (state == null) {
      return image
    }
    graphics.color = solutionColor
    state.poligons()
        .map { it.sub(centroid) }
        .forEach {
          val xPoints = it.vertices.map { (BITMAP_SIZE / 4 + BITMAP_SIZE / 2 * it.x.toDouble()).toInt() }.toIntArray()
          val yPoints = it.vertices.map { BITMAP_SIZE - (BITMAP_SIZE / 4 + BITMAP_SIZE / 2 * it.y.toDouble()).toInt() }.toIntArray()
          val poly = Polygon(xPoints, yPoints, xPoints.size)
          graphics.fillPolygon(poly)
        }

    if (resemblance != null) {
      val currentFont = graphics.font
      val newFont = currentFont.deriveFont(currentFont.size * 4f)
      graphics.font = newFont
      if (resemblance == 1.0) {
      } else if (resemblance > 0.9) {
        graphics.color = Color.YELLOW
      } else if (resemblance > 0.8) {
        graphics.color = Color.ORANGE
      } else {
        graphics.color = Color.RED
      }
      graphics.drawString("Resemblance = $resemblance", 10, 40)
    }
    return image
  }

  fun visualizeFolds(state: IState, quality: Int = 1): BufferedImage {
    val BITMAP_SIZE = BITMAP_STEP * quality
    val image = BufferedImage(BITMAP_SIZE, BITMAP_SIZE, BufferedImage.TYPE_INT_ARGB)
    val graphics = image.createGraphics()

    graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

    val m = BITMAP_SIZE / 10
    val s = BITMAP_SIZE
    graphics.color = Color(255, 255, 0, 128)
    graphics.fillPolygon(Polygon(intArrayOf(m, s - m, s - m, m), intArrayOf(m, m, s - m, s - m), 4))

    val centroid = centroid(state.vertexes().toList())
      .add(Vertex(Fraction(-1, 2), Fraction(-1, 2)))

    graphics.color = Color.GRAY
    graphics.stroke = BasicStroke(4f)
    state.initialPoligons()
        .map { it.sub(centroid) }
        .forEach {
          val xPoints = it.vertices.map { (m + (BITMAP_SIZE - 2*m) * it.x.toDouble()).toInt() }.toIntArray()
          val yPoints = it.vertices.map { BITMAP_SIZE - (m + (BITMAP_SIZE - 2*m) * it.y.toDouble()).toInt() }.toIntArray()
          val poly = Polygon(xPoints, yPoints, xPoints.size)
          graphics.drawPolygon(poly)
        }

    return image
  }

  fun visualizedAndSaveImage(task: Problem = Problem(emptyList(), emptyList()), state: IState? = null, quality: Int = 1
                             , filePath: String = "output.png", resemblance: Double? = null) {
    val image = visualizationOf(task, state, quality, resemblance)
    ImageIO.write(image,"png", File(filePath))
  }

  fun visualizedAndSaveFolds(state: IState, quality: Int = 1, filePath: String = "fold_output.png") {
    val image = visualizeFolds(state, quality)
    ImageIO.write(image,"png", File(filePath))
  }

}