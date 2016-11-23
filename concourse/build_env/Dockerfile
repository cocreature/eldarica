FROM ubuntu:16.04
MAINTAINER Moritz Kiefer <moritz.kiefer@purelyfunctional.org>
RUN apt-get update && \
    apt-get install -y \
      ant \
      openjdk-8-jdk \
      scala && \
    rm -rf /var/lib/apt/lists/*
ENV SCALA_HOME /usr/share/scala
