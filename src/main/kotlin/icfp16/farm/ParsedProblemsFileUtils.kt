package icfp16.farm

class ParsedProblemsFileUtils {

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

  // ------------ solutions ------------

  fun filenameForSolutionImage(problemId: String): String {
    return "solution_image_$problemId.png"
  }

  fun getDefaultSolutionImagesFolder(): String {
    return "generated_pictures_bitmap_solver"
  }

  fun getFullPathForSolutionImage(problemId: String): String {
    return getDefaultSolutionImagesFolder()+"/"+filenameForSolutionImage(problemId)
  }
}