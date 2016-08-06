#!/usr/bin/env bash

echo "Pulling repo.."
git pull || exit 1

./gradlew run -PrunArgs=grab || exit 1

if ! git diff-index --quiet HEAD --; then
    git add parsed_problems || exit 1
	git commit -m "[AUTO] Add new problems at $(date)" # it may fail if no changes, it's ok
	git push || exit 1
fi