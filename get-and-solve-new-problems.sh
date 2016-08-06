#!/usr/bin/env bash

echo "Pulling repo.."
git pull || exit 1

./gradlew run -PrunArgs=grab || exit 1

if ! git diff-index --no-ext-diff --quiet HEAD --; then
    git add parsed_problems || exit 1
	git commit -m "[AUTO] Add new problems at $(date)" # it may fail if no changes, it's ok
	git push || exit 1
fi

./gradlew run -PrunArgs=doit || exit 1

if ! git diff-index --no-ext-diff --quiet HEAD --; then
    git add parsed_problems || exit 1
	git commit -m "[AUTO] Add new problems at $(date)" # it may fail if no changes, it's ok
	git push || exit 1
fi

git commit -m "[AUTO] Farm solved problems at $(date)" # it may fail if no changes, it's ok
git push || echo "Failed to push new solved problems to the repo"; exit 1