package icfp16.estimate

import icfp16.data.*
import icfp16.state.State
import icfp16.visualizer.Visualizer
import java.awt.Color
import java.awt.Polygon
import java.awt.image.BufferedImage
import icfp16.problem
import icfp16.state.IState
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO

interface Estimator {

  /***
   * Quality - any int th
   */
  fun resemblanceOf(task: Problem, state: IState, quality: Int = 1) : Double
}


class EstimatorFactory {

  fun bestEstimatorEver(): Estimator {
    return BitmapEstimator()
  }
}

class BitmapEstimator : Estimator {
  // useful in tests to check outcome: BitmapEstimator().apply { debug = true }
  var debug: Boolean = false

  override fun resemblanceOf(task: Problem, state: IState, quality: Int): Double {
    val image = Visualizer().visualizationOf(task, state, quality)

    var allSquarePixels = 0
    var noncoveredPixels = 0
    var coveredPixels = 0
    for (x in 0..image.width - 1)  {
      for (y in 0..image.height - 1) {
        val rgb = image.getRGB(x, y)
        val color = Color(rgb)
        if (rgb == 0) continue

        allSquarePixels++

        if (rgb == Visualizer.originalItemColor.rgb) {
          noncoveredPixels++
        }
        if (color.red > 0 && color.blue > 0) {
          coveredPixels++
        }
      }
    }

    if (debug) {
      try {
        ImageIO.write(image, "png", File("./output_image.png"))
        System.out.println("-- save")
      } catch (e: IOException) {
        e.printStackTrace()
      }
    }
//    val matchedParts = allSquarePixels - noncoveredPixels
//    System.out.println("Matched $matchedParts")
//    System.out.println("NonMatcher ${noncoveredPixels}")
//    System.out.println("Total ${allSquarePixels}")
//    System.out.println("Covered ${coveredPixels}")
    val resemblance = coveredPixels.toDouble() / allSquarePixels.toDouble()
//    System.out.println("Resemlense ${resemblance}")
    return resemblance
  }

  fun bestPossibleResemblanceOf(task: Problem, state: IState, quality: Int): Double {
    val stateImage = Visualizer().visualizationOf(Problem(emptyList(), emptyList(), ""), state, quality)
    val problemImage = Visualizer().visualizationOf(task, State(emptyArray(), emptyArray(), arrayOf(task.poligons.first().vertices.first())), quality)

    var statePixels = 0
    var problemPixels = 0
    for (x in 0..stateImage.width - 1)  {
      for (y in 0..stateImage.height - 1) {
        if (stateImage.getRGB(x, y) != 0) statePixels++
        if (problemImage.getRGB(x, y) != 0) problemPixels++
      }
    }

    val maxCovered = if (problemPixels > statePixels) (statePixels) else (problemPixels)
    val allSquare = problemPixels + statePixels - maxCovered


    val bestPossibleResemblance = maxCovered / allSquare.toDouble()
    return bestPossibleResemblance
  }

}