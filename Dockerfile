FROM maven:3.9.8-eclipse-temurin-21

WORKDIR /app

# Copy pom.xml and instal dependency
# COPY pom.xml .
# RUN mvn install -DskipTests

# Copy all source code
# COPY . .

# Running test run
# CMD ["mvn", "test", "-DsuiteXmlFile=testng.xml"]

