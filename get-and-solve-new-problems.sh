#!/usr/bin/env bash

sh ./get-new-problems.sh
if [ $? -ne 0 ] ; then
	exit 1
else
	./gradlew run -PrunArgs=doit || exit 1
	git commit -m "[AUTO] Farm solved problems at $(date)" # it may fail if no changes, it's ok
	git push || exit 1
fi