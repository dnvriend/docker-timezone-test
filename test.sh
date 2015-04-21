#!/bin/bash
export COMPOSE_FILE=docker-compose.yml
activator clean universal:packageZipTarball
docker-compose kill
docker-compose rm --force
docker-compose build
docker-compose up -d
docker-compose logs
#sbt test