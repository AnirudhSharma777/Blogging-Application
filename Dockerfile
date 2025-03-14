
FROM gradle:8.3.0-jdk17 As build

WORKDIR /app

COPY . .

RUN chmod +x ./gradlew

RUN ./gradlew build -x test

FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY --from=build /app/build/libs/*.jar blogapplication.jar

EXPOSE 8081

ENTRYPOINT [ "java", "-jar", "blogapplication.jar" ]
