#!/usr/bin/env bash

cd src/main/docker

docker compose up -d

echo "Docker container started!"

docker compose logs -f
