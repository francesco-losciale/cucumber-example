#!/bin/bash

docker login -u $DOCKER_USERNAME -p $DOCKER_PASSWORD &&

cd ../payment-java-card-scheme &&
  docker build -t freng0/payment-java-card-scheme . &&
  docker push freng0/payment-java-card-scheme &&

cd ../payment-java-bank-issuer &&
  docker build -t freng0/payment-java-bank-issuer . &&
  docker push freng0/payment-java-bank-issuer
