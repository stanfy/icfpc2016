package icfp16.farm

import icfp16.data.Problem
import icfp16.data.ProblemContainer
import icfp16.data.ProblemParser
import java.io.File


class ProblemContainersParser {

    fun parseProblemContainerFromFile(filePath: String): ProblemContainer? {
        val file = File(filePath)
        val problemName = file.nameWithoutExtension
        val regex = "problem_(\\d*)".toRegex()

        val problemId = regex.matchEntire(problemName)?.groups?.get(1)?.value
        println("problemId " + problemId)
        if (problemId == null) {
            return null
        }

        val rawProblem = file.readText()
        println("raw problem " + rawProblem)

        return generateProblemContainerFromRawProblem(rawProblem, problemId)
    }

    fun generateProblemContainerFromRawProblem(rawProblem: String, problemId: String): ProblemContainer? {
        val problem = ProblemParser().parseProblem(rawProblem)
        val container = ProblemContainer(problem, problemId, "")
        return container
    }
}