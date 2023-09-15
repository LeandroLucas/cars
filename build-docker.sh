#!/bin/bash

sh build-jar.sh

echo "Gerando container Docker"
docker build -t leosantos/cars:1.0.2 .
