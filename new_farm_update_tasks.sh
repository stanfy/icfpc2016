#!/usr/bin/env bash

# How it works:
# 1. Pull problems (with ID > icfp16.farm.PROBLEMS_START_ID = 3831) that are not already in Firebase
# 2. Send them to the firebase
# 3. Wait for 1 hour
# 4. GOTO 1

echo "Press [CTRL+C] to stop.."
while :
do
  echo "Pull new tasks and push them to the firebase"
  ./gradlew run -PrunArgs=updateFirebase || exit 0

  echo "Tasks are updated. Wait for 1 hour"

	sleep 3600
done
