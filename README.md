ICFP 2016
=========

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