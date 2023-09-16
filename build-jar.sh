#!/bin/bash

echo "Gerando build do frontend..."
cd ./cars-web && npm i && npm run build-prod
cd ..

echo "Gerando build do backend"
cd ./cars-api && mvn clean package
cd ..
