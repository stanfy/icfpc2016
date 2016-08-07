#!/usr/bin/env bash

# How it works:
# 1. Get tasks status
# 2. Wait for 1 minute
# 3. GOTO 1

echo "Press [CTRL+C] to stop.."
while :
do
  echo "Current status..."
  ./gradlew run -PrunArgs=monitoring || exit 0

  echo "Will sleep for 1 min"

  sleep 60
done
