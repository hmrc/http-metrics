#!/usr/bin/env bash
SBT_OPTS="-XX:MaxMetaspaceSize=400M" sbt pre-commit
