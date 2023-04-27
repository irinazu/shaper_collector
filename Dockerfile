FROM openjdk:11
ADD target/shaper_collector-0.0.1-SNAPSHOT.jar shaper.jar
ENTRYPOINT ["java","-jar","shaper.jar"]