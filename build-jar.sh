#!/bin/bash

echo "Gerando build do frontend..."
cd ./cars-web && npm i && npm run build-prod
cd ..

echo "Movendo arquivos gerados do frontend para o diretório de estáticos do backend"
mv ./cars-web/dist/cars-web/* ./cars-api/src/main/resources/static

echo "Gerando build do backend"
cd ./cars-api && mvn clean package
cd ..

