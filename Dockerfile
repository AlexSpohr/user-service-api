FROM maven:3.9-amazoncorretto-21 AS build
COPY . /app
WORKDIR /app
RUN mvn clean compile

FROM build AS package
RUN mvn package -Dmaven.test.skip=true

FROM openjdk:21
COPY --from=package /app/target/*.jar /app/user-service-api.jar
EXPOSE 8080
WORKDIR /app
ENTRYPOINT ["java", "-jar", "user-service-api.jar"]