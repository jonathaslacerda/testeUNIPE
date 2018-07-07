FROM openjdk:8-alpine

MAINTAINER Jonathas Lacerda

ADD target/aulaUNIPE.jar aulaUNIPE.jar

CMD java -jar /aulaUNIPE.jar