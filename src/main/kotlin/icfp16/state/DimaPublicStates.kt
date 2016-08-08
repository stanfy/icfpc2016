package icfp16.state


import icfp16.data.ComplexPolygon
import icfp16.data.Edge
import icfp16.data.Fraction
import icfp16.data.Vertex

class DimaPublicStates {
  companion object {
    val states: Array<IState> = arrayOf(
        ComplexState()
            .fold(Edge(
                Vertex(Fraction(1), Fraction(1, 2)),
                Vertex(Fraction(0), Fraction(1, 2))
            ))
            .fold(Edge(
                Vertex(Fraction(1), Fraction(3, 4)),
                Vertex(Fraction(1, 2), Fraction(1, 2))
            )).setName("problem_18"),

        ComplexState()
            .fold(Edge(
                Vertex(Fraction(2, 3), Fraction(1)),
                Vertex(Fraction(1), Fraction(2, 3)))
            ).fold(Edge(
            Vertex(Fraction(0), Fraction(2, 3)),
            Vertex(Fraction(1, 3), Fraction(1)))
        ).setName("problem_38, 994"),

        ComplexState()
            .fold(Edge(
                Vertex(Fraction(0), Fraction(1, 2)),
                Vertex(Fraction(1, 2), Fraction(1)))
            ).fold(Edge(
            Vertex(Fraction(1, 2), Fraction(1)),
            Vertex(Fraction(1), Fraction(1, 2)))
        ).setName("problem_39, 975 (0.9255216693418941)"),

        ComplexState()
            .fold(Edge(
                Vertex(Fraction(1, 2), Fraction(1)),
                Vertex(Fraction(1), Fraction(1, 2)))
            ).setName("problem_158 (1.0)"),

        ComplexState()
            .fold(Edge(
                Vertex(Fraction(0), Fraction(1, 2)),
                Vertex(Fraction(1, 2), Fraction(1))
            ))
            .setName("problem_1075 (1.0)"),
        ComplexState()
            .fold(Edge(
                Vertex(Fraction(1), Fraction(1)),
                Vertex(Fraction(0), Fraction(0))
            ))
            .setName("problem_1759, 1776 (1.0)"), // !!!!!!!!!!
        ComplexState()
            .fold(Edge(
                Vertex(Fraction(3, 4), Fraction(0)),
                Vertex(Fraction(0), Fraction(3, 4))
            ))
            .setName("problem_1848 (1.0)"),

        ComplexState()
            .fold(Edge(
                Vertex(Fraction(1, 8), Fraction(0)),
                Vertex(Fraction(0), Fraction(1, 8))
            ))
            .setName("problem_2084 (1.0)"),

        ComplexState()
            .fold(Edge(
                Vertex(Fraction(1, 42), Fraction(0)),
                Vertex(Fraction(0), Fraction(1, 42))
            )).fold(Edge(
                Vertex(Fraction(41, 42), Fraction(1)),
                Vertex(Fraction(1), Fraction(41, 42))
            ))
            .setName("problem_2235 (1.0)"),

        ComplexState()
            .fold(Edge(
                Vertex(Fraction(1, 3), Fraction(1)),
                Vertex(Fraction(1), Fraction(1, 3))
            ))
            .setName("problem_2247 (1.0)"),

        ComplexState()
            .fold(Edge(
                Vertex(Fraction(0), Fraction(1, 2)),
                Vertex(Fraction(1), Fraction(1, 2))
            ))
            .setName("problem_3020, 3043, 779 (1.0)"),

        ComplexState()
            .fold(Edge(
                Vertex(Fraction(1), Fraction(1, 2)),
                Vertex(Fraction(1, 2), Fraction(0))
            )).fold(Edge(
              Vertex(Fraction(1, 2), Fraction(1)),
              Vertex(Fraction(1), Fraction(1, 2))
            )).fold(Edge(
                Vertex(Fraction(1), Fraction(1, 2)),
                Vertex(Fraction(0), Fraction(1, 2))
            ))
            .setName("problem_1567, 175, 776, 845, 1068, 1071, 1074, 1076, 1085, 1118, 1132, 1231, 1371, 1372, 1373, 1374, 1375, 1377, 1383, 1384, 1385, 1386, 1387, 1389, 1391 (1.0)"),

            ComplexState()
            .foldMountainVAlley(
                Edge(
                    Vertex(Fraction(3, 16), Fraction(0)),
                    Vertex(Fraction(0), Fraction(3, 16))
                ),
                Edge(
                    Vertex(Fraction(2, 16), Fraction(0)),
                    Vertex(Fraction(0), Fraction(2, 16))
                )
            )

                .foldMountainVAlley(
                    Edge(
                        Vertex(Fraction(13, 16), Fraction(1)),
                        Vertex(Fraction(1), Fraction(13, 16))
                    ),
                    Edge(
                        Vertex(Fraction(14, 16), Fraction(1)),
                        Vertex(Fraction(1), Fraction(14, 16))
                    )
                )

                .foldMountainVAlley(
                    Edge(
                        Vertex(Fraction(0), Fraction(13, 16)),
                        Vertex(Fraction(3, 16), Fraction(1))
                    ),
                    Edge(
                        Vertex(Fraction(0), Fraction(14, 16)),
                        Vertex(Fraction(2, 16), Fraction(1))
                    )
                )

                .foldMountainVAlley(
                    Edge(
                        Vertex(Fraction(1), Fraction(3, 16)),
                        Vertex(Fraction(13, 16), Fraction(0))
                    ),
                    Edge(
                        Vertex(Fraction(1), Fraction(2, 16)),
                        Vertex(Fraction(14, 16), Fraction(0))
                    )
                )

//                .foldMountainVAlley(
//                    Edge(
//                        Vertex(Fraction(3, 16), Fraction(0)),
//                        Vertex(Fraction(0), Fraction(3, 16))
//                    ),
//                    Edge(
//                        Vertex(Fraction(2, 16), Fraction(0)),
//                        Vertex(Fraction(0), Fraction(2, 16))
//                    )
//                )
//
//                .foldMountainVAlley(
//                    Edge(
//                        Vertex(Fraction(3, 16), Fraction(0)),
//                        Vertex(Fraction(0), Fraction(3, 16))
//                    ),
//                    Edge(
//                        Vertex(Fraction(2, 16), Fraction(0)),
//                        Vertex(Fraction(0), Fraction(2, 16))
//                    )
//                )
//            .foldMountainVAlley(
//                Edge(
//                    Vertex(Fraction(4, 8), Fraction(8, 8)),
//                    Vertex(Fraction(8, 8), Fraction(4, 8))
//                ),
//                Edge(
//                    Vertex(Fraction(5, 8), Fraction(8, 8)),
//                    Vertex(Fraction(8, 8), Fraction(5, 8))
//                )
//            )
//            .foldMountainVAlley(
//                Edge(
//                    Vertex(Fraction(0, 8), Fraction(4, 8)),
//                    Vertex(Fraction(4, 8), Fraction(8, 8))
//                ),
//                Edge(
//                    Vertex(Fraction(0, 8), Fraction(5, 8)),
//                    Vertex(Fraction(3, 8), Fraction(8, 8))
//                )
//            )
            .setName("Quarukafdfsdsfd3")

 // to submit: 1414, 1418, 1419, 1423, 1425, 1428, 1429, 1431, 1433, 1437, 1440, 1444, 1445, 1449, 1450, 1624, 2021, 2237, 2334, 2412, 2494, 2580, 2581, 2582, 2583, 2770, 2997
    )

  }
}