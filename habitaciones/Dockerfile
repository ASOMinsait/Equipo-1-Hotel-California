FROM openjdk:19-alpine as builder

WORKDIR /app/habitaciones

COPY ./pom.xml /app
COPY ./habitaciones/.mvn ./.mvn
COPY ./habitaciones/mvnw .
COPY ./habitaciones/pom.xml .
RUN sed -i -e 's/\r$//' ./mvnw

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
COPY ./habitaciones/src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:19-alpine
WORKDIR /app
RUN mkdir ./logs
COPY --from=builder /app/habitaciones/target/habitaciones-0.0.1-SNAPSHOT.jar .
EXPOSE 8002

CMD ["java", "-jar", "habitaciones-0.0.1-SNAPSHOT.jar"]
