package icfp16.farm

class ParsedProblemsFileUtils {

  fun filenameForProblemId(problemId: String): String {
    return "problem_$problemId.txt"
  }

  fun getDefaultProblemFileFolder(): String {
    return "parsed_problems"
  }

  fun getFullPathForProblemId(problemId: String): String {
    return getDefaultProblemFileFolder()+"/"+filenameForProblemId(problemId)
  }
}