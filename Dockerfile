FROM openjdk:17-jdk-alpine

COPY target/my-spring-boot-app.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]