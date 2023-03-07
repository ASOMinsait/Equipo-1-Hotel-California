FROM openjdk:19-alpine as builder

WORKDIR /app/facturas

COPY ./pom.xml /app
COPY ./facturas/.mvn ./.mvn
COPY ./facturas/mvnw .
COPY ./facturas/pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
#RUN ./mvnw dependency:go-offline
COPY ./facturas/src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:19-alpine
WORKDIR /app
RUN mkdir ./logs
COPY --from=builder /app/facturas/target/facturas-0.0.1-SNAPSHOT.jar .
EXPOSE 8001

CMD ["java", "-jar", "facturas-0.0.1-SNAPSHOT.jar"]