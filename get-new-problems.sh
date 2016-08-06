#!/usr/bin/env bash

echo "Pulling repo.."
git pull || echo "Failed to pull"; exit 1

gw run -PrunArgs=automate
./gradlew run -PrunArgs=grab || echo "Failed to grab new problems"; exit 1

if ! git diff-index --quiet HEAD --; then
    git add parsed_problems || echo "Failed to add new problems to the repo"; exit 1
	git commit -m "[AUTO] Add new problems at $(date)" # it may fail if no changes, it's ok
	git push || echo "Failed to push grabbed problems to the repo1"; exit 1
fi