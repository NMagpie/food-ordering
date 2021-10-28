FROM adoptopenjdk/openjdk11:latest
EXPOSE 9090
ADD target/food-ordering.jar food-ordering.jar
ADD /configFO.txt configFO.txt
ENTRYPOINT ["java", "-jar", "/food-ordering.jar"]