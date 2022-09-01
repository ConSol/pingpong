#!/usr/bin/env bash
cd ../
commit_id=$(git rev-parse --short HEAD)
mvn clean package -DskipTests
docker build --file dockerfile/Dockerfile.distroless --tag 238356400807.dkr.ecr.eu-central-1.amazonaws.com/ping:${commit_id} ./ping
docker build --file dockerfile/Dockerfile.distroless --tag 238356400807.dkr.ecr.eu-central-1.amazonaws.com/pong:${commit_id} ./pong
docker build --file dockerfile/Dockerfile.distroless --tag 238356400807.dkr.ecr.eu-central-1.amazonaws.com/reporter:${commit_id} ./reporter
aws ecr get-login-password --region eu-central-1 | docker login --username AWS --password-stdin 238356400807.dkr.ecr.eu-central-1.amazonaws.com
docker push 238356400807.dkr.ecr.eu-central-1.amazonaws.com/ping:${commit_id}
docker push 238356400807.dkr.ecr.eu-central-1.amazonaws.com/pong:${commit_id}
docker push 238356400807.dkr.ecr.eu-central-1.amazonaws.com/reporter:${commit_id}
