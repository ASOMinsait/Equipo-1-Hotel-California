FROM openjdk:19-alpine as builder

WORKDIR /app/reservas

COPY ./pom.xml /app
COPY ./reservas/.mvn ./.mvn
COPY ./reservas/mvnw .

COPY ./reservas/pom.xml .

RUN sed -i -e 's/\r$//' ./mvnw

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
COPY ./reservas/src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:19-alpine
WORKDIR /app
RUN mkdir ./logs
COPY --from=builder /app/reservas/target/reservas-0.0.1-SNAPSHOT.jar .
EXPOSE 8003

CMD ["java", "-jar", "reservas-0.0.1-SNAPSHOT.jar"]
