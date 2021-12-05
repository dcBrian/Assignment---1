FROM maven:3.6.0-jdk-11-slim AS build

# copy the source tree and the pom.xml to our new container
COPY ./ ./

# package our application code
RUN mvn clean package -DskipTests

# set the startup command to execute the jar
ENTRYPOINT ["java", "-jar", "target/homework-0.0.1-SNAPSHOT.jar"]
