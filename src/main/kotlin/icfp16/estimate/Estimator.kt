package icfp16.estimate

import icfp16.data.*
import icfp16.state.State
import icfp16.visualizer.Visualizer
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
  override fun resemblanceOf(task: Problem, state: State, quality: Int): Double {
    val image = Visualizer().visualizationOf(task, state, quality)

    var allSquarePixels = 0
    var noncoveredPixels = 0
    var coveredPixels = 0
    for (x in 0..image.width - 1)  {
      for (y in 0..image.height - 1) {
        val rgb = image.getRGB(x, y)
        if (rgb == 0) continue

        allSquarePixels++

        if (rgb == Visualizer.originalItemColor.rgb) {
          noncoveredPixels++
        }
        if (rgb != Visualizer.originalItemColor.rgb && rgb != Visualizer.solutionColor.rgb) {
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