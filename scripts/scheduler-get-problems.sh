#!/usr/bin/env bash

# CD to root of the repo
cd $(git rev-parse --show-toplevel)

while true; do 
	sh scripts/get-new-problems.sh
	sleep 900
done