ICFP 2016
=========

Some automation
-----------------
```
./get-new-problems.sh
```
To automate getting new problems, run `./scripts/scheduler-get-new-problems.sh` and leave it in bg.


###Farm:

Solve unsolved:

```
./new_farm_start.sh 
```


Solve all current tasks:

```
./new_farm_recalculate_all.sh
```

###Monitoring
```
./new_farm_monitoring.sh
```

![pic](last_results.png?raw=true)



New farm
========

New farm contains two parts:
* solver: get tasks from firebase and solves it
* syncer: get tasks from REST API sends them to Firebase

`new_farm_start.sh` - starts solver. Solver tries to solve all unsolved tasks. After this it wait 1 minute, pulls repo
and begin solve again
`new_farm_update_tasks.sh` - call sync every hour

Clone this repo into some separate place and start both scripts in different terminals.

Also: use
```
./gradlew run -PrunArgs=printNewFarmTasks
```
to show all tasks from Firebase

**Install Java 8!!!**
Also, please ensure [your global `.gitignore`](https://help.github.com/articles/ignoring-files/#create-a-global-gitignore) 
file excludes your editor file (like .idea or .eclipse).

Code style
----------
Please use 2 spaces indentation (4 spaces for continuation indent).

Recommended IDE
---------------
[IntelliJ Idea Community Edition](https://www.jetbrains.com/idea/download/)

Import to IDEA
--------------
Just choose an import option and select build.gradle file in this repo root. Configure Java 8 to be used as Gradle VM.

Command line
------------

Run tests
```
gw test
```

Run application
```
gw run
```

What the hell is `gw`?
It's a gradle wrapper shortcut. Add the following to your `~/.profile`:
```bash
function upfind() {
  dir=`pwd`
  while [ "$dir" != "/" ]; do
    p=`find "$dir" -maxdepth 1 -name $1`
    if [ ! -z $p ]; then
      echo "$p"
      return
    fi
    dir=`dirname "$dir"`
  done
}

function gw() {
  GW="$(upfind gradlew)"
  if [ -z "$GW" ]; then
    echo "Gradle wrapper not found."
  else
    $GW $@
  fi
}
```