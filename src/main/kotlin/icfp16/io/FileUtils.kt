package icfp16.io

import java.io.File

class FileUtils {

  // ------------ problems ------------

  fun filenameForProblemId(problemId: String): String {
    return "problem_$problemId.txt"
  }

  fun getDefaultProblemFileFolder(): String {
    return "parsed_problems"
  }

  fun getFullPathForProblemId(problemId: String): String {
    return getDefaultProblemFileFolder()+"/"+filenameForProblemId(problemId)
  }

  fun getFullPathForProblemName(problemNameFile: String): String {
    return getDefaultProblemFileFolder()+"/"+problemNameFile
  }

  fun getProblemIdByFilePath(filePath: String): String? {
    val file = File(filePath)
    val problemName = file.nameWithoutExtension
    return getProblemIdByFileNameWithoutExtension(problemName)
  }

  fun getProblemIdByFileNameWithoutExtension(problemName: String): String? {
    val regex = "problem_(\\d*)(.*)".toRegex()
    val problemId = regex.matchEntire(problemName)?.groups?.get(1)?.value
    return problemId
  }


  // ------------ solution images ------------

  fun filenameForSolutionImage(problemId: String): String {
    return "solution_image_$problemId.png"
  }

  fun getDefaultSolutionImagesFolder(): String {
    return "generated_pictures"
  }

  fun getFullPathForSolutionImage(problemId: String): String {
    return getDefaultSolutionImagesFolder()+"/"+filenameForSolutionImage(problemId)
  }

  // ------------ solutions ------------

  fun filenameForSolution(problemId: String): String {
    return "solution_file_$problemId.txt"
  }

  fun getDefaultSolutionFileFolder(): String {
    return "generated_solutions"
  }

  fun getFullPathForSolutionFile(problemId: String): String {
    return getDefaultSolutionFileFolder()+"/"+filenameForSolution(problemId)
  }

  // ---------- cached solution names

  fun filenameForCachedSolutionName(problemId: String): String {
    return "cached_solution_names_$problemId.txt"
  }

  fun getDefaultCacheSolutionNameFileFolder(): String {
    return "cached_generated_solution_names"
  }

  fun getFullPathForCachedSolutionFile(problemId: String): String {
    return getDefaultCacheSolutionNameFileFolder()+"/"+filenameForCachedSolutionName(problemId)
  }

  // ------------ problems ------------

  fun filenameForCustomProblem(problemId: String): String {
    return "custom_problem_$problemId.txt"
  }

  fun getDefaultCustomProblemsFileFolder(): String {
    return "generated_problems"
  }

  fun getFullPathForCustomProblemFile(problemId: String): String {
    return getDefaultCustomProblemsFileFolder()+"/"+filenameForCustomProblem(problemId)
  }


}