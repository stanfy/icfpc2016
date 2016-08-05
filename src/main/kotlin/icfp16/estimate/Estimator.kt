package icfp16.estimate

import icfp16.Problem
import icfp16.state.State
import java.awt.Canvas
import java.awt.Polygon
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO


interface Estimator {

  fun resemblenceOf(task: Problem, state: State) : Double

}

class BitmapEstimator : Estimator {
  val BITMAP_SIZE = 1000

  override fun resemblenceOf(task: Problem, state: State): Double {
    val image = BufferedImage(BITMAP_SIZE, BITMAP_SIZE, BufferedImage.TYPE_INT_RGB)
    val graphics = image.createGraphics()


    task.poligons.map {
      val xPoints = it.vertices.map { (BITMAP_SIZE/2 + BITMAP_SIZE/2 * it.x.toDouble()).toInt() }.toIntArray()
      val yPoints = it.vertices.map { (BITMAP_SIZE/2 + BITMAP_SIZE/2 * it.x.toDouble()).toInt() }.toIntArray()
      val poly = Polygon(xPoints, yPoints, xPoints.size)
        graphics.drawPolygon(poly)
    }

//    var lines = text.split('\n');
//    var n = 0;
//    var polygons = parseInt(lines[0]);
//    n++;
//    console.log('start for ' + polygons + ' p');
//
//    ctx.strokeStyle = 'black';
//    ctx.beginPath();
//    ctx.moveTo(ox, 0);
//    ctx.lineTo(ox, h);
//    ctx.stroke();
//    ctx.beginPath();
//    ctx.moveTo(0, oy);
//    ctx.lineTo(w, oy);
//    ctx.stroke();
//
//    for (var p = 0; p < polygons; p++) {
//      var numberOfPoints = parseInt(lines[n++]);
//
//      ctx.beginPath();
//      var line = lines[n++].split(',')
//      var x = parseValue(line[0]), y = parseValue(line[1]);
//      var x1 = ox + x * scale, y1 = oy - y * scale;
//      ctx.moveTo(x1, y1);
//      console.log('from ' + x1 + ', ' + y1);
//      for (var i = 1; i < numberOfPoints; i++) {
//      var line = lines[n++].split(',')
//      var x = parseValue(line[0]), y = parseValue(line[1]);
//      var xx = ox + x * scale, yy = oy - y * scale;
//      ctx.lineTo(xx, yy);
//      console.log('from ' + xx + ', ' + yy);
//    }
//      ctx.lineTo(x1, y1);
//      ctx.strokeStyle = 'green';
//      ctx.stroke();
//    }
//
//    if (!$('#skeleton').get(0).checked) { return; }
//
//    var skCount = parseInt(lines[n++]);
//    for (var i = 0; i < skCount; i++) {
//      var coords = lines[n++].split(' ');
//      var x1 = parseValue(coords[0].split(',')[0]);
//      var y1 = parseValue(coords[0].split(',')[1]);
//      var x2 = parseValue(coords[1].split(',')[0]);
//      var y2 = parseValue(coords[1].split(',')[1]);
//      console.log(x1 + ', ' + y1 + ' - ' + x2 + ',' + y2);
//      ctx.beginPath();
//      ctx.strokeStyle = 'blue';
//      ctx.moveTo(ox + x1 * scale, oy - y1 * scale);
//      ctx.lineTo(ox + x2 * scale, oy - y2 * scale);
//      ctx.stroke();
//    }
//
    try {
      ImageIO.write(image, "png", File("./output_image.png"))
      System.out.println("-- saved")
    } catch (e: IOException) {
      e.printStackTrace()
    }
    return 0.0
  }
}