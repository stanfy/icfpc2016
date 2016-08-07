#!/usr/bin/env bash

# How it works:
# 1. Pull repo
# 2. Get all tasks tasks from Firebase
# 3. Solve tasks + update Firebase with solution
# 4. Wait for 1 minute
# 5. GOTO 1

echo "Press [CTRL+C] to stop.."
while :
do
  echo "Pulling repo.."
  git pull || exit 0
  echo "=> Pulled"

  echo "Start solving..."
  ./gradlew run -PrunArgs=recalculateAll || exit 0

  echo "All tasks solved. Wait for 1 minute"

	sleep 60
done
