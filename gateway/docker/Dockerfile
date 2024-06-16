FROM amazoncorretto:21-alpine-jdk

# Instalar o curl
RUN apk --no-cache add curl

WORKDIR /app/deployment
COPY . /app/deployment
ENV JAVA_OPTS="-XX:MinRAMPercentage=50.0 -XX:MaxRAMPercentage=80.0"
ENV SPRING_PROFILES_ACTIVE=des
ENTRYPOINT ["sh", "-c", "java -jar /app/deployment/*.jar"]

EXPOSE 8085
