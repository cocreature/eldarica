FROM ubuntu:16.04
MAINTAINER Moritz Kiefer <moritz.kiefer@purelyfunctional.org>
RUN apt-get update && \
    apt-get install -y \
      openjdk-8-jre-headless && \
    rm -rf /var/lib/apt/lists/*
ADD lazabs.jar /eldarica/lazabs.jar
