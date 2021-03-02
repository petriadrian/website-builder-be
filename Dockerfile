FROM maven:3.6.3-adoptopenjdk-14 as build
COPY pom.xml .

# dependencies already downloaded so just create the jar
COPY src/ src/
RUN ["mvn", "package", "-DskipTests"]

FROM openjdk:14-oracle

ENV DB=mongo

EXPOSE 8080

WORKDIR /app

COPY --from=build /target/app-1.0.jar ./app.jar
ENTRYPOINT ["java","-jar","app.jar"]
