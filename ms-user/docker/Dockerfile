FROM amazoncorretto:21-alpine-jdk

WORKDIR /app/deployment
COPY . /app/deployment
ENV JAVA_OPTS="-XX:MinRAMPercentage=50.0 -XX:MaxRAMPercentage=80.0"
ENTRYPOINT ["sh", "-c", "java -jar /app/deployment/*.jar"]

EXPOSE 8080