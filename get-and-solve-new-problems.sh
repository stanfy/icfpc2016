#!/usr/bin/env bash

echo "Pulling repo.."
git pull || exit 1
echo "=> Pulled"

echo "Start grabbing.."
./gradlew run -PrunArgs=grab || exit 1
echo "=> Grabbed"

if ! git diff-index --no-ext-diff --quiet HEAD --; then
    git add parsed_problems || exit 1
	git commit -m "[AUTO] Add new problems at $(date)" # it may fail if no changes, it's ok
	git push || exit 1
fi

echo "=> Start solving.."
./gradlew run -PrunArgs=doit,solveUnsolved,4 || exit 1
echo "=> Saving farmed solutions.."

if ! git diff-index --no-ext-diff --quiet HEAD --; then
    git add generated_solutions
    git add generated_pictures
	git commit -m "[AUTO] Add farmed solutions at $(date)" # it may fail if no changes, it's ok
	git push || exit 1
fi