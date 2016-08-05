package icfp16.folder
import icfp16.data.*
import java.math.BigInteger

fun Edge.fold(by : Edge) : Edge{
    return by
}

fun Edge.middle() : Vertex{
    val midx = a.x.sub(b.x).div(2).abs()
    val midy = a.y.sub(b.y).div(2).abs()
    return Vertex(midx, midy)
}

fun Edge.crossPoint(that : Edge) : Vertex{

//        if (a.x.equals(that.a.x))   // вертикаль
//        {
//            val y = a.y + ((that.a.Y - p1.Y) * (p3.X - p1.X)) / (p2.X - p1.X);
//
//            if (y > Math.Max(p3.Y, p4.Y) || y < Math.Min(p3.Y, p4.Y) || y > Math.Max(p1.Y, p2.Y) || y < Math.Min(p1.Y, p2.Y))   // если за пределами отрезков
//                return new Point(0, 0);
//            else
//            return new Point(p3.X, (int)y);
//        }
//        else            // горизонталь
//        {
//            double x = p1.X + ((p2.X - p1.X) * (p3.Y - p1.Y)) / (p2.Y - p1.Y);
//            if (x > Math.Max(p3.X, p4.X) || x < Math.Min(p3.X, p4.X) || x > Math.Max(p1.X, p2.X) || x < Math.Min(p1.X, p2.X))   // если за пределами отрезков
//                return new Point(0, 0);
//            else
//            return new Point((int)x, p3.Y);
//        }
    return that.a
}



//class Line {
//
//    constructor(k: Fraction, b: Fraction) {
//        this.k = k
//        this.b =b
//    }
//
//    constructor(edge:Edge){
//        this.k = edge.a.x
//        // Fraction(edge.b.y.sub(edge.a.y), edge.b.x.sub(edge.a.x))
//        this.b = edge.b.x //Fraction(edge.a.y.sub(k.mul(edge.a.x)))
//    }
//
//    abstract var k: Fraction
//    // Fraction(edge.b.y.sub(edge.a.y), edge.b.x.sub(edge.a.x))
//    var b: Fraction = null!!// = edge.b.x //Fraction(edge.a.y.sub(k.mul(edge.a.x)))
//
//    fun perp(): Line {
//
//        val m = edge.a
//
//        return this
//    }
//
//}