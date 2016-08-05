package icfp16.estimate

import icfp16.data.Problem
import icfp16.state.State
import java.awt.Color
import java.awt.Polygon
import java.awt.image.BufferedImage


interface Estimator {

  /***
   * Quality - any int th
   */
  fun resemblanceOf(task: Problem, state: State, quality: Int = 1) : Double
}


class EstimatorFactory {

  fun bestEstimatorEver(): Estimator {
    return BitmapEstimator()
  }
}


class BitmapEstimator : Estimator {
  val BITMAP_STEP = 1000

  override fun resemblanceOf(task: Problem, state: State, quality: Int): Double {
    val BITMAP_SIZE = BITMAP_STEP *  quality
    val image = BufferedImage(BITMAP_SIZE, BITMAP_SIZE, BufferedImage.TYPE_INT_ARGB)
    val graphics = image.createGraphics()

    val originalItemColor = Color.YELLOW
    val solutionColor = Color(0, 0, 255, 128)

    graphics.color = originalItemColor
    task.poligons.map {
      val xPoints = it.vertices.map { (BITMAP_SIZE/4 + BITMAP_SIZE/2 * it.x.toDouble()).toInt() }.toIntArray()
      val yPoints = it.vertices.map { (BITMAP_SIZE/4 + BITMAP_SIZE/2 * it.y.toDouble()).toInt() }.toIntArray()
      val poly = Polygon(xPoints, yPoints, xPoints.size)
      graphics.fillPolygon(poly)
    }

    graphics.color = solutionColor
    state.poligons().map {
      val xPoints = it.vertices.map { (BITMAP_SIZE/4 + BITMAP_SIZE/2 * it.x.toDouble()).toInt() }.toIntArray()
      val yPoints = it.vertices.map { (BITMAP_SIZE/4 + BITMAP_SIZE/2 * it.y.toDouble()).toInt() }.toIntArray()
      val poly = Polygon(xPoints, yPoints, xPoints.size)
      graphics.fillPolygon(poly)
    }

    var allSquarePixels = 0
    var noncoveredPixels = 0
    var coveredPixels = 0
    for (x in 0..BITMAP_SIZE - 1)  {
      for (y in 0..BITMAP_SIZE - 1) {
        val rgb = image.getRGB(x, y)
        if (rgb == 0) continue

        allSquarePixels++

        if (rgb == originalItemColor.rgb) {
          noncoveredPixels++
        }
        if (rgb != originalItemColor.rgb && rgb != solutionColor.rgb) {
          coveredPixels++
        }
      }
    }

//    try {
//      ImageIO.write(image, "png", File("./output_image.png"))
//      System.out.println("-- save")
//    } catch (e: IOException) {
//      e.printStackTrace()
//    }
//    val matchedParts = allSquarePixels - noncoveredPixels
//    System.out.println("Matched $matchedParts")
//    System.out.println("NonMatcher ${noncoveredPixels}")
//    System.out.println("Total ${allSquarePixels}")
//    System.out.println("Covered ${coveredPixels}")
    val resemblance = coveredPixels.toDouble() / allSquarePixels.toDouble()
//    System.out.println("Resemlense ${resemblence}")
    return resemblance
  }
}