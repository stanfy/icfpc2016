package icfp16.solver

import icfp16.io.FileUtils
import java.io.File

class SolvedSolutionNamesCacher {

  fun cacheSolutionNameToFile(problemId: String, names: List<String>) {
    val file = File(FileUtils().getFullPathForCachedSolutionFile(problemId))
    file.bufferedWriter().use { out ->
      names.forEach { n ->
        out.write(n)
        out.write("\n")
      }
    }
  }

  fun readCachedSolutionNamesFor(problemId: String): List<String> {
    val file = File(FileUtils().getFullPathForCachedSolutionFile(problemId))
    if (!file.exists()) {
      return emptyList()
    }
    val cachedNames = file.readLines()
    return cachedNames
  }

}