FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app

COPY pom.xml .

RUN mvn dependency:go-offline -B

COPY src ./src

RUN mvn clean package -DskipTests    # skip during image build

FROM maven:3.9.9-eclipse-temurin-21 AS tester
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
CMD ["mvn", "test"]


FROM eclipse-temurin:21-jdk AS runner

WORKDIR /app

COPY --from=builder ./app/target/auth-service-0.0.1-SNAPSHOT.jar ./app.jar

EXPOSE 4005

ENTRYPOINT ["java", "-jar", "app.jar"]