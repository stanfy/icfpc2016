package icfp16.farm

import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

class OwnSolutionsStorage {

  companion object {

    val fileName = "own-solutions-ids.txt"

    fun updatedOwnSolutions(): List<String> {
      return File(fileName).readLines()
    }

    fun addNewOwnSolutionId(id: String) {

      try {
        val lineToWrite = "\n$id"
        Files.write(Paths.get(fileName), lineToWrite.toByteArray(),
          StandardOpenOption.APPEND)
      } catch (e: IOException) {
        println("oops, can't write to file ${fileName}")
      }
    }
  }
}