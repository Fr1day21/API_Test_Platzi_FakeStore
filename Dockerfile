FROM maven:3.9.8-eclipse-temurin-21

WORKDIR /app

## Copy pom.xml and instal dependency (undisable when only running Dockerfile)
#COPY pom.xml .
#RUN mvn install -DskipTests

## Copy all source code (undisable when only running Dockerfile)
#COPY . .

## Running test run (undisable when only running Dockerfile)
#CMD ["mvn", "test", "-DsuiteXmlFile=testng.xml"]

