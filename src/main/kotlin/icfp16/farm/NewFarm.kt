package icfp16.farm

import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import icfp16.api.Snapshot
import icfp16.api.SolutionSpec
import icfp16.api.createApi
import icfp16.api.parseProblem
import icfp16.data.Problem
import icfp16.data.ProblemContainer
import icfp16.data.SolutionContainer
import icfp16.estimate.EstimatorFactory
import icfp16.io.FileUtils
import icfp16.solver.BestSolverEver
import icfp16.state.solution
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import java.io.File
import java.io.FileInputStream
import java.nio.file.Files
import java.nio.file.attribute.BasicFileAttributes
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.stream.Collectors
import java.util.stream.Stream

val PROBLEMS_START_ID = 1

fun main(args: Array<String>) {
  // TODO: force solving of only these problems

  var problemsIds = listOf<String>()

  problemsIds = listOf(
    "5375", "5376", "5377", "5378", "5379", "5380", "5381", "5382"
    //"5531", "4725"
  )

////  problemsIds = (500..600).map { it.toString() }
//  icfp16.farm.startSolving(problemIds = problemsIds)
}


private fun initFirebase() {
  val options = FirebaseOptions.Builder()
      .setServiceAccount(FileInputStream("firebase/icfp2016-5417b8234b8e.json"))
      .setDatabaseUrl("https://icfp2016-44126.firebaseio.com/").build()
  FirebaseApp.initializeApp(options)
}

fun startSolving(problemIds: List<String> = emptyList(), recalculateAll: Boolean = false) {
  println("Start NEW FARM solver")

  println("Init Firebase")
  initFirebase()

  val chunkSize = 10
  var chunkIndex = 0

  val api = createApi(HttpLoggingInterceptor.Level.BODY)
  val database = FirebaseDatabase.getInstance()
  var tasks = hashMapOf<String, Pair<Task, String>>()

  // ----------- start loop -----------
  do {
    println("Get already stored tasks... chunkSize=$chunkSize  chunkIndex=$chunkIndex")
    chunkIndex++
    tasks = getStoredTasks(database)

    var ourOwnSolutionIds = OwnSolutionsStorage.updatedOwnSolutions().toTypedArray()

    val taskValues = ArrayList(tasks.values)
    Collections.shuffle(taskValues)

    // three modes:
    // 0. do not solve our own solutions
    //    split by chunks
    //
    //    1. recalculate all problems where res < 1.0
    // OR 2. set 'problemsIds' --> re-calculate all these problems
    // OR 3. re-calculate only not solved

    var filteredValues = taskValues.toList()
      .filterNot { ourOwnSolutionIds.contains(it.component1().problem_id)  }

    if (recalculateAll) {
      filteredValues =
        filteredValues
          .filter { it.component1().realResemblance != 1.0 }

    } else if (problemIds.isNotEmpty()) {
      filteredValues =
        filteredValues
          .filter { problemIds.contains(it.component1().problem_id) }
          .filter { it.component1().realResemblance != 1.0 }
          .sortedBy { Integer.parseInt(it.first.problem_id) }

    } else {
      filteredValues =
        filteredValues
          .filter { it.component1().solution.isEmpty() }
    }

    // chunking
    println("I need to solve ${filteredValues.count()} tasks, but first I'll solve $chunkSize tasks")
    filteredValues = filteredValues.take(chunkSize)

    val count = filteredValues.count()
    var index = 0

    val result =
    filteredValues
      .parallelStream()
      .map {
        val task = it.first
        val problem = parseProblem(task.problem)

        index++
        println("Start solving problem #${task.problem_id}  index = $index from $count tasks")

        val resemblance = task.realResemblance
        val solver = BestSolverEver()
        val state = solver.solve(problem, task.problem_id, resemblance)

        if (state == null) {
          println("Problem ${task.problem_id} is not solved")
          "${task.problem_id} - FAIL"
        } else {
          var maxRetries = 5
          var retries = maxRetries

          do {
            val submission = api.submitSolution(task.problem_id, SolutionSpec(state.solution())).execute()

            // own solution, save for later
            if (submission.code() == 403) {
              OwnSolutionsStorage.addNewOwnSolutionId(task.problem_id)
            }

            ourOwnSolutionIds = OwnSolutionsStorage.updatedOwnSolutions().toTypedArray()

            if (ourOwnSolutionIds.contains(task.problem_id)) {
              println("We're smart now we won't re-submit our own problem!")
              break
            }

            if (submission.isSuccessful) {
              val realResemblance = submission.body().resemblance
              println("Problem ${task.problem_id} is solved. Resemblance: $realResemblance")

              val estimator = EstimatorFactory().bestEstimatorEver()
              val estimatedResemblance = estimator.resemblanceOf(problem, state, 4)

              // save to file
              val problemContainer = ProblemContainer(problem, problemId = task.problem_id, problemHash = task.hash)
              val solutionContainer = SolutionContainer(problemContainer, state, realResemblance, estimatedResemblance)
              Farm.saveSolutionImageToFile(solutionContainer)
              Farm.saveSolutionContainerToFile(solutionContainer)

              // check if we need to update
              if (task.realResemblance < realResemblance) {
                val taskRef = database.getReference("icfp2016/tasks/${it.second}")

                taskRef.updateChildren(
                  mapOf(
                    "solution" to state.solution(),
                    "realResemblance" to realResemblance,
                    "estimatedResemblance" to estimatedResemblance
                  )
                )
              } else {
                println("Won't update DB Problem ${task.problem_id} Resemblance: $realResemblance is less than we have now ${task.realResemblance}" )
              }
            } else {
              println("Sleeping because I can't submit...")
              val delay = 3000 * ((maxRetries + 1) - retries).toLong()
              Thread.sleep(delay)
            }
          } while (!submission.isSuccessful && --retries > 0)
          "${task.problem_id} - OK"
        }
      }
      .collect(Collectors.toList())

    println("These are results: $result")
    println("Stop NEW FARM solver (Something wrong with Firebase - program finalization takes time, so wait a minute :)")

  } while (tasks.count() > 0)
}


fun newFarmMonitoring() {
  println("Checking Firebase...")
  initFirebase()

  val database = FirebaseDatabase.getInstance()
  println("Getting tasks...")

  val tasks = getStoredTasks(database)
  val ourOwnSolutionIds = OwnSolutionsStorage.updatedOwnSolutions().toTypedArray()

  val taskValues = ArrayList(tasks.values)

  val overall = taskValues
    .count()

  val ourOwn = taskValues
    .filter { ourOwnSolutionIds.contains(it.component1().problem_id)  }
    .count()

  val unsolved = taskValues
    .filterNot { ourOwnSolutionIds.contains(it.component1().problem_id)  }
    .filter { it.component1().solution.isEmpty()}
    .count()

  val gems = taskValues
    .filter { it.component1().realResemblance == 1.0 }
    .count()

  val veryNice = taskValues
    .filter { it.component1().realResemblance < 1.0 &&  it.component1().realResemblance >= 0.8 }
    .count()

  val betterThanNothing = taskValues
    .filter { it.component1().realResemblance < 0.8 &&  it.component1().realResemblance >= 0.5 }
    .count()

  val veryBad = taskValues
    .filter { it.component1().realResemblance <= 0.3 }
    .count()

  println("")
  println("-------------------- Firebase current status ----------------- ")
  println("date " + Date())
  println("- overall tasks count = " + overall)
  println("- our own tasks count = " + ourOwn)
  println("- unsolved tasks count = " + unsolved)
  println("")
  println("gems: r == 1.0 tasks count = " + gems)
  println("nice: r in [0.8 .. 1.0) tasks count = " + veryNice)
  println("ok: r in [0.5 .. 0.8) tasks count = " + betterThanNothing)
  println("bad: r =< 0.3 count = " + veryBad)
  println("-------------------------------------------------------------- ")
  println("")
  println("")
}


fun understandIdsDifference() {
  val tasksFromServer: List<String> = getAllTasksIdsFromFireBase().map { it.problem_id }
  val ourOwnSolutionIds = OwnSolutionsStorage.updatedOwnSolutions().toTypedArray()

  val localTasksIds: List<String> = File(FileUtils().getDefaultProblemFileFolder()).listFiles()
    .map { FileUtils().getProblemIdByFileNameWithoutExtension(it.name)!! }

  println("tasks from server = ${tasksFromServer.count()}, local tasks = ${localTasksIds.count()}")

  println(" our own solutions \n${ourOwnSolutionIds.sortedBy { Integer.parseInt(it) }}")


  val mutableSet = localTasksIds.toMutableSet()
  mutableSet.removeAll(tasksFromServer)
  println(" local-server \n${mutableSet.sortedBy { Integer.parseInt(it) }}")


//  val ownMinusDifference = ourOwnSolutionIds.toMutableSet()
//  ownMinusDifference.removeAll(mutableSet)
//  println("difference ids: count=${ownMinusDifference.count()}")
//  println(ownMinusDifference)
}

fun printUnsolved() {
  println("Checking Firebase...")
  initFirebase()

  val database = FirebaseDatabase.getInstance()
  println("Getting tasks...")

  val tasks = getStoredTasks(database)
  val ourOwnSolutionIds = OwnSolutionsStorage.updatedOwnSolutions().toTypedArray()

  val taskValues = ArrayList(tasks.values)

  val unsolved = taskValues
    .filterNot { ourOwnSolutionIds.contains(it.component1().problem_id)  }
    .filter { it.component1().solution.isEmpty()}
    .forEach { t ->
      println("-------------------------------")
      printTaskBeautiful(t.component1())
      println("-------------------------------")
      println("")
    }
}


fun getAllTasksIdsFromFireBase(): List<Task> {
  println("Checking Firebase...")
  initFirebase()

  val database = FirebaseDatabase.getInstance()
  println("Getting tasks...")

  val tasks = getStoredTasks(database)

  val taskValues = ArrayList(tasks.values)

  val result = taskValues
    .sortedBy { Integer.parseInt(it.first.problem_id) }
    .map { it.first }

  return result
}


fun updateTasksDb() {
  initFirebase()

  val api = createApi(HttpLoggingInterceptor.Level.NONE)
  val database = FirebaseDatabase.getInstance()

  val ref = database.getReference("icfp2016").child("tasks")

  val snapshotsRest = api.listSnapshots().execute()

  if (!snapshotsRest.isSuccessful) {
    println("Can not get snapshots: ${snapshotsRest.message()}")
    return
  }

  println("Getting list of shapshots...")
  val snapshots: List<Snapshot> = snapshotsRest.body().snapshots
  val latest = snapshots.reduce { a: Snapshot, b: Snapshot -> if (a.time > b.time) a else b }
  println("Latest snapshot " + latest)

  Thread.sleep(1100)

  val problemSpecsReq = api.getContestStatus(latest.snapshot_hash).execute()
  if (!problemSpecsReq.isSuccessful) {
    println("Can not get problem specs: ${problemSpecsReq.message()}")
    return
  }
  val problemSpecs = problemSpecsReq.body().problems

  Thread.sleep(1100)

  val tasks = getStoredTasks(database)

  problemSpecs
      .filter { !tasks.containsKey(it.problem_id) }
      .filter { Integer.parseInt(it.problem_id) > PROBLEMS_START_ID }
      .forEach {
        var resp: Response<Problem>
        do {
          resp = api.getProblemSpec(it.problem_spec_hash).execute()

          if (!resp.isSuccessful) {
            println("Can not get spec for problem #${it.problem_id}: ${resp.message()}")
          } else {
            println("Got spec for problem #${it.problem_id}")
          }

          Thread.sleep(1100)
        } while (!resp.isSuccessful)

        val problem = resp.body()

        val task = Task(it.problem_id, it.problem_spec_hash, it.publish_time, problem.rawString)
        ref.push().setValue(task)
      }

  println("Firebase updated!")
}

fun printCurrentFirebaseTasks() {
  initFirebase()
  val tasks = getStoredTasks(FirebaseDatabase.getInstance())

  tasks
      .values
      .sortedBy { Integer.parseInt(it.first.problem_id) }
      .forEach { pair ->
        val task = pair.first
        printTaskBeautiful(task)
      }
}

fun printTaskBeautiful(task: Task) {
  println("PROBLEM_ID: ${task.problem_id}")
  println("hash: ${task.hash}")
  println("time: ${task.time}")
  println("problem: ${task.problem}")
  println("solution: ${task.solution}")
  println("realResemblance: ${task.realResemblance}")
  println("estimatedResemblance: ${task.estimatedResemblance}")
  println("")
  println("")
}

fun importSolutionsFromLocalToFirebase() {
  initFirebase()

  val database = FirebaseDatabase.getInstance()
  val storedTasks = getStoredTasks(database)
  val ref = database.getReference("icfp2016").child("tasks")

  val addedTasks = HashSet<String>()

  File("generated_solutions").listFiles().forEach {
    val file = it.readText()
    val idRes = Regex("problemId=(.*?),").find(file)
    if (idRes == null) {
      println("In file ${it.name} id is not found")
    } else {
      val taskId = idRes.groups[1]!!.value

      val solution = file.split("-------------------------- solution --------------------")[1]
      val resemblanceStr = Regex("real res =([0-9.]*) estimated res =([0-9.]*)").find(file)

      if (resemblanceStr == null) {
        println("In file ${it.name} resemblance is not found")
      } else {
        val rR = resemblanceStr.groups[1]!!.value
        val eR = resemblanceStr.groups[2]!!.value
        val realResemblance = if (!rR.isEmpty()) rR.toDouble() else 0.toDouble()
        val estResemblance = if (!eR.isEmpty()) eR.toDouble() else 0.toDouble()

        val task = storedTasks[taskId]

        if (storedTasks.containsKey(taskId) || addedTasks.contains(taskId)) {
          val taskRef = database.getReference("icfp2016/tasks/${task!!.second}")
          taskRef.updateChildren(
              mapOf(
                  "solution" to solution,
                  "realResemblance" to realResemblance,
                  "estimatedResemblance" to estResemblance
              )
          )
          println("Task #$taskId is updated")
        } else {
          val problemStart = file.indexOf("rawString=") + "rawString=".length
          val problemEnd = file.indexOf(")", problemStart) - 1

          val problem = file.substring(problemStart..problemEnd)

          val attrs = Files.readAttributes(it.toPath(), BasicFileAttributes::class.java)
          ref.push().setValue(Task(taskId, "", attrs.creationTime().to(TimeUnit.SECONDS), problem, solution,
              realResemblance, estResemblance))
          println("Task #$taskId is added to FB")
          addedTasks.add(taskId)
        }

        println("${it.name}: uploaded to firebase")
      }
    }
  }
}

private fun getStoredTasks(database: FirebaseDatabase): HashMap<String, Pair<Task, String>> {
  val ref = database.getReference("icfp2016/tasks")

  val tasks = HashMap<String, Pair<Task, String>>()
  val block = CountDownLatch(1)

  // Get all currently available tasks
  ref.addListenerForSingleValueEvent(DbListener {
    for (c in it!!.children) {
      val task = c.getValue(Task::class.java)
      tasks[task.problem_id] = task.to(c.key)
    }

    block.countDown()
  })

  block.await()
  return tasks
}

class DbListener(val f: (DataSnapshot?) -> Unit): ValueEventListener {
  override fun onDataChange(p0: DataSnapshot?) {
    f(p0)
  }

  override fun onCancelled(p0: DatabaseError?) {
    System.err.println("Some trouble with DB " + p0?.toString())
  }
}

data class Task(
    val problem_id: String = "",
    val hash: String = "",
    val time: Long = 0,
    val problem: String = "",
    val solution: String = "",
    val realResemblance: Double = 0.toDouble(),
    val estimatedResemblance: Double = 0.toDouble()
)

fun <T> Collection<T>.parallelStream(): Stream<T> {
  @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN", "UNCHECKED_CAST")
  return (this as java.util.Collection<T>).parallelStream()
}
