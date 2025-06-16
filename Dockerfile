FROM eclipse-temurin:17-jdk-alpine
VOLUME /demo
ENTRYPOINT ["java","-jar","/app.jar"]