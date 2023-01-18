#!/usr/bin/env bash
SBT_OPTS="-XX:MaxMetaspaceSize=400M" sbt clean scalafmtAll scalafixAll coverage test coverageReport
