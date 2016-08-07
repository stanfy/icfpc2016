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
import icfp16.estimate.EstimatorFactory
import icfp16.solver.BestSolverEver
import icfp16.state.solution
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import java.io.File
import java.io.FileInputStream
import java.util.*
import java.util.concurrent.CountDownLatch
import java.util.stream.Stream

val PROBLEMS_START_ID = 3831

fun main(args: Array<String>) {
  importSolutionsFromLocalToFirebase()
}

private fun initFirebase() {
  val options = FirebaseOptions.Builder()
      .setServiceAccount(FileInputStream("firebase/icfp2016-5417b8234b8e.json"))
      .setDatabaseUrl("https://icfp2016-44126.firebaseio.com/").build()
  FirebaseApp.initializeApp(options)
}

fun startSolving() {
  println("Start NEW FARM solver")

  println("Init Firebase")
  initFirebase()

  val api = createApi(HttpLoggingInterceptor.Level.NONE)

  val database = FirebaseDatabase.getInstance()
  println("Get already stored tasks")
  val tasks = getStoredTasks(database)

  tasks.values
      .parallelStream()
      .filter { it.component1().solution.isEmpty() }
      .forEach {
        val task = it.first
        val problem = parseProblem(task.problem)

        println("Start solving problem #${task.problem_id}")

        val solver = BestSolverEver()
        val state = solver.solve(problem, task.problem_id)

        if (state == null) {
          println("Problem ${task.problem_id} is not solved")
        } else {
          var retries = 5

          do {
            val submission = api.submitSolution(task.problem_id, SolutionSpec(state.solution())).execute()

            if (submission.isSuccessful) {
              val realResemblance = submission.body().resemblance
              println("Problem ${task.problem_id} is solved. Resemblance: $realResemblance")

              val estimator = EstimatorFactory().bestEstimatorEver()
              val estimatedResemblance = estimator.resemblanceOf(problem, state, 4)

              val taskRef = database.getReference("icfp2016/tasks/${it.second}")

              taskRef.updateChildren(
                  mapOf(
                      "solution" to state.solution(),
                      "realResemblance" to realResemblance,
                      "estimatedResemblance" to estimatedResemblance
                  )
              )
            } else {
              Thread.sleep(3000)
            }
          } while (!submission.isSuccessful && --retries > 0)

        }
      }

  println("Stop NEW FARM solver (Something wrong with Firebase - program finalization takes time, so wait a minute :)")
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

  Thread.sleep(1200)

  val problemSpecsReq = api.getContestStatus(latest.snapshot_hash).execute()
  if (!problemSpecsReq.isSuccessful) {
    println("Can not get problem specs: ${problemSpecsReq.message()}")
    return
  }
  val problemSpecs = problemSpecsReq.body().problems

  Thread.sleep(1200)

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

          Thread.sleep(3000)
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
}

fun importSolutionsFromLocalToFirebase() {
  initFirebase()

  val database = FirebaseDatabase.getInstance()
  val storedTasks = getStoredTasks(database)
  File("generated_solutions").listFiles().forEach {
    val file = it.readText()
    val idRes = Regex("problemId=(.*?),").find(file)
    if (idRes == null) {
      println("In file ${it.name} id is not found")
    } else {
      val taskId = idRes.groups[1]!!.value
      if (storedTasks.containsKey(taskId)) {
        val solution = file.split("-------------------------- solution --------------------")[1]
        val resembl = Regex("real res =([0-9.]*) estimated res =([0-9.]*)").find(file)

        if (resembl == null) {
          println("In file ${it.name} resemblance is not found")
        } else {
          val realResembl = resembl.groups[1]!!.value
          val estResembl = resembl.groups[2]!!.value

          val task = storedTasks[taskId]

          val taskRef = database.getReference("icfp2016/tasks/${task!!.second}")

          println(mapOf(
              "solution" to solution,
              "realResemblance" to if (!realResembl.isEmpty()) realResembl.toDouble() else 0.toDouble(),
              "estimatedResemblance" to if (!estResembl.isEmpty()) estResembl.toDouble() else 0.toDouble()
          ))

          taskRef.updateChildren(
              mapOf(
                  "solution" to solution,
                  "realResemblance" to if (!realResembl.isEmpty()) realResembl.toDouble() else 0.toDouble(),
                  "estimatedResemblance" to if (!estResembl.isEmpty()) estResembl.toDouble() else 0.toDouble()
              )
          )

          println("${it.name}: uploaded to firebase")
        }
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
    val realResemblance: Any = 0.toDouble(),
    val estimatedResemblance: Any = 0.toDouble()
)

fun <T> Collection<T>.parallelStream(): Stream<T> {
  @Suppress("PLATFORM_CLASS_MAPPED_TO_KOTLIN", "UNCHECKED_CAST")
  return (this as java.util.Collection<T>).parallelStream()
}
