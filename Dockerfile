FROM eclipse-temurin:11-jre
WORKDIR /app
# Copy pre-built jar from host `target/`
COPY target/*.jar /app/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
