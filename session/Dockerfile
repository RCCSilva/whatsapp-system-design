FROM openjdk:11.0.7 AS builder

WORKDIR /back

COPY . .
RUN ./mvnw package -Dmaven.test.skip=true 

FROM openjdk:11.0.7-jre-slim
ARG JAR_FILE=/back/target/*.jar

RUN addgroup spring && useradd -g spring spring
USER spring:spring

WORKDIR /app
COPY --from=builder ${JAR_FILE} app.jar

EXPOSE 5000

ENTRYPOINT ["java", "-jar", "app.jar"]