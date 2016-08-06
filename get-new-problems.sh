#!/usr/bin/env bash

if [ $# -ge 1 ]; then 
LOG_PATH="$1"
else
LOG_PATH="/tmp/icfp_get_new_problem_log.txt"
fi

echo >> $LOG_PATH
date >> $LOG_PATH

echo "Pulling repo.." >> $LOG_PATH
git pull >> $LOG_PATH 2>&1 || exit 1
echo "Pulled"

./gradlew run -PrunArgs=grab >> $LOG_PATH || exit 1

if ! git diff-index --quiet HEAD --; then
	echo "Going to commit" >> $LOG_PATH

    git add parsed_problems || exit 1
	git commit -m "[AUTO] Add new problems at $(date)" # it may fail if no changes, it's ok
	git push >> $LOG_PATH || exit 1

	echo "Commited" >> $LOG_PATH
fi

echo "Done" >> $LOG_PATH