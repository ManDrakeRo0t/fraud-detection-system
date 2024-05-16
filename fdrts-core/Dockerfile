#
# Build stage
#
FROM maven:3.8.3-openjdk-17 AS build
COPY . .
RUN mvn clean package -Pprod -DskipTests

#
# Package stage
#
FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/fdrts-core-0.0.1-SNAPSHOT.jar core.jar
# ENV PORT=8081
EXPOSE 8080
ENTRYPOINT ["java","-jar", "core.jar"]