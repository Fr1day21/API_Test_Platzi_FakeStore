# Gunakan base image Maven + JDK 21
FROM maven:3.9.8-eclipse-temurin-21

WORKDIR /app

# Step 1: copy pom.xml dulu, install dependency (caching efisien)
COPY pom.xml .
RUN mvn install -DskipTests

# Step 2: copy seluruh source code
COPY . .

# Step 3: default command → jalankan test sesuai suite TestNG
CMD ["mvn", "test", "-DsuiteXmlFile=testng.xml"]

