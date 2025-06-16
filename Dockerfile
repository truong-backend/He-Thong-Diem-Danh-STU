FROM eclipse-temurin:17-jdk-alpine
VOLUME /demo
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"] 