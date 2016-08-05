package icfp16.io

import icfp16.data.Problem
import icfp16.data.ProblemContainer
import icfp16.io.FileUtils
import icfp16.problem.ProblemParser
import java.io.File


class ProblemContainersParser {

  fun generateProblemContainerForProblemId(problemId: String): ProblemContainer? {
    val filePath = FileUtils().getFullPathForProblemId(problemId)
    return generateProblemContainerFromFile(filePath)
  }

  fun generateProblemContainerFromFile(filePath: String): ProblemContainer? {
    val file = File(filePath)
    val problemName = file.nameWithoutExtension
    val regex = "problem_(\\d*)".toRegex()

    val problemId = regex.matchEntire(problemName)?.groups?.get(1)?.value

    if (problemId == null) {
      return null
    }

    val rawProblem = file.readText()
    println("--- raw problem: \n" + rawProblem)

    return generateProblemContainerFromRawProblem(rawProblem, problemId)
  }

  fun generateProblemContainerFromRawProblem(rawProblem: String, problemId: String): ProblemContainer? {
    val problem = ProblemParser().parseProblem(rawProblem)
    val container = ProblemContainer(problem, problemId, "")
    return container
  }
}