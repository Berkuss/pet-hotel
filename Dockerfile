ARG version
FROM maven:3.8.5-openjdk-17-slim

WORKDIR /app
COPY . .
RUN mvn package -DskipTests=true

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/target/pet-hotel.jar"]
