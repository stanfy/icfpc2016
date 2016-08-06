#!/usr/bin/env bash

git pull || exit 1
./gradlew run -PrunArgs=grab || exit 1
git add parsed_problems || exit 1
git commit -m "[AUTO] Add new problems at $(date)" # it may fail if no changes, it's ok
git push || exit 1