#!/usr/bin/env bash

LOG_PATH="/Users/zen/Desktop/farm_log.txt"

echo >> $LOG_PATH
date >> $LOG_PATH

echo "Pulling repo.." >> $LOG_PATH
git pull >> $LOG_PATH || exit 1

./gradlew run -PrunArgs=grab >> $LOG_PATH || exit 1

if ! git diff-index --quiet HEAD --; then
	echo "Going to commit" >> $LOG_PATH

    git add parsed_problems || exit 1
	git commit -m "[AUTO] Add new problems at $(date)" # it may fail if no changes, it's ok
	git push >> $LOG_PATH || exit 1

	echo "Commited" >> $LOG_PATH
fi

echo "Done" >> $LOG_PATH